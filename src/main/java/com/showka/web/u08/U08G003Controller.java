package com.showka.web.u08;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
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
import com.showka.domain.Keshikomi;
import com.showka.domain.Nyukin;
import com.showka.domain.NyukinKeshikomi;
import com.showka.domain.Urikake;
import com.showka.domain.builder.KeshikomiBuilder;
import com.showka.domain.builder.NyukinKeshikomiBuilder;
import com.showka.service.crud.u05.i.UrikakeCrudService;
import com.showka.service.crud.u08.i.NyukinCrudService;
import com.showka.service.crud.u08.i.NyukinKeshikomiCrudService;
import com.showka.service.specification.u06.i.UrikakeKeshikomiSpecificationService;
import com.showka.service.validate.u08.i.NyukinKeshikomiValidateService;
import com.showka.value.AmountOfMoney;
import com.showka.value.EigyoDate;
import com.showka.web.ControllerBase;
import com.showka.web.ModelAndViewExtended;

@Controller
@EnableAutoConfiguration
public class U08G003Controller extends ControllerBase {

	@Autowired
	private NyukinCrudService nyukinCrudService;

	@Autowired
	private UrikakeCrudService urikakeCrudService;

	@Autowired
	private NyukinKeshikomiCrudService nyukinKeshikomiCrudService;

	@Autowired
	private NyukinKeshikomiValidateService nyukinKeshikomiValidateService;

	@Autowired
	private UrikakeKeshikomiSpecificationService urikakeKeshikomiSpecificationService;

	@RequestMapping(value = "/u08g003/refer", method = RequestMethod.GET)
	public ModelAndViewExtended refer(@ModelAttribute U08G003Form form, ModelAndViewExtended model) {
		// get 入金消込
		NyukinKeshikomi nyukinKeshikomi = nyukinKeshikomiCrudService.getDomain(form.getNyukinId());
		// set form
		Nyukin nyukin = nyukinKeshikomi.getNyukin();
		form.setVersion(nyukin.getVersion());
		// set model
		// 入金
		model.addObject("nyukinKingaku", nyukin.getKingaku().intValue());
		model.addObject("kokyakuName", nyukin.getKokyaku().getName());
		model.addObject("nyukinHoho", nyukin.getNyukinHohoKubun().name());
		model.addObject("nyukinDate", nyukin.getDate().toString());
		model.addObject("bushoName", nyukin.getBusho().getName());
		// 消込リスト
		List<Map<String, Object>> keshikomiList = nyukinKeshikomi.getKeshikomiSet().stream().map(keshikomi -> {
			Map<String, Object> ret = new HashMap<String, Object>();
			// 消込
			ret.put("keshikomiId", keshikomi.getRecordId());
			ret.put("urikakeId", keshikomi.getUrikakeId());
			ret.put("kingaku", keshikomi.getKingaku().intValue());
			ret.put("version", keshikomi.getVersion());
			// 売掛
			Urikake urikake = keshikomi.getUrikake();
			ret.put("urikakeKingaku", urikake.getKingaku().intValue());
			// 残高
			AmountOfMoney zandaka = urikakeKeshikomiSpecificationService.getZandakaOf(urikake);
			ret.put("urikakeZandaka", zandaka.intValue());
			return ret;
		}).collect(Collectors.toList());
		model.addObject("keshikomiList", keshikomiList);
		model.addForm(form);
		model.setViewName("/u08/u08g003");
		return model;
	}

	/**
	 * 登録.
	 */
	@Transactional
	@RequestMapping(value = "/u08g003/register", method = RequestMethod.POST)
	public ResponseEntity<?> register(@ModelAttribute U08G003Form form, ModelAndViewExtended model) {
		// build 入金消込
		NyukinKeshikomi nyukinKeshikomi = this.buildNyukinKeshikomiFromForm(form);
		// 営業日
		EigyoDate eigyoDate = nyukinKeshikomi.getNyukinBushoEigyoDate();
		// validate
		nyukinKeshikomiValidateService.validate(nyukinKeshikomi);
		// save
		nyukinKeshikomiCrudService.save(eigyoDate, nyukinKeshikomi);
		// return model
		form.setSuccessMessage("登録成功");
		model.addForm(form);
		return ResponseEntity.ok(model);
	}

