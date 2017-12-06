package com.showka.web.u05;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.showka.domain.KokyakuDomain;
import com.showka.domain.UriageDomain;
import com.showka.domain.UriageMeisaiDomain;
import com.showka.domain.builder.UriageDomainBuilder;
import com.showka.domain.builder.UriageMeisaiDomainBuilder;
import com.showka.entity.TUriagePK;
import com.showka.kubun.HanbaiKubun;
import com.showka.kubun.i.Kubun;
import com.showka.service.crud.u01.i.KokyakuCrudService;
import com.showka.service.crud.u05.i.UriageCrudService;
import com.showka.service.crud.z00.i.MShohinCrudService;
import com.showka.service.validate.u01.i.KokyakuValidateService;
import com.showka.service.validate.u05.i.UriageValidateService;
import com.showka.system.exception.NotExistException;
import com.showka.value.TaxRate;
import com.showka.value.TheDate;
import com.showka.web.Mode;
import com.showka.web.ModelAndViewExtended;

@Controller
@EnableAutoConfiguration
public class U05G002Controller {

	@Autowired
	private KokyakuCrudService kokyakuCrudService;

	@Autowired
	private KokyakuValidateService kokyakuValidateService;

	@Autowired
	private UriageCrudService uriageCrudService;

	@Autowired
	private UriageValidateService uriageValidateService;

	@Autowired
	private MShohinCrudService shohinCrudService;

	/** 税率. */
	private TaxRate ZEIRITSU = new TaxRate(0.08);

	// public method called by request
	/**
	 * 登録モード初期表示
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/u05g002/registerForm", method = RequestMethod.GET)
	public ModelAndViewExtended registerForm(ModelAndViewExtended model) {

		// 初期入力値設定
		U05G002Form form = new U05G002Form();
		form.setKokyakuCode("");
		form.setUriageDate(new Date());
		form.setDenpyoNumber("00000");
		form.setHanbaiKubun(HanbaiKubun.現金.getCode());
		model.addForm(form);

		// モード
		model.setMode(Mode.REGISTER);

		// view
		model.setViewName("/u05/u05g002");
		return model;
	}

	/**
	 * 登録モード初期表示
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/u05g002/updateForm", method = RequestMethod.POST)
	public ModelAndViewExtended updateForm(@ModelAttribute U05G002Form form, ModelAndViewExtended model) {

		// 初期入力値設定
		model = refer(form, model);

		// モード
		model.setMode(Mode.UPDATE);
		return model;
	}

	/**
	 * 参照.
	 *
	 */
	@RequestMapping(value = "/u05g002/refer", method = RequestMethod.GET)
	public ModelAndViewExtended refer(@ModelAttribute U05G002Form form, ModelAndViewExtended model) {

		// 顧客取得
		KokyakuDomain kokyaku = kokyakuCrudService.getDomain(form.getKokyakuCode());

		// 売上取得
		TUriagePK pk = new TUriagePK();
		pk.setDenpyoNumber(form.getDenpyoNumber());
		pk.setKokyakuId(kokyaku.getRecordId());
		UriageDomain u = uriageCrudService.getDomain(pk);

		// set form
		form.setHanbaiKubun(u.getHanbaiKubun().getCode());
		form.setUriageDate(u.getUriageDate().toDate());

		// set 明細
		List<U05G002MeisaiForm> meisaiList = new ArrayList<U05G002MeisaiForm>();
		for (UriageMeisaiDomain meisai : u.getUriageMeisai()) {
			U05G002MeisaiForm e = new U05G002MeisaiForm();
			e.setMeisaiNumber(meisai.getMeisaiNumber());
			e.setHanbaiNumber(meisai.getHanbaiNumber());
			e.setHanbaiTanka(meisai.getHanbaiTanka().intValue());
			e.setShohinCode(meisai.getShohinDomain().getCode());
			meisaiList.add(e);
		}
		form.setMeisai(meisaiList);

		// set model
		model.addForm(form);
		model.setMode(Mode.READ);
		model.setViewName("/u05/u05g002");
		return model;
	}

	/**
	 * 登録.
	 *
	 */
	@Transactional
	@RequestMapping(value = "/u05g002/register", method = RequestMethod.POST)
	public ResponseEntity<?> register(@ModelAttribute U05G002Form form, ModelAndViewExtended model) {

		// set id
		form.setRecordId(UUID.randomUUID().toString());
		List<U05G002MeisaiForm> meisai = form.getMeisai();
		meisai.forEach(m -> m.setRecordId(UUID.randomUUID().toString()));

		// meisai number
		AtomicInteger i = new AtomicInteger();
		meisai.forEach(m -> m.setMeisaiNumber(i.getAndIncrement()));

		// domain
		UriageDomain uriage = buildDomainFromForm(form);

		// validate
		uriageValidateService.validateForRegister(uriage);
		uriageValidateService.validate(uriage);

		// save
		uriageCrudService.save(uriage);

		// jump refer
		form.setSuccessMessage("登録成功");
		model.addForm(form);
		return ResponseEntity.ok(model);
	}

