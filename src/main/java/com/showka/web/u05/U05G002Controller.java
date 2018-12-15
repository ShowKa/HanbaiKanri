package com.showka.web.u05;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.showka.domain.builder.UriageBuilder;
import com.showka.domain.builder.UriageMeisaiBuilder;
import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u05.Uriage;
import com.showka.domain.u05.UriageMeisai;
import com.showka.domain.u05.UriageRireki;
import com.showka.domain.u06.Urikake;
import com.showka.domain.u07.Seikyu;
import com.showka.entity.TUriagePK;
import com.showka.kubun.HanbaiKubun;
import com.showka.kubun.i.Kubun;
import com.showka.service.construct.u06.i.UrikakeConstruct;
import com.showka.service.crud.u01.i.KokyakuCrud;
import com.showka.service.crud.u05.i.UriageCrud;
import com.showka.service.crud.u05.i.UriageMeisaiCrud;
import com.showka.service.crud.u06.i.UrikakeCrud;
import com.showka.service.crud.z00.i.ShohinCrud;
import com.showka.service.persistence.u05.i.UriagePersistence;
import com.showka.service.persistence.u06.i.UrikakePersistence;
import com.showka.service.persistence.u11.i.ShohinIdoUriagePersistence;
import com.showka.service.query.u05.i.UriageKeijoQuery;
import com.showka.service.query.u05.i.UriageRirekiQuery;
import com.showka.service.query.u07.i.SeikyuQuery;
import com.showka.service.validator.u01.i.KokyakuValidator;
import com.showka.service.validator.u05.i.UriageValidator;
import com.showka.system.exception.validate.NotExistException;
import com.showka.value.EigyoDate;
import com.showka.value.TaxRate;
import com.showka.web.ControllerBase;
import com.showka.web.Mode;
import com.showka.web.ModelAndViewExtended;

@Controller
@EnableAutoConfiguration
public class U05G002Controller extends ControllerBase {

	@Autowired
	private KokyakuCrud kokyakuCrud;

	@Autowired
	private KokyakuValidator kokyakuValidator;

	@Autowired
	private UriageCrud uriageCrud;

	@Autowired
	private UriagePersistence uriagePersistence;

	@Autowired
	private UriageValidator uriageValidator;

	@Autowired
	private UriageKeijoQuery uriageKeijoQuery;

	@Autowired
	private ShohinCrud shohinCrud;

	@Autowired
	private UriageMeisaiCrud uriageMeisaiCrud;

	@Autowired
	private UriageRirekiQuery uriageRirekiQuery;

	@Autowired
	private ShohinIdoUriagePersistence shohinIdoUriagePersistence;

	@Autowired
	private UrikakeConstruct urikakeConstruct;

	@Autowired
	private UrikakeCrud urikakeCrud;

	@Autowired
	private UrikakePersistence urikakePersistence;

	@Autowired
	private SeikyuQuery seikyuQuery;

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
		Kokyaku kokyaku = kokyakuCrud.getDomain(form.getKokyakuCode());

		// 売上取得
		TUriagePK pk = new TUriagePK();
		pk.setDenpyoNumber(form.getDenpyoNumber());
		pk.setKokyakuId(kokyaku.getRecordId());
		Uriage u = uriageCrud.getDomain(pk);

		// 売上ID
		String uriageId = u.getRecordId();

		// set form
		form.setHanbaiKubun(u.getHanbaiKubun().getCode());
		form.setUriageDate(u.getUriageDate().toDate());
		form.setVersion(u.getVersion());
		form.setRecordId(uriageId);

		// set 明細
		List<U05G002MeisaiForm> meisaiList = new ArrayList<U05G002MeisaiForm>();
		for (UriageMeisai meisai : u.getUriageMeisai()) {
			U05G002MeisaiForm e = new U05G002MeisaiForm();
			e.setMeisaiNumber(meisai.getMeisaiNumber());
			e.setHanbaiNumber(meisai.getHanbaiNumber());
			e.setHanbaiTanka(meisai.getHanbaiTanka().intValue());
			e.setShohinCode(meisai.getShohinDomain().getCode());
			e.setVersion(meisai.getVersion());
			e.setRecordId(meisai.getRecordId());
			meisaiList.add(e);
		}
		form.setMeisai(meisaiList);

