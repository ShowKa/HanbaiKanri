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

import com.showka.domain.u11.ShohinZaiko;
import com.showka.domain.z00.Busho;
import com.showka.domain.z00.Shohin;
import com.showka.service.persistence.u11.i.ShohinZaikoPersistence;
import com.showka.service.persistence.z00.i.BushoPersistence;
import com.showka.service.persistence.z00.i.ShohinPersistence;
import com.showka.value.EigyoDate;
import com.showka.web.ControllerBase;
import com.showka.web.Mode;
import com.showka.web.ModelAndViewExtended;

@Controller
@EnableAutoConfiguration
public class U11G001Controller extends ControllerBase {

	@Autowired
	private ShohinZaikoPersistence shohinZaikoPersistence;

	@Autowired
	private BushoPersistence bushoPersistence;

	@Autowired
	private ShohinPersistence mShohinPersistence;

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
		Busho busho = bushoPersistence.getDomain(form.getBushoCode());
		List<ShohinZaiko> _zaikoList = shohinZaikoPersistence.getShohinZaiko(busho, new EigyoDate(form.getDate()));
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
		Busho busho = bushoPersistence.getDomain(form.getBushoCode());
		Shohin shohin = mShohinPersistence.getDomain(form.getShohinCode());
		ShohinZaiko _zaiko = shohinZaikoPersistence.getShohinZaiko(busho, new EigyoDate(form.getDate()), shohin);
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
