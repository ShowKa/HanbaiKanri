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

import com.showka.domain.u17.BushoNyukin;
import com.showka.domain.u17.BushoUriage;
import com.showka.domain.z00.Busho;
import com.showka.service.crud.z00.i.BushoCrud;
import com.showka.service.persistence.u05.i.UriageKeijoPersistence;
import com.showka.service.persistence.u07.i.SeikyuUrikakePersistence;
import com.showka.service.persistence.u08.i.NyukinKeijoPersistence;
import com.showka.service.persistence.u11.i.ShohinZaikoPersistence;
import com.showka.service.persistence.u17.i.BushoDatePersistence;
import com.showka.service.query.u05.i.UriageKeijoQuery;
import com.showka.service.query.u08.i.NyukinKeijoQuery;
import com.showka.service.validator.u17.i.BushoDateValidator;
import com.showka.value.EigyoDate;
import com.showka.web.ControllerBase;
import com.showka.web.Mode;
import com.showka.web.ModelAndViewExtended;

@Controller
@EnableAutoConfiguration
public class U17G001Controller extends ControllerBase {

	@Autowired
	private BushoCrud bushoCrud;

	@Autowired
	private BushoDateValidator bushoDateValidator;

	@Autowired
	private BushoDatePersistence bushoDatePersistence;

	@Autowired
	private ShohinZaikoPersistence shohinZaikoPersistence;

	@Autowired
	private UriageKeijoPersistence uriageKeijoPersistence;

	@Autowired
	private UriageKeijoQuery uriageKeijoQuery;

	@Autowired
	private SeikyuUrikakePersistence seikyuUrikakePersistence;

	@Autowired
	private NyukinKeijoPersistence nyukinKeijoPersistence;

	@Autowired
	private NyukinKeijoQuery nyukinKeijoQuery;

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
		List<Busho> _bushoList = bushoCrud.getDomains();
		// to map
		List<Map<String, Object>> bushoList = _bushoList.stream().map(b -> {
			Map<String, Object> ret = new HashMap<String, Object>();
			ret.put("code", b.getCode());
			ret.put("name", b.getName());
			EigyoDate eigyoDate = b.getEigyoDate();
			ret.put("eigyoDate", eigyoDate.toString());
			ret.put("dayOfWeek", eigyoDate.getDayOfWeek());
			// FIXME 前日の売上計上を取得
			EigyoDate keijoDate = new EigyoDate(eigyoDate.plusDays(-1));
			BushoUriage bushoKeijo = uriageKeijoQuery.getBushoUriage(b, keijoDate);
			ret.put("uriageKeijo", bushoKeijo.getKeijoKingaku());
			ret.put("uriageKeijoTeisei", bushoKeijo.getTeiseiKingaku());
			// 入金の計上を取得
			BushoNyukin nyukinKeijo = nyukinKeijoQuery.getBushoNyukin(b, keijoDate);
			ret.put("keshikomiFurikomi", nyukinKeijo.getKeshikomiKingaku_Furikomi().intValue());
			ret.put("mishoriFurikomi", nyukinKeijo.getMishoriKingaku_Furikomi().intValue());
			ret.put("keshikomiGenkin", nyukinKeijo.getKeshikomiKingaku_Genkin().intValue());
			ret.put("mishoriGenkin", nyukinKeijo.getMishoriKingaku_Genkin().intValue());
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
		Busho busho = bushoCrud.getDomain(bushoCode);
		EigyoDate eigyoDate = new EigyoDate(form.getEigyoDate());
		// validate
		bushoDateValidator.validateForClosing(busho, eigyoDate);
		// 売上計上
		uriageKeijoPersistence.keijo(busho, eigyoDate);
		// 入金計上
		nyukinKeijoPersistence.keijo(busho, eigyoDate);
		// 在庫繰越
		shohinZaikoPersistence.kurikoshi(busho, eigyoDate);
		// 請求
		seikyuUrikakePersistence.seikyu(busho, eigyoDate);
		// close
		bushoDatePersistence.toNextEigyoDate(busho);
		// set model
		form.setSuccessMessage("締め完了（部署コード : " + bushoCode + ", 営業日 : " + eigyoDate + "）");
		model.addForm(form);
		// return
		return ResponseEntity.ok(model);
	}
}