		// set 売掛
		boolean exists = urikakeCrud.exists(uriageId);
		if (exists) {
			Urikake urikake = urikakeCrud.getDomain(uriageId);
			form.setUrikakeVersion(urikake.getVersion());
			// 請求済み判定
			Optional<Seikyu> _seikyu = seikyuQuery.getNewest(urikake.getRecordId());
			if (_seikyu.isPresent()) {
				model.addObject("isSeikyuZumi", true);
			}
		}

		// set model
		model.addForm(form);
		model.setMode(Mode.READ);
		model.setViewName("/u05/u05g002");

		// 計上済み判定
		model.addObject("isKeijoZumi", uriageKeijoQuery.hasDone(u));

		return model;
	}

	/**
	 * 登録.
	 *
	 */
	@Transactional
	@RequestMapping(value = "/u05g002/register", method = RequestMethod.POST)
	public ResponseEntity<?> register(@ModelAttribute U05G002Form form, ModelAndViewExtended model) {

		// 明細番号
		AtomicInteger i = new AtomicInteger(1);
		List<U05G002MeisaiForm> meisai = form.getMeisai();
		meisai.forEach(m -> m.setMeisaiNumber(i.getAndIncrement()));

		// domain
		Uriage uriage = buildDomainFromForm(form);

		// validate
		uriageValidator.validateForRegister(uriage);
		uriageValidator.validate(uriage);

		// save
		uriageCrud.save(uriage);

		// 掛売
		Optional<Urikake> urikake = urikakeConstruct.by(uriage);
		urikake.ifPresent(u -> {
			urikakeCrud.save(u);
		});

		// 商品移動
		shohinIdoUriagePersistence.save(uriage);

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
		Integer maxMeisaiNumber = uriageMeisaiCrud.getMaxMeisaiNumber(form.getRecordId());
		AtomicInteger i = new AtomicInteger(maxMeisaiNumber + 1);
		form.getMeisai()
				.stream()
				.filter(m -> m.getMeisaiNumber() == null)
				.forEach(m -> m.setMeisaiNumber(i.getAndIncrement()));

		// domain
		Uriage uriage = buildDomainFromForm(form);

		// validate
		uriageValidator.validateForUpdate(uriage);
		uriageValidator.validate(uriage);

		// save
		uriageCrud.save(uriage);

		// 掛売
		Optional<Urikake> urikake = urikakeConstruct.by(uriage);
		urikake.ifPresent(u -> {
			// OCC
			u.setVersion(form.getUrikakeVersion());
			urikakeCrud.save(u);
		});

		// 商品移動
		shohinIdoUriagePersistence.save(uriage);

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
	// TODO revert delete 内部で分岐させるより初めからmethod分割しておくほうがよい。
	@Transactional
	@RequestMapping(value = "/u05g002/delete", method = RequestMethod.POST)
	public ResponseEntity<?> delete(@ModelAttribute U05G002Form form, ModelAndViewExtended model) {

		// 売上PK
		TUriagePK pk = new TUriagePK();
		pk.setDenpyoNumber(form.getDenpyoNumber());
		Kokyaku kokyaku = kokyakuCrud.getDomain(form.getKokyakuCode());
		pk.setKokyakuId(kokyaku.getRecordId());

		// 売上
		Uriage uriage = uriageCrud.getDomain(pk);
		uriage.setVersion(form.getVersion());

		// validate
		uriageValidator.validateForDelete(pk);

		// delete 商品移動
		shohinIdoUriagePersistence.delete(pk);

		// 売上履歴=1件 -> 一度も計上されていないで売上・売掛を削除。
		// 売上履歴>1件 -> 前回計上状態に差し戻す。
		UriageRireki rireki = uriageRirekiQuery.get(form.getRecordId());
		if (rireki.getList().size() == 1) {
			// delete
			urikakeCrud.deleteIfExists(form.getRecordId(), form.getUrikakeVersion());
			uriageCrud.delete(uriage);
		} else {
			// revert
			urikakePersistence.revert(form.getRecordId(), form.getUrikakeVersion());
			uriagePersistence.revert(pk, form.getVersion());
		}

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

		// record_id 採番
		form.setRecordId(form.getRecordId() == null ? "" : form.getRecordId());

		// 売上明細
		List<UriageMeisai> uriageMeisaiList = new ArrayList<UriageMeisai>();
		for (U05G002MeisaiForm mf : form.getMeisai()) {

			// record_id 採番
			String rec = mf.getRecordId();
			mf.setRecordId(StringUtils.isEmpty(rec) ? "" : rec);

			// build
			UriageMeisaiBuilder mb = new UriageMeisaiBuilder();
			mb.withHanbaiNumber(mf.getHanbaiNumber());
			mb.withHanbaiTanka(BigDecimal.valueOf(mf.getHanbaiTanka()));
			mb.withMeisaiNumber(mf.getMeisaiNumber());
			mb.withRecordId(mf.getRecordId());
			mb.withShohinDomain(shohinCrud.getDomain(mf.getShohinCode()));
			mb.withVersion(mf.getVersion());
			UriageMeisai md = mb.build();

			// add list
			uriageMeisaiList.add(md);
		}

		// 顧客
		Kokyaku kokyaku = kokyakuCrud.getDomain(form.getKokyakuCode());

		// 営業日取得
		EigyoDate eigyoDate = kokyaku.getShukanBusho().getEigyoDate();

		// 売上
		UriageBuilder ub = new UriageBuilder();
		ub.withUriageMeisai(uriageMeisaiList);
		ub.withDenpyoNumber(form.getDenpyoNumber());
		ub.withHanbaiKubun(Kubun.get(HanbaiKubun.class, form.getHanbaiKubun()));
		ub.withRecordId(form.getRecordId());
		ub.withShohizeiritsu(ZEIRITSU);
		ub.withUriageDate(new EigyoDate(form.getUriageDate()));
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
		kokyakuValidator.validateForRefer(form.getKokyakuCode());
		Uriage d = buildDomainFromForm(form);
		uriageValidator.validateForRegister(d);
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
				shohinCrud.getDomain(shohinCode);
			} catch (Exception e) {
				throw new NotExistException("商品", shohinCode);
			}
		});

		Uriage d = buildDomainFromForm(form);
		uriageValidator.validate(d);
		model.addForm(form);
		return ResponseEntity.ok(model);
	}

	@Transactional
	@RequestMapping(value = "/u05g002/cancel", method = RequestMethod.POST)
	public ResponseEntity<?> cancel(@ModelAttribute U05G002Form form, ModelAndViewExtended model) {
		// domain
		TUriagePK pk = new TUriagePK();
		pk.setKokyakuId(kokyakuCrud.getDomain(form.getKokyakuCode()).getRecordId());
		pk.setDenpyoNumber(form.getDenpyoNumber());
		// delete 商品移動
		shohinIdoUriagePersistence.delete(pk);
		// cancel 売掛
		String uriageId = form.getRecordId();
		if (urikakeCrud.exists(uriageId)) {
			Urikake urikake = urikakeCrud.getDomain(uriageId);
			urikake.setVersion(form.getUrikakeVersion());
			urikakePersistence.cancel(urikake);
		}
		// cancel
		uriageValidator.validateForCancel(pk);
		uriagePersistence.cancel(pk, form.getVersion());
		// message
		form.setSuccessMessage("売上伝票をキャンセルしました.");
		// set model
		model.addForm(form);
		return ResponseEntity.ok(model);
	}

	@RequestMapping(value = "/u05g002/getRireki", method = RequestMethod.POST)
	public ResponseEntity<?> getRireki(@ModelAttribute U05G002Form form, ModelAndViewExtended model) {
		// 履歴取得
		UriageRireki rireki = uriageRirekiQuery.get(form.getRecordId());

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
}