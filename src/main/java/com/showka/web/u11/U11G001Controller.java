package com.showka.web.u11;

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

import com.showka.domain.Busho;
import com.showka.domain.Shohin;
import com.showka.domain.ShohinZaiko;
import com.showka.service.crud.u11.i.ShohinZaikoCrudService;
import com.showka.service.crud.z00.i.BushoCrudService;
import com.showka.service.crud.z00.i.ShohinCrudService;
import com.showka.value.TheDate;
import com.showka.web.ControllerBase;
import com.showka.web.Mode;
import com.showka.web.ModelAndViewExtended;

@Controller
@EnableAutoConfiguration
public class U11G001Controller extends ControllerBase {

	@Autowired
	private ShohinZaikoCrudService shohinZaikoCrudService;

	@Autowired
	private BushoCrudService bushoCrudService;

	@Autowired
	private ShohinCrudService mShohinCrudService;

	/**
	 * 参照.
	 *
	 */
	@RequestMapping(value = "/u11g001/refer", method = RequestMethod.GET)
	public ModelAndViewExtended refer(@ModelAttribute U11G001Form form, ModelAndViewExtended model) {
		// set 部署コード
		form.setBushoCode(getLoginShain().getShozokuBusho().getCode());
		form.setDate(getLoginShain().getShozokuBusho().getEigyoDate().toDate());
		// set model
		model.addForm(form);
		model.setMode(Mode.READ);
		model.setViewName("/u11/u11g001");
		// return
		return model;
	}

	/**
	 * 商品在庫全取得.
	 *
	 */
	@RequestMapping(value = "/u11g001/getAll", method = RequestMethod.POST)
	public ResponseEntity<?> getAll(@ModelAttribute U11G001Form form, ModelAndViewExtended model) {
		// get 商品在庫
		Busho busho = bushoCrudService.getDomain(form.getBushoCode());
		List<ShohinZaiko> _zaikoList = shohinZaikoCrudService.getShohinZaiko(busho, new TheDate(form.getDate()));
		// to map
		List<Map<String, Object>> zaikoList = _zaikoList.stream().map(z -> {
			Map<String, Object> ret = new HashMap<String, Object>();
			ret.put("code", z.getShohin().getCode());
			ret.put("name", z.getShohin().getName());
			ret.put("number", z.getNumber());
			return ret;
		}).collect(Collectors.toList());
		// set model
		model.addObject("zaikoList", zaikoList);
		model.addObject("bushoName", busho.getName());
		model.addForm(form);
		// return
		return ResponseEntity.ok(model);
	}

	/**
	 * 商品在庫取得.
	 *
	 */
	@RequestMapping(value = "/u11g001/get", method = RequestMethod.POST)
	public ResponseEntity<?> get(@ModelAttribute U11G001FormForShohinIdoMeisai form, ModelAndViewExtended model) {
		// get 商品在庫
		Busho busho = bushoCrudService.getDomain(form.getBushoCode());
		Shohin shohin = mShohinCrudService.getDomain(form.getShohinCode());
		ShohinZaiko _zaiko = shohinZaikoCrudService.getShohinZaiko(busho, new TheDate(form.getDate()), shohin);
		// to map
		List<Map<String, Object>> shohinIdoList = _zaiko.getShohinIdoList().stream().map(z -> {
			Map<String, Object> ret = new HashMap<String, Object>();
			ret.put("timestamp", z.getTimestamp().toString("HH:mm:ss"));
			ret.put("kubun", z.getKubun().name());
			ret.put("number", z.getIncreaseOrDecreaseNumber());
			return ret;
		}).collect(Collectors.toList());
		// set model
		model.addObject("shohinIdoList", shohinIdoList);
		model.addObject("shohinName", shohin.getName());
		model.addObject("kurikoshiZaiko", _zaiko.getKurikoshiNumber());
		model.addForm(form);
		// return
		return ResponseEntity.ok(model);
	}
}
