package com.showka.web.u07;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u07.Seikyu;
import com.showka.service.crud.u01.i.KokyakuCrud;
import com.showka.service.query.u07.i.SeikyuQuery;
import com.showka.web.ControllerBase;
import com.showka.web.MavMap;
import com.showka.web.Mode;
import com.showka.web.ModelAndViewExtended;

@Controller
@EnableAutoConfiguration
public class U07G001Controller extends ControllerBase {

	@Autowired
	private KokyakuCrud kokyakuCrud;

	@Autowired
	private SeikyuQuery seikyuQuery;

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
	@RequestMapping(value = "/u07g001/getList", method = RequestMethod.POST)
	public ResponseEntity<?> getList(@ModelAttribute U07G001Form form, ModelAndViewExtended model) {
		// get 顧客
		Kokyaku kokyaku = kokyakuCrud.getDomain(form.getKokyakuCode());
		// get 請求リスト
		List<Seikyu> _seikyuList = seikyuQuery.get(kokyaku);
		// to map
		List<MavMap> seikyuList = _seikyuList.stream().map(seikyu -> {
			MavMap ret = new MavMap();
			ret.put("seikyuDate", seikyu.getSeikyuDate().toString());
			ret.put("gokeiKingaku", seikyu.getGokeiKingaku().getFormatted());
			ret.put("shiharaiDate", seikyu.getShiharaiDate().toString());
			return ret;
		}).collect(Collectors.toList());
		// set model
		model.addObject("kokyakuName", kokyaku.getName());
		model.addObject("seikyuList", seikyuList);
		model.addForm(form);
		// return
		return ResponseEntity.ok(model);
	}
}
