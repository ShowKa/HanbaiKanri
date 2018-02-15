package com.showka.web.u05;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.showka.domain.Kokyaku;
import com.showka.domain.Uriage;
import com.showka.domain.UriageMeisai;
import com.showka.domain.UriageRireki;
import com.showka.domain.builder.UriageBuilder;
import com.showka.domain.builder.UriageMeisaiBuilder;
import com.showka.entity.TUriagePK;
import com.showka.kubun.HanbaiKubun;
import com.showka.kubun.i.Kubun;
import com.showka.service.crud.u01.i.KokyakuCrudService;
import com.showka.service.crud.u05.i.UriageCrudService;
import com.showka.service.crud.u05.i.UriageMeisaiCrudService;
import com.showka.service.crud.u05.i.UriageRirekiCrudService;
import com.showka.service.crud.z00.i.ShohinCrudService;
import com.showka.service.specification.u05.i.UriageKeijoSpecificationService;
import com.showka.service.validate.u01.i.KokyakuValidateService;
import com.showka.service.validate.u05.i.UriageValidateService;
import com.showka.system.exception.NotExistException;
import com.showka.value.TaxRate;
import com.showka.value.TheDate;
import com.showka.web.ControllerBase;
import com.showka.web.Mode;
import com.showka.web.ModelAndViewExtended;

@Controller
@EnableAutoConfiguration
public class U05G002Controller extends ControllerBase {

	@Autowired
	private KokyakuCrudService kokyakuCrudService;

	@Autowired
	private KokyakuValidateService kokyakuValidateService;

	@Autowired
	private UriageCrudService uriageCrudService;

	@Autowired
	private UriageValidateService uriageValidateService;

	@Autowired
	private UriageKeijoSpecificationService uriageKeijoSpecificationService;

	@Autowired
	private ShohinCrudService shohinCrudService;

	@Autowired
	private UriageMeisaiCrudService uriageMeisaiCrudService;

	@Autowired
	private UriageRirekiCrudService uriageRirekiCrudService;

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
		// ログインユーザーの所属部署の営業日をデフォルト値とする
		form.setUriageDate(getLoginShain().getShozokuBusho().getEigyoDate().toDate());
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
		Kokyaku kokyaku = kokyakuCrudService.getDomain(form.getKokyakuCode());

		// 売上取得
		TUriagePK pk = new TUriagePK();
		pk.setDenpyoNumber(form.getDenpyoNumber());
		pk.setKokyakuId(kokyaku.getRecordId());
		Uriage u = uriageCrudService.getDomain(pk);

		// set form
		form.setHanbaiKubun(u.getHanbaiKubun().getCode());
		form.setUriageDate(u.getUriageDate().toDate());
		form.setVersion(u.getVersion());

		// set 明細
		List<U05G002MeisaiForm> meisaiList = new ArrayList<U05G002MeisaiForm>();
		for (UriageMeisai meisai : u.getUriageMeisai()) {
			U05G002MeisaiForm e = new U05G002MeisaiForm();
			e.setMeisaiNumber(meisai.getMeisaiNumber());
			e.setHanbaiNumber(meisai.getHanbaiNumber());
			e.setHanbaiTanka(meisai.getHanbaiTanka().intValue());
			e.setShohinCode(meisai.getShohinDomain().getCode());
			e.setVersion(meisai.getVersion());
			meisaiList.add(e);
		}
		form.setMeisai(meisaiList);

		// set model
		model.addForm(form);
		model.setMode(Mode.READ);
		model.setViewName("/u05/u05g002");

		// 計上済み判定
		model.addObject("isKeijoZumi", uriageKeijoSpecificationService.isKeijoZumi(u));

