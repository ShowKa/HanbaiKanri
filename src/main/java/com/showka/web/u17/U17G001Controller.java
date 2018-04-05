package com.showka.web.u17;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.showka.domain.Busho;
import com.showka.domain.BushoUriage;
import com.showka.service.crud.u05.i.UriageKeijoCrudService;
import com.showka.service.crud.u07.i.SeikyuUrikakeCrudService;
import com.showka.service.crud.u11.i.ShohinZaikoCrudService;
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

	@Autowired
	private ShohinZaikoCrudService shohinZaikoCrudService;

	@Autowired
	private UriageKeijoCrudService uriageKeijoCrudService;

	@Autowired
	private SeikyuUrikakeCrudService seikyuUrikakeCrudService;

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
		List<Busho> _bushoList = bushoCrudService.getDomains();
		// to map
		List<Map<String, Object>> bushoList = _bushoList.stream().map(b -> {
			Map<String, Object> ret = new HashMap<String, Object>();
			ret.put("code", b.getCode());
			ret.put("name", b.getName());
			EigyoDate eigyoDate = b.getEigyoDate();
			ret.put("eigyoDate", eigyoDate.toString());
			// FIXME 前日の売上計上を取得
			BushoUriage bushoKeijo = uriageKeijoCrudService.getBushoUriage(b, eigyoDate.plusDays(-1));
			ret.put("uriageKeijo", bushoKeijo.getKeijoKingaku());
			ret.put("uriageKeijoTeisei", bushoKeijo.getTeiseiKingaku());
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
	@Transactional
	public ResponseEntity<?> close(@ModelAttribute U17G001Form form, ModelAndViewExtended model) {
		// input
		String bushoCode = form.getBushoCode();
		Busho busho = bushoCrudService.getDomain(bushoCode);
		EigyoDate eigyoDate = new EigyoDate(form.getEigyoDate());
		// validate
		bushoDateValidateService.validateForClosing(busho, eigyoDate);
		// 売上計上
		uriageKeijoCrudService.keijo(busho, eigyoDate);
		// 在庫繰越
		shohinZaikoCrudService.kurikoshi(busho, eigyoDate);
		// 請求
		seikyuUrikakeCrudService.seikyu(busho, eigyoDate);
		// close
		bushoDateCrudService.toNextEigyoDate(busho);
		// set model
		form.setSuccessMessage("締め完了（部署コード : " + bushoCode + ", 営業日 : " + eigyoDate + "）");
		model.addForm(form);
		// return
		return ResponseEntity.ok(model);
	}
}
