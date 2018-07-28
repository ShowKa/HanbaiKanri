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
import com.showka.domain.Seikyu;
import com.showka.service.crud.u08.i.FirmBankFuriwakeCrudService;
import com.showka.service.crud.z00.i.BushoCrudService;
import com.showka.service.search.u07.i.SeikyuSearchService;
import com.showka.web.ControllerBase;
import com.showka.web.ModelAndViewExtended;

@Controller
@RequestMapping("api/u08b002")
public class U08B002Controller extends ControllerBase {

	@Autowired
	private FirmBankFuriwakeCrudService firmBankFuriwakeCrudService;

	@Autowired
	private SeikyuSearchService seikyuSearchService;

	@Autowired
	private BushoCrudService bushoCrudService;

	/**
	 * FirmBank振分データ作成.
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
	public ResponseEntity<?> fbFuriwake(@ModelAttribute U08B002Form form, ModelAndViewExtended model) {
		// delete
		firmBankFuriwakeCrudService.deleteAll();
		// search 請求・売掛
		Busho busho = bushoCrudService.getDomain(form.getBushoCode());
		List<Seikyu> seikyuList = seikyuSearchService.getAllOf(busho);
		// save FB振分
		firmBankFuriwakeCrudService.save(seikyuList);
		// return
		form.setSuccessMessage("FB振分データ作成成功");
		model.addForm(form);
		return ResponseEntity.ok(model);
	}
}
