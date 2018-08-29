package com.showka.web.u08;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.showka.domain.Busho;
import com.showka.domain.FBFurikomiMatchingResult;
import com.showka.kubun.FurikomiMatchintErrorCause;
import com.showka.service.crud.u08.i.FirmBankFurikomiMatchingCrudService;
import com.showka.service.crud.u08.i.FirmBankFurikomiMatchingErrorCrudService;
import com.showka.service.crud.z00.i.BushoCrudService;
import com.showka.service.search.u08.i.FirmBankFurikomiSearchService;
import com.showka.value.TheDate;
import com.showka.web.ControllerBase;
import com.showka.web.ModelAndViewExtended;

@Controller
@RequestMapping("api/u08b003")
public class U08B003Controller extends ControllerBase {

	@Autowired
	private FirmBankFurikomiMatchingCrudService crudService;

	@Autowired
	private FirmBankFurikomiSearchService searchService;

	@Autowired
	private FirmBankFurikomiMatchingErrorCrudService errorService;

	@Autowired
	private BushoCrudService bushoCrudService;

	// transaction制御のため自クラスをinject
	@Autowired
	private U08B003Controller thisConroller;

	/**
	 * 全部署のFirmBank振込のマッチング.
	 * 
	 * <pre>
	 * 処理単位：
	 * ・伝送日付
	 * データ更新方針：
	 * ・全データ削除
	 * ・部署ごとにマッチング処理
	 * トランザクション：
	 * ・部署毎にコミット
	 * ・エラーが発生した場合、その部署の処理はロールバックして次の部署の処理に移る。
	 * </pre>
	 */
	@RequestMapping(value = "matchAll", method = RequestMethod.GET)
	@Transactional
	public ResponseEntity<?> matchAll(@ModelAttribute U08B003Form form, ModelAndViewExtended model) {
		// delete
		crudService.deleteAll();
		// 部署リスト
		List<Busho> bushoList = bushoCrudService.getDomains();
		// 伝送日付
		Date date = form.getDate();
		// 部署毎にマッチング処理
		int error = bushoList.stream().mapToInt(busho -> {
			U08B003Form _form = new U08B003Form();
			_form.setBushoCode(busho.getCode());
			_form.setDate(date);
			try {
				thisConroller.match(_form, model);
				logger.info("処理成功 部署コード： " + busho.getCode());
			} catch (Exception e) {
				logger.error("処理失敗 部署コード： " + busho.getCode(), e);
				return 1;
			}
			return 0;
		}).sum();
		// return
		if (error > 0) {
			String message = "エラー部署あり。件数 : " + error;
			logger.warn(message);
			form.setWarningMessage(message);
		} else {
			form.setSuccessMessage("全部署のFBマッチング成功");
		}
		model.addForm(form);
		return ResponseEntity.ok(model);
	}

	/**
	 * FirmBank振込のマッチング.
	 * 
	 * <pre>
	 * 処理単位：
	 * ・部署, 伝送日付毎
	 * データ更新方針：
	 * ・特記事項なし
	 * トランザクション：
	 * ・1件でもエラーの場合、コミットしない。
	 * </pre>
	 */
	@RequestMapping(value = "match", method = RequestMethod.GET)
	@Transactional(TxType.REQUIRES_NEW)
	public ResponseEntity<?> match(@ModelAttribute U08B003Form form, ModelAndViewExtended model) {
		// 部署
		Busho busho = bushoCrudService.getDomain(form.getBushoCode());
		// matching error
		TheDate date = new TheDate(form.getDate());
		// マッチングデータ抽出
		FBFurikomiMatchingResult result = searchService.searchMatched(busho, date);
		// マッチング成功
		result.getMatched().entrySet().stream().forEach(e -> {
			crudService.save(e.getKey(), e.getValue());
		});
		// マッチングエラー
		result.getMultipleMathed().stream().forEach(fbFurikomiId -> {
			errorService.save(fbFurikomiId, FurikomiMatchintErrorCause.複数マッチング);
		});
		result.getRepetition().stream().forEach(fbFurikomiId -> {
			errorService.save(fbFurikomiId, FurikomiMatchintErrorCause.同一振込);
		});
		// return
		form.setSuccessMessage("部署FBマッチング成功");
		model.addForm(form);
		return ResponseEntity.ok(model);
	}

	/**
	 * FirmBank振込のアンマッチングの登録.
	 * 
	 * <pre>
	 * 処理単位：
	 * ・伝送日付
	 * データ更新方針：
	 * ・特記事項なし
	 * トランザクション：
	 * ・1件でもエラーの場合、コミットしない。
	 * </pre>
	 */
	@RequestMapping(value = "unmatch", method = RequestMethod.GET)
	@Transactional
	public ResponseEntity<?> unmatch(@ModelAttribute U08B003Form form, ModelAndViewExtended model) {
		// アンマッチ
		TheDate date = new TheDate(form.getDate());
		List<String> unmatchedList = searchService.searchUnmatched(date);
		unmatchedList.stream().forEach(fbFurikomiId -> {
			errorService.save(fbFurikomiId, FurikomiMatchintErrorCause.マッチング対象なし);
		});
		// return
		form.setSuccessMessage("FBアンマッチ処理");
		model.addForm(form);
		return ResponseEntity.ok(model);
	}
}