	/**
	 * 更新.
	 *
	 */
	@RequestMapping(value = "/u05g002/update", method = RequestMethod.GET)
	public ModelAndViewExtended update(@ModelAttribute U05G002Form form, ModelAndViewExtended model) {

		// domain
		UriageDomain uriage = buildDomainFromForm(form);

		// validate
		uriageValidateService.validate(uriage);

		// save
		uriageCrudService.save(uriage);

		// message
		form.setSuccessMessage("登録成功");

		// set model
		model.addForm(form);
		model.setMode(Mode.REGISTER);
		model.setViewName("/u05/u05g002");
		return model;
	}

	/**
	 * form から domain を生成する.
	 * 
	 * @param form
	 * @return
	 */
	private UriageDomain buildDomainFromForm(U05G002Form form) {

		// record_id 採番
		form.setRecordId(form.getRecordId() == null ? UUID.randomUUID().toString() : form.getRecordId());

		// 売上明細
		List<UriageMeisaiDomain> uriageMeisaiList = new ArrayList<UriageMeisaiDomain>();
		for (U05G002MeisaiForm mf : form.getMeisai()) {

			// record_id 採番
			mf.setRecordId(mf.getRecordId() == null ? UUID.randomUUID().toString() : mf.getRecordId());

			// build
			UriageMeisaiDomainBuilder mb = new UriageMeisaiDomainBuilder();
			mb.withHanbaiNumber(mf.getHanbaiNumber());
			mb.withHanbaiTanka(BigDecimal.valueOf(mf.getHanbaiTanka()));
			mb.withMeisaiNumber(mf.getMeisaiNumber());
			mb.withRecordId(mf.getRecordId());
			mb.withShohinDomain(shohinCrudService.getDomain(mf.getShohinCode()));
			mb.withUriageId(form.getRecordId());
			mb.withUriageId(form.getRecordId());
			mb.withVersion(mf.getVersion());
			UriageMeisaiDomain md = mb.build();

			// add list
			uriageMeisaiList.add(md);
		}

		// 顧客
		KokyakuDomain kokyaku = kokyakuCrudService.getDomain(form.getKokyakuCode());

		// 売上
		UriageDomainBuilder ub = new UriageDomainBuilder();
		ub.withUriageMeisai(uriageMeisaiList);
		ub.withDenpyoNumber(form.getDenpyoNumber());
		ub.withHanbaiKubun(Kubun.get(HanbaiKubun.class, form.getHanbaiKubun()));
		ub.withRecordId(form.getRecordId());
		ub.withShohizeiritsu(ZEIRITSU);
		ub.withUriageDate(new TheDate(form.getUriageDate()));
		ub.withVersion(form.getVersion());
		ub.withKokyaku(kokyaku);
		UriageDomain uriage = ub.build();

		return uriage;
	}

	/**
	 * Validate Header.
	 *
	 */
	@RequestMapping(value = "/u05g002/validateHeader", method = RequestMethod.POST)
	public ResponseEntity<?> validateHeader(@ModelAttribute U05G002Form form, ModelAndViewExtended model) {
		kokyakuValidateService.validateForRefer(form.getKokyakuCode());
		UriageDomain d = buildDomainFromForm(form);
		uriageValidateService.validateForRegister(d);
		model.addForm(form);
		return ResponseEntity.ok(model);
	}

	/**
	 * Validate Meisai.
	 *
	 */
	@RequestMapping(value = "/u05g002/validateMeisai", method = RequestMethod.POST)
	public ResponseEntity<?> validateMeisai(@ModelAttribute U05G002Form form, ModelAndViewExtended model) {

		// TODO
		List<U05G002MeisaiForm> meisaiList = form.getMeisai();
		meisaiList.forEach(m -> {
			String shohinCode = m.getShohinCode();
			try {
				shohinCrudService.getDomain(shohinCode);
			} catch (Exception e) {
				throw new NotExistException("商品", shohinCode);
			}
		});

		UriageDomain d = buildDomainFromForm(form);
		uriageValidateService.validate(d);
		model.addForm(form);
		return ResponseEntity.ok(model);
	}
}