		return model;
	}

	/**
	 * 登録.
	 *
	 */
	@Transactional
	@RequestMapping(value = "/u05g002/register", method = RequestMethod.POST)
	public ResponseEntity<?> register(@ModelAttribute U05G002Form form, ModelAndViewExtended model) {

		// meisai number
		AtomicInteger i = new AtomicInteger(1);
		List<U05G002MeisaiForm> meisai = form.getMeisai();
		meisai.forEach(m -> m.setMeisaiNumber(i.getAndIncrement()));

		// domain
		Uriage uriage = buildDomainFromForm(form);

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
	@Transactional
	@RequestMapping(value = "/u05g002/update", method = RequestMethod.POST)
	public ResponseEntity<?> update(@ModelAttribute U05G002Form form, ModelAndViewExtended model) {

		// 新しい売上明細に明細番号付番
		String uriageId = getUriageId(form.getKokyakuCode(), form.getDenpyoNumber());
		Integer maxMeisaiNumber = uriageMeisaiCrudService.getMaxMeisaiNumber(uriageId);
		AtomicInteger i = new AtomicInteger(maxMeisaiNumber + 1);
		form.getMeisai()
				.stream()
				.filter(m -> m.getMeisaiNumber() == null)
				.forEach(m -> m.setMeisaiNumber(i.getAndIncrement()));

		// domain
		Uriage uriage = buildDomainFromForm(form);

		// validate
		uriageValidateService.validateForUpdate(uriage);
		uriageValidateService.validate(uriage);

		// save
		uriageCrudService.save(uriage);

		// message
		form.setSuccessMessage("更新成功");

		// set model
		model.addForm(form);
		return ResponseEntity.ok(model);
	}

	/**
	 * delete.
	 *
	 */
	@Transactional
	@RequestMapping(value = "/u05g002/delete", method = RequestMethod.POST)
	public ResponseEntity<?> delete(@ModelAttribute U05G002Form form, ModelAndViewExtended model) {

		// domain
		TUriagePK pk = new TUriagePK();
		pk.setDenpyoNumber(form.getDenpyoNumber());
		Kokyaku kokyaku = kokyakuCrudService.getDomain(form.getKokyakuCode());
		pk.setKokyakuId(kokyaku.getRecordId());
		Uriage uriage = uriageCrudService.getDomain(pk);

		// 排他制御
		uriage.setVersion(form.getVersion());

		// validate
		uriageValidateService.validateForDelete(uriage);

		// delete
		uriageCrudService.delete(uriage);

		// message
		form.setSuccessMessage("削除成功");

		// set model
		model.addForm(form);
		return ResponseEntity.ok(model);
	}

	/**
	 * form から domain を生成する.
	 * 
	 * @param form
	 * @return
	 */
	private Uriage buildDomainFromForm(U05G002Form form) {

		// 売上明細
		List<UriageMeisai> uriageMeisaiList = new ArrayList<UriageMeisai>();
		for (U05G002MeisaiForm mf : form.getMeisai()) {

			// build
			UriageMeisaiBuilder mb = new UriageMeisaiBuilder();
			mb.withHanbaiNumber(mf.getHanbaiNumber());
			mb.withHanbaiTanka(BigDecimal.valueOf(mf.getHanbaiTanka()));
			mb.withMeisaiNumber(mf.getMeisaiNumber());
			mb.withShohinDomain(shohinCrudService.getDomain(mf.getShohinCode()));
			mb.withVersion(mf.getVersion());
			UriageMeisai md = mb.build();

			// add list
			uriageMeisaiList.add(md);
		}

		// 顧客
		Kokyaku kokyaku = kokyakuCrudService.getDomain(form.getKokyakuCode());

		// 営業日取得
		TheDate eigyoDate = kokyaku.getShukanBusho().getEigyoDate();

		// 売上
		UriageBuilder ub = new UriageBuilder();
		ub.withUriageMeisai(uriageMeisaiList);
		ub.withDenpyoNumber(form.getDenpyoNumber());
		ub.withHanbaiKubun(Kubun.get(HanbaiKubun.class, form.getHanbaiKubun()));
		ub.withShohizeiritsu(ZEIRITSU);
		ub.withUriageDate(new TheDate(form.getUriageDate()));
		ub.withKeijoDate(eigyoDate);
		ub.withVersion(form.getVersion());
		ub.withKokyaku(kokyaku);
		Uriage uriage = ub.build();

		return uriage;
	}

	/**
	 * Validate Header.
	 *
	 */
	@RequestMapping(value = "/u05g002/validateHeader", method = RequestMethod.POST)
	public ResponseEntity<?> validateHeader(@ModelAttribute U05G002Form form, ModelAndViewExtended model) {
		kokyakuValidateService.validateForRefer(form.getKokyakuCode());
		Uriage d = buildDomainFromForm(form);
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

		Uriage d = buildDomainFromForm(form);
		uriageValidateService.validate(d);
		model.addForm(form);
		return ResponseEntity.ok(model);
	}

	@Transactional
	@RequestMapping(value = "/u05g002/cancel", method = RequestMethod.POST)
	public ResponseEntity<?> cancel(@ModelAttribute U05G002Form form, ModelAndViewExtended model) {
		// domain
		TUriagePK pk = new TUriagePK();
		pk.setKokyakuId(kokyakuCrudService.getDomain(form.getKokyakuCode()).getRecordId());
		pk.setDenpyoNumber(form.getDenpyoNumber());
		// validate
		uriageValidateService.validateForCancel(pk);
		// cancel
		uriageCrudService.cancel(pk, form.getVersion());
		// message
		form.setSuccessMessage("売上伝票をキャンセルしました.");

		// set model
		model.addForm(form);
		return ResponseEntity.ok(model);
	}

	@RequestMapping(value = "/u05g002/getRireki", method = RequestMethod.POST)
	public ResponseEntity<?> getRireki(@ModelAttribute U05G002Form form, ModelAndViewExtended model) {
		// 履歴取得
		String uriageId = getUriageId(form.getKokyakuCode(), form.getDenpyoNumber());
		UriageRireki rireki = uriageRirekiCrudService.getUriageRirekiList(uriageId);

		// 画面に必要な履歴情報をmapに入れる。
		List<Map<String, Object>> rirekiList = new ArrayList<Map<String, Object>>();
		rireki.getAllWithTeiseiDenpyo().stream().forEach(uriage -> {
			uriage.getUriageMeisai().forEach(meisai -> {
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("shohinCode", meisai.getShohinDomain().getCode());
				m.put("hanbaiNumber", meisai.getHanbaiNumber());
				m.put("hanbaiTanka", meisai.getHanbaiTanka());
				m.put("meisaiNumber", meisai.getMeisaiNumber());
				rirekiList.add(m);
			});
		});

		// set model
		model.addObject("rirekiList", rirekiList);
		model.addForm(form);
		return ResponseEntity.ok(model);
	}

	/**
	 * 売上IDを取得.
	 * 
	 * @param kokyakuCode
	 *            顧客コード
	 * @param denpyoNumber
	 *            伝票番号
	 * @return 売上ID
	 */
	private String getUriageId(String kokyakuCode, String denpyoNumber) {
		return uriageCrudService.getDomain(kokyakuCode, denpyoNumber).getRecordId();
	}
}
