package com.showka.web.u07;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.showka.domain.Kokyaku;
import com.showka.domain.Seikyu;
import com.showka.service.crud.u01.i.KokyakuCrudService;
import com.showka.service.search.u07.i.SeikyuSearchService;
import com.showka.web.ControllerBase;
import com.showka.web.Mode;
import com.showka.web.ModelAndViewExtended;

@Controller
@EnableAutoConfiguration
public class U07G001Controller extends ControllerBase {

	@Autowired
	private KokyakuCrudService kokyakuCrudService;

	@Autowired
	private SeikyuSearchService seikyuSearchService;

	/**
	 * 参照.
	 *
	 */
	@RequestMapping(value = "/u07g001/refer", method = RequestMethod.GET)
	public ModelAndViewExtended refer(@ModelAttribute U07G001Form form, ModelAndViewExtended model) {
		// set model
		model.addForm(form);
		model.setMode(Mode.READ);
		model.setViewName("/u07/u07g001");
		// return
		return model;
	}

	/**
	 * 顧客の請求全取得.
	 *
	 */
	@RequestMapping(value = "/u07g001/get", method = RequestMethod.POST)
	public ResponseEntity<?> getAll(@ModelAttribute U07G001Form form, ModelAndViewExtended model) {
		// get 顧客
		Kokyaku kokyaku = kokyakuCrudService.getDomain(form.getKokyakuCode());
		// get 請求リスト
		List<Seikyu> _seikyuList = seikyuSearchService.getAllOf(kokyaku);
		// to map
		List<Map<String, Object>> seikyuList = _seikyuList.stream().map(seikyu -> {
			Map<String, Object> ret = new HashMap<String, Object>();
			ret.put("seikyuDate", seikyu.getSeikyuDate().toString());
			ret.put("gokeiKingaku", seikyu.getGokeiKingaku());
			ret.put("shiharaiDate", seikyu.getShiharaiDate().toString());
			return ret;
		}).collect(Collectors.toList());
		// set model
		model.addObject("seikyuList", seikyuList);
		model.addForm(form);
		// return
		return ResponseEntity.ok(model);
	}
}
