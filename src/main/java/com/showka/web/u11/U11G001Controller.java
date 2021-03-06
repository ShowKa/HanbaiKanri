package com.showka.web.u11;

import java.util.List;
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
import com.showka.kubun.ShohinIdoKubun;
import com.showka.service.crud.z00.i.BushoCrud;
import com.showka.service.crud.z00.i.ShohinCrud;
import com.showka.service.query.u11.i.ShohinZaikoQuery;
import com.showka.value.EigyoDate;
import com.showka.web.ControllerBase;
import com.showka.web.MavMap;
import com.showka.web.Mode;
import com.showka.web.ModelAndViewExtended;

@Controller
@EnableAutoConfiguration
public class U11G001Controller extends ControllerBase {

	@Autowired
	private ShohinZaikoQuery shohinZaikoQuery;

	@Autowired
	private BushoCrud bushoCrud;

	@Autowired
	private ShohinCrud shohinCrud;

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
		Busho busho = bushoCrud.getDomain(form.getBushoCode());
		List<ShohinZaiko> _zaikoList = shohinZaikoQuery.get(busho, new EigyoDate(form.getDate()));
		// to map
		List<MavMap> zaikoList = _zaikoList.stream().map(z -> {
			MavMap ret = new MavMap();
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
		Busho busho = bushoCrud.getDomain(form.getBushoCode());
		Shohin shohin = shohinCrud.getDomain(form.getShohinCode());
		ShohinZaiko _zaiko = shohinZaikoQuery.get(busho, new EigyoDate(form.getDate()), shohin);
		// to map
		List<MavMap> shohinIdoList = _zaiko.getShohinIdoList().stream().map(z -> {
			MavMap ret = new MavMap();
			ret.put("timestamp", z.getTimestamp().toString("HH:mm:ss"));
			ret.put("kubun", z.getKubun().name());
			ret.put("number", z.getNumberForBushoZaiko(_zaiko.getShohin()));
			// 入荷商品移動ID
			boolean isNyuka = z.getKubun() == ShohinIdoKubun.入荷;
			String nyukaShohinIdoId = isNyuka ? z.getRecordId() : "";
			ret.put("isNyuka", isNyuka);
			ret.put("nyukaShohinIdoId", nyukaShohinIdoId);
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
