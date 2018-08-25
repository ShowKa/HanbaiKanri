package com.showka.web.u08;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

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
	// TODO トランザクション制御未実装
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
		bushoList.parallelStream().forEach(busho -> {
			U08B003Form _form = new U08B003Form();
			_form.setBushoCode(busho.getCode());
			_form.setDate(date);
			this.match(_form, model);
		});
		// return
		form.setSuccessMessage("全部署のFBマッチング成功");
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
	@Transactional
	public ResponseEntity<?> match(@ModelAttribute U08B003Form form, ModelAndViewExtended model) {
		// 部署
		Busho busho = bushoCrudService.getDomain(form.getBushoCode());
		// matching error
		TheDate date = new TheDate(form.getDate());
		// マッチングデータ抽出
		FBFurikomiMatchingResult result = searchService.searchMatched(busho, date);
		// マッチング成功
		result.getMatched().entrySet().parallelStream().forEach(e -> {
			crudService.save(e.getKey(), e.getValue());
		});
		// マッチングエラー
		result.getMultipleMathed().parallelStream().forEach(fbFurikomiId -> {
			errorService.save(fbFurikomiId, FurikomiMatchintErrorCause.複数マッチング);
		});
		result.getRepetition().parallelStream().forEach(fbFurikomiId -> {
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
		unmatchedList.parallelStream().forEach(fbFurikomiId -> {
			errorService.save(fbFurikomiId, FurikomiMatchintErrorCause.マッチング対象なし);
		});
		// return
		form.setSuccessMessage("FBアンマッチ処理");
		model.addForm(form);
		return ResponseEntity.ok(model);
	}
}
