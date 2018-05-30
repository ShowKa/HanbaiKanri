package com.showka.web.u08;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.showka.domain.Busho;
import com.showka.domain.Urikake;
import com.showka.service.crud.u08.i.FirmBankFuriwakeCrudService;
import com.showka.service.crud.z00.i.BushoCrudService;
import com.showka.service.search.u05.i.UrikakeSearchService;
import com.showka.web.ControllerBase;
import com.showka.web.ModelAndViewExtended;

@Controller
@RequestMapping("api/u08b002")
public class U08B002Controller extends ControllerBase {

	@Autowired
	private FirmBankFuriwakeCrudService firmBankFuriwakeCrudService;

	@Autowired
	private UrikakeSearchService urikakeSearchService;

	@Autowired
	private BushoCrudService bushoCrudService;

	/**
	 * FirmBank振分データ作成.
	 * 
	 * <pre>
	 * </pre>
	 * 
	 * @param form
	 *            form
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> loadFB(@ModelAttribute U08B002Form form, ModelAndViewExtended model) {
		// delete
		firmBankFuriwakeCrudService.deleteAll();
		// urikake search
		Busho busho = bushoCrudService.getDomain(form.getBushoCode());
		List<Urikake> urikakeList = urikakeSearchService.search(busho);
		// return
		form.setSuccessMessage("FB振分データ作成成功");
		model.addForm(form);
		return ResponseEntity.ok(model);
	}
}
