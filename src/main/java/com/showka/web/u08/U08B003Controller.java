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
import com.showka.domain.FBFurikomiMatched;
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
		// delete
		crudService.deleteAll();
		// matching error
		// FB振込：同一部署内に同一振込依頼人名が存在する場合 => エラー
		TheDate date = new TheDate(form.getDate());
		List<String> errorList1 = searchService.searchIraininRepetition(busho, date);
		errorList1.forEach(e -> {
			errorService.save(e, FurikomiMatchintErrorCause.振込依頼人名重複);
		});
		// FB振込:マッチする振分データが存在しない => エラー
		List<String> errorList2 = searchService.searchNotMatched(busho, date);
		errorList2.forEach(e -> {
			errorService.save(e, FurikomiMatchintErrorCause.マッチング対象なし);
		});
		// FB振込：マッチするデータが2つ以上ある => エラー
		List<String> errorList3 = searchService.searchMultipleMatched(busho, date);
		errorList3.forEach(e -> {
			errorService.save(e, FurikomiMatchintErrorCause.複数マッチング);
		});
		// matching
		List<FBFurikomiMatched> matchedList = searchService.searchMatched(busho, date);
		matchedList.forEach(crudService::save);
		// return
		form.setSuccessMessage("FBマッチング成功");
		model.addForm(form);
		return ResponseEntity.ok(model);
	}
}
