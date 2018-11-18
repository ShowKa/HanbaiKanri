package com.showka.web.u08;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.showka.domain.u07.Seikyu;
import com.showka.domain.z00.Busho;
import com.showka.service.persistence.u08.i.FirmBankFuriwakePersistence;
import com.showka.service.persistence.z00.i.BushoPersistence;
import com.showka.service.query.u07.i.SeikyuQuery;
import com.showka.web.ControllerBase;
import com.showka.web.ModelAndViewExtended;

@Controller
@RequestMapping("api/u08b002")
public class U08B002Controller extends ControllerBase {

	@Autowired
	private FirmBankFuriwakePersistence firmBankFuriwakePersistence;

	@Autowired
	private SeikyuQuery seikyuQuery;

	@Autowired
	private BushoPersistence bushoPersistence;

	/**
	 * FirmBank振分データ作成.
	 * 
	 * <pre>
	 * 処理単位：
	 * ・部署
	 * データ更新方針：
	 * ・特になし。
	 * トランザクション：
	 * ・1件でもエラーの場合、コミットしない。
	 * </pre>
	 */
	@RequestMapping(method = RequestMethod.GET)
	@Transactional
	public ResponseEntity<?> fbFuriwake(@ModelAttribute U08B002Form form, ModelAndViewExtended model) {
		// search 請求・売掛
		Busho busho = bushoPersistence.getDomain(form.getBushoCode());
		List<Seikyu> seikyuList = seikyuQuery.getAllOf(busho);
		// save FB振分
		firmBankFuriwakePersistence.save(seikyuList);
		// return
		form.setSuccessMessage("FB振分データ作成成功");
		model.addForm(form);
		return ResponseEntity.ok(model);
	}
}
