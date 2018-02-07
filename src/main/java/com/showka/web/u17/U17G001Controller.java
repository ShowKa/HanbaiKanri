package com.showka.web.u17;

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

import com.showka.domain.BushoDomain;
import com.showka.service.crud.u17.i.BushoDateCrudService;
import com.showka.service.crud.z00.i.BushoCrudService;
import com.showka.service.validate.u17.i.BushoDateValidateService;
import com.showka.value.EigyoDate;
import com.showka.web.ControllerBase;
import com.showka.web.Mode;
import com.showka.web.ModelAndViewExtended;

@Controller
@EnableAutoConfiguration
public class U17G001Controller extends ControllerBase {

	@Autowired
	private BushoCrudService bushoCrudService;

	@Autowired
	private BushoDateValidateService bushoDateValidateService;

	@Autowired
	private BushoDateCrudService bushoDateCrudService;

	/**
	 * 参照.
	 *
	 */
	@RequestMapping(value = "/u17g001/refer", method = RequestMethod.GET)
	public ModelAndViewExtended refer(@ModelAttribute U17G001Form form, ModelAndViewExtended model) {
		// set model
		model.addForm(form);
		model.setMode(Mode.READ);
		model.setViewName("/u17/u17g001");
		// return
		return model;
	}

	/**
	 * 部署リスト取得.
	 *
	 */
	@RequestMapping(value = "/u17g001/getBushoList", method = RequestMethod.POST)
	public ResponseEntity<?> getBushoList(@ModelAttribute U17G001Form form, ModelAndViewExtended model) {
		// get 部署リスト
		List<BushoDomain> _bushoList = bushoCrudService.getDomains();
		// to map
		List<Map<String, Object>> bushoList = _bushoList.stream().map(b -> {
			Map<String, Object> ret = new HashMap<String, Object>();
			ret.put("code", b.getCode());
			ret.put("name", b.getName());
			ret.put("eigyoDate", b.getEigyoDate().toString());
			return ret;
		}).collect(Collectors.toList());
		// set model
		model.addObject("bushoList", bushoList);
		// set model
		model.addForm(form);
		// return
		return ResponseEntity.ok(model);
	}

	/**
	 * 締め.
	 *
	 */
	@RequestMapping(value = "/u17g001/close", method = RequestMethod.POST)
	public ResponseEntity<?> close(@ModelAttribute U17G001Form form, ModelAndViewExtended model) {
		// input
		String bushoCode = form.getBushoCode();
		BushoDomain busho = bushoCrudService.getDomain(bushoCode);
		EigyoDate eigyoDate = new EigyoDate(form.getEigyoDate());
		// validate
		bushoDateValidateService.validateForClosing(bushoCode, eigyoDate);
		// close
		bushoDateCrudService.toNextEigyoDate(busho);
		// set model
		form.setSuccessMessage("締め完了（部署コード : " + bushoCode + "）");
		model.addForm(form);
		// return
		return ResponseEntity.ok(model);
	}
}
