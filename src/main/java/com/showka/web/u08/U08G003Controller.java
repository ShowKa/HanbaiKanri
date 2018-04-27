package com.showka.web.u08;

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
import com.showka.domain.Keshikomi;
import com.showka.domain.Nyukin;
import com.showka.domain.NyukinKeshikomi;
import com.showka.domain.Urikake;
import com.showka.domain.builder.KeshikomiBuilder;
import com.showka.domain.builder.NyukinKeshikomiBuilder;
import com.showka.service.crud.u05.i.UrikakeCrudService;
import com.showka.service.crud.u08.i.NyukinCrudService;
import com.showka.service.crud.u08.i.NyukinKeshikomiCrudService;
import com.showka.service.validate.u08.i.NyukinKeshikomiValidateService;
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

	@RequestMapping(value = "/u08g003/refer", method = RequestMethod.POST)
	public ResponseEntity<?> refer(@ModelAttribute U08G003Form form, ModelAndViewExtended model) {
		// get 入金消込
		NyukinKeshikomi nyukinKeshikomi = nyukinKeshikomiCrudService.getDomain(form.getNyukinId());
		// to model
		List<Map<String, Object>> keshikomiList = nyukinKeshikomi.getKeshikomiList().stream().map(keshikomi -> {
			Map<String, Object> ret = new HashMap<String, Object>();
			ret.put("keshikomiId", keshikomi.getRecordId());
			ret.put("urikakeId", keshikomi.getUrikakeId());
			ret.put("kingaku", keshikomi.getKingaku().intValue());
			ret.put("version", keshikomi.getVersion());
			return ret;
		}).collect(Collectors.toList());
		model.addObject("keshikomiList", keshikomiList);
		model.addForm(form);
		return ResponseEntity.ok(model);
	}

	/**
	 * 登録.
	 */
	@Transactional
	@RequestMapping(value = "/u08g003/register", method = RequestMethod.POST)
	public ResponseEntity<?> register(@ModelAttribute U08G003Form form, ModelAndViewExtended model) {
		// 入金
		Nyukin nyukin = nyukinCrudService.getDomain(form.getNyukinId());
		// OCC
		nyukin.setVersion(form.getVersion());
		// 部署
		Busho busho = nyukin.getBusho();
		// 営業日
		EigyoDate eigyoDate = busho.getEigyoDate();
		// 売掛消込
		List<Keshikomi> keshikomiList = form.getMeisai().stream().map(m -> {
			// 売掛
			String urikakeId = m.getUrikakeId();
			Urikake urikake = urikakeCrudService.getDomain(urikakeId);
			// build 消込
			KeshikomiBuilder b = new KeshikomiBuilder();
			b.withNyukin(nyukin);
			b.withUrikake(urikake);
			b.withDate(eigyoDate);
			b.withKingaku(m.getKingaku());
			b.withVersion(m.getVersion());
			return b.build();
		}).collect(Collectors.toList());
		// 入金消込
		NyukinKeshikomiBuilder b = new NyukinKeshikomiBuilder();
		b.withNyukin(nyukin);
		b.withKeshikomiList(keshikomiList);
		NyukinKeshikomi nyukinKeshikomi = b.build();
		// validate
		nyukinKeshikomiValidateService.validate(nyukinKeshikomi);
		// save
		nyukinKeshikomiCrudService.save(nyukinKeshikomi);
		// return model
		model.addForm(form);
		return ResponseEntity.ok(model);
	}
}