	/**
	 * 更新.
	 */
	@Transactional
	@RequestMapping(value = "/u08g003/update", method = RequestMethod.POST)
	public ResponseEntity<?> update(@ModelAttribute U08G003Form form, ModelAndViewExtended model) {
		// build 入金消込
		NyukinKeshikomi nyukinKeshikomi = this.buildNyukinKeshikomiFromForm(form);
		// get 入金消込更新前
		NyukinKeshikomi nyukinKeshikomiBeforeUpdate = nyukinKeshikomiCrudService.getDomain(form.getNyukinId());
		EigyoDate eigyoDate = nyukinKeshikomiBeforeUpdate.getNyukinBushoEigyoDate();
		nyukinKeshikomiBeforeUpdate.removeKeshikomiOf(eigyoDate);
		// merge 消込セット
		nyukinKeshikomi.mergeKeshikomiSet(nyukinKeshikomiBeforeUpdate);
		// validate
		nyukinKeshikomiValidateService.validate(nyukinKeshikomi);
		// save
		nyukinKeshikomiCrudService.save(eigyoDate, nyukinKeshikomi);
		// return model
		form.setSuccessMessage("更新成功");
		model.addForm(form);
		return ResponseEntity.ok(model);
	}

	/**
	 * キャンセル.
	 */
	@Transactional
	@RequestMapping(value = "/u08g003/cancel", method = RequestMethod.POST)
	public ResponseEntity<?> cancel(@ModelAttribute U08G003Form form, ModelAndViewExtended model) {
		// 消込対象の消込ID
		Set<String> target = form.getMeisai().stream().map(m -> {
			return m.getKeshikomiId();
		}).collect(Collectors.toSet());
		// 入金消込
		NyukinKeshikomi nyukinKeshikomi = nyukinKeshikomiCrudService.getDomain(form.getNyukinId());
		// OCC
		Nyukin nyukin = nyukinKeshikomi.getNyukin();
		nyukin.setVersion(form.getVersion());
		// 消込
		nyukinKeshikomiCrudService.cancel(nyukin, target);
		// return model
		model.addForm(form);
		return ResponseEntity.ok(model);
	}

	/**
	 * フォームから入金消込をビルドする.
	 * 
	 * @param form
	 *            フォーム
	 * @return 入金消込
	 */
	private NyukinKeshikomi buildNyukinKeshikomiFromForm(U08G003Form form) {
		// 入金
		Nyukin nyukin = nyukinCrudService.getDomain(form.getNyukinId());
		// OCC
		nyukin.setVersion(form.getVersion());
		// 部署
		Busho busho = nyukin.getBusho();
		// 営業日
		EigyoDate eigyoDate = busho.getEigyoDate();
		// 売掛消込
		Set<Keshikomi> keshikomiSet = form.getMeisai().stream().map(m -> {
			// 売掛
			String urikakeId = m.getUrikakeId();
			Urikake urikake = urikakeCrudService.getDomain(urikakeId);
			// FIXME
			String keshikomiId = m.getId() != null ? m.getId() : "dummy_" + UUID.randomUUID().toString();
			// build 消込
			KeshikomiBuilder b = new KeshikomiBuilder();
			b.withNyukin(nyukin);
			b.withUrikake(urikake);
			b.withDate(eigyoDate);
			b.withKingaku(m.getKingaku());
			b.withVersion(m.getVersion());
			b.withRecordId(keshikomiId);
			return b.build();
		}).collect(Collectors.toSet());
		// 入金消込
		NyukinKeshikomiBuilder b = new NyukinKeshikomiBuilder();
		b.withNyukin(nyukin);
		b.withKeshikomiSet(keshikomiSet);
		NyukinKeshikomi nyukinKeshikomi = b.build();
		return nyukinKeshikomi;
	}
}
