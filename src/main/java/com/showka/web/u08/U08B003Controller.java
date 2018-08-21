package com.showka.web.u08;

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
	 * FirmBank振込のマッチング.
	 * 
	 * <pre>
	 * 処理単位：
	 * ・部署
	 * データ更新方針：
	 * ・全件削除
	 * ・insert
	 * トランザクション：
	 * ・1件でもエラーの場合、コミットしない。
	 * </pre>
	 */
	@RequestMapping(method = RequestMethod.GET)
	@Transactional
	public ResponseEntity<?> match(@ModelAttribute U08B003Form form, ModelAndViewExtended model) {
		// busho
		Busho busho = bushoCrudService.getDomain(form.getBushoCode());
		// matching error
		TheDate date = new TheDate(form.getDate());
		// delete
		crudService.deleteAll();
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
		// アンマッチ
		List<String> unmatchedList = searchService.searchUnmatched(busho, date);
		unmatchedList.forEach(fbFurikomiId -> {
			errorService.save(fbFurikomiId, FurikomiMatchintErrorCause.マッチング対象なし);
		});
		// return
		form.setSuccessMessage("FBマッチング成功");
		model.addForm(form);
		return ResponseEntity.ok(model);
	}
}
