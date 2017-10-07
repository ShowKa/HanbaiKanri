package com.showka.web.u01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.showka.domain.BushoDomain;
import com.showka.domain.KokyakuDomain;
import com.showka.domain.NyukinKakeInfoDomain;
import com.showka.domain.builder.KokyakuDomainBuilder;
import com.showka.domain.builder.NyukinKakeInfoDomainBuilder;
import com.showka.kubun.HanbaiKubun;
import com.showka.kubun.KokyakuKubun;
import com.showka.kubun.NyukinHohoKubun;
import com.showka.kubun.NyukinTsukiKubun;
import com.showka.kubun.i.Kubun;
import com.showka.service.crud.u01.KokyakuCrudServiceImpl;
import com.showka.service.crud.z00.i.BushoCrudService;
import com.showka.service.validate.u01.KokyakuValidateServiceImpl;
import com.showka.web.Mode;
import com.showka.web.ModelAndViewExtended;

/**
 * U01G002 顧客登録画面
 *
 * @author 25767
 *
 */
@Controller
@EnableAutoConfiguration
public class U01G002Controller {

	@Autowired
	private KokyakuCrudServiceImpl kokyakuCrudService;

	@Autowired
	private BushoCrudService bushoCrudService;

	@Autowired
	private KokyakuValidateServiceImpl kokyakuValidateService;

	// public method called by request
	/**
	 * 登録モード初期表示
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/u01g002/registerForm", method = RequestMethod.GET)
	public ModelAndViewExtended registerForm(ModelAndViewExtended model) {
		// すっからかんのフォームを表示する

		// 選択肢を取得して画面に送る
		setListToModelAttribute(model);

		// モード情報を画面に送る。登録ボタンのリンク先を/u01g002/registerにしておく
		model.setMode(Mode.REGISTER);

		// view
		model.setViewName("/u01/u01g002");
		return model;
	}

	/**
	 * 参照モード初期表示
	 *
	 * @param kokyakuCode
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/u01g002/refer", method = RequestMethod.GET)
	public ModelAndViewExtended refer(@Valid @ModelAttribute U01G002Form form, ModelAndViewExtended model) {

		// 顧客codeをもとに該当顧客の情報を取得し、画面に送る
		KokyakuDomain kokyaku = kokyakuCrudService.getDomain(form.getCode());
		model.addObject("kokyaku", setForm(kokyaku));

		// 選択肢を取得して画面に送る
		setListToModelAttribute(model);

		// 入金サイトを取得して画面に送る
		NyukinKakeInfoDomain nyukinKakeInfo = kokyaku.getNyukinKakeInfo();
		if (nyukinKakeInfo != null) {
			model.addObject("nyukinSight", nyukinKakeInfo.getNyukinSight());
		}

		// モード情報を画面に送る。編集できないようにする
		model.setMode(Mode.READ);

		model.setViewName("/u01/u01g002");
		return model;
	}

	/**
	 * 更新モード初期表示
	 *
	 * @param code
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/u01g002/updateForm", method = RequestMethod.GET)
	public ModelAndViewExtended updateForm(@Valid @ModelAttribute U01G002Form form, ModelAndViewExtended model) {

		// 顧客codeをもとに該当顧客の情報を取得し、画面に送る
		KokyakuDomain kokyaku = kokyakuCrudService.getDomain(form.getCode());
		model.addObject("kokyaku", setForm(kokyaku));

		// 選択肢を取得して画面に送る
		setListToModelAttribute(model);

		// モード情報を画面に送る。登録ボタンのリンク先を/u01g002/updateにしておく
		model.setMode(Mode.UPDATE);

		model.setViewName("/u01/u01g002");
		return model;
	}

	/**
	 * 新規登録
	 *
	 * @param form
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/u01g002/register", method = RequestMethod.GET)
	@Transactional
	public ResponseEntity<?> register(@Valid @ModelAttribute U01G002Form form, BindingResult result,
			ModelAndViewExtended model) {

		// set recordID
		form.setKokyakuRecordId(UUID.randomUUID().toString());
		form.setNyukinKakeInfoRecordId(UUID.randomUUID().toString());

		// make KokyakuDomain
		KokyakuDomain kokyakuDomain = createKokyakuDomain(form);

		// validate
		kokyakuValidateService.validateForRegister(kokyakuDomain);
		kokyakuValidateService.validate(kokyakuDomain);

		// register
		kokyakuCrudService.save(kokyakuDomain);

		// jump refer
		form.setInfoMessage("登録成功");
		model.addForm(form);
		return ResponseEntity.ok(model);
	}

	/**
	 * 更新
	 *
	 * @param form
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/u01g002/update", method = RequestMethod.GET)
	@Transactional
	public ResponseEntity<?> update(@Valid @ModelAttribute U01G002Form form, BindingResult result,
			ModelAndViewExtended model) {

		// set recordID
		if (form.getNyukinKakeInfoRecordId().isEmpty()) {
			form.setNyukinKakeInfoRecordId(UUID.randomUUID().toString());
		}

		// make KokyakuDomain
		KokyakuDomain kokyakuDomain = createKokyakuDomain(form);

		// validate
		kokyakuValidateService.validate(kokyakuDomain);

		// update
		kokyakuCrudService.save(kokyakuDomain);

		// jump refer
		form.setInfoMessage("");
		model.addForm(form);
		return ResponseEntity.ok(model);
	}

	/**
	 * 削除
	 *
	 * @param form
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/u01g002/delete", method = RequestMethod.GET)
	@Transactional
	public ResponseEntity<?> delete(@Valid @ModelAttribute U01G002Form form, ModelAndViewExtended model) {

		// make KokyakuDomain
		KokyakuDomain kokyakuDomain = createKokyakuDomain(form);

		// delete
		kokyakuCrudService.delete(kokyakuDomain);

		// jump search
		form.setInfoMessage("");
		model.addForm(form);
		return ResponseEntity.ok(model);

	}

	// private method

	/**
	 * 部署の一覧、顧客区分の一覧、販売区分の一覧、入金方法区分の一覧、入金月区分、締日の候補日 の一覧を取得してmodelにセット
	 *
	 * @param model
	 *
	 */
	private void setListToModelAttribute(ModelAndViewExtended model) {

		// 部署一覧
		model.addObject("bushoList", bushoCrudService.getMBushoList());

		// 顧客区分
		List<HashMap<String, String>> KokyakuKubunList = new ArrayList<HashMap<String, String>>();
		for (KokyakuKubun kubun : KokyakuKubun.values()) {
			HashMap<String, String> m = new HashMap<String, String>();
			m.put("code", kubun.getCode());
			m.put("name", kubun.toString());
			KokyakuKubunList.add(m);
		}
		model.addObject("kokyakuKubunList", KokyakuKubunList);

		// 販売区分
		List<HashMap<String, String>> HanbaiKubunList = new ArrayList<HashMap<String, String>>();
		for (HanbaiKubun kubun : HanbaiKubun.values()) {
			HashMap<String, String> m = new HashMap<String, String>();
			m.put("code", kubun.getCode());
			m.put("name", kubun.toString());
			HanbaiKubunList.add(m);
		}
		model.addObject("hanbaiKubunList", HanbaiKubunList);

		// 入金方法区分
		List<HashMap<String, String>> NyukinHohoKubunList = new ArrayList<HashMap<String, String>>();
		for (NyukinHohoKubun kubun : NyukinHohoKubun.values()) {
			HashMap<String, String> m = new HashMap<String, String>();
			m.put("code", kubun.getCode());
			m.put("name", kubun.toString());
			NyukinHohoKubunList.add(m);
		}
		model.addObject("nyukinHohoKubunList", NyukinHohoKubunList);

		// 入金月区分
		List<HashMap<String, String>> NyukinTsukiKubunList = new ArrayList<HashMap<String, String>>();
		for (NyukinTsukiKubun kubun : NyukinTsukiKubun.values()) {
			HashMap<String, String> m = new HashMap<String, String>();
			m.put("code", kubun.getCode());
			m.put("name", kubun.toString());
			NyukinTsukiKubunList.add(m);
		}
		model.addObject("nyukinTsukiKubunList", NyukinTsukiKubunList);

		// 締日の候補日
		List<Integer> shimebiList = new ArrayList<Integer>(Arrays.asList(5, 10, 15, 20, 25, 30));
		model.addObject("shimebiList", shimebiList);
	}

	/**
	 * formの内容をNyukinKakeInfoDomainBuilderにセットする。versionは別途設定する必要がある。
	 *
	 * @param form
	 *
	 */
	private NyukinKakeInfoDomain createNyukinKakeInfoDomain(U01G002Form form) {

		NyukinKakeInfoDomainBuilder nyukinKakeInfoBuilder = new NyukinKakeInfoDomainBuilder();
		nyukinKakeInfoBuilder.withKokyakuId(form.getKokyakuRecordId());
		nyukinKakeInfoBuilder.withNyukinDate(form.getNyukinDate());
		nyukinKakeInfoBuilder.withNyukinHohoKubun(Kubun.get(NyukinHohoKubun.class, form.getNyukinHohoKubun()));
		nyukinKakeInfoBuilder.withNyukinTsukiKubun(Kubun.get(NyukinTsukiKubun.class, form.getNyukinTsukiKubun()));
		nyukinKakeInfoBuilder.withShimeDate(form.getShimebi());
		nyukinKakeInfoBuilder.withRecordId(form.getNyukinKakeInfoRecordId());
		nyukinKakeInfoBuilder.withVersion(form.getNyukinKakeInfoVersion());
		System.out.println(form.getNyukinKakeInfoVersion());
		return nyukinKakeInfoBuilder.build();
	}

	/**
	 * formの内容をNyukinDomainBuilderにセットする。versionとnyukinKakeInfoは別途設定する必要がある。
	 *
	 * @param form
	 *
	 */
	private KokyakuDomain createKokyakuDomain(U01G002Form form) {

		final String kokyakuCode = form.getCode();
		HanbaiKubun hanbaiKubun = Kubun.get(HanbaiKubun.class, form.getHanbaiKubun());

		KokyakuDomainBuilder kokyakuDomainBuilder = new KokyakuDomainBuilder();
		kokyakuDomainBuilder.withCode(kokyakuCode);
		kokyakuDomainBuilder.withName(form.getName());
		kokyakuDomainBuilder.withAddress(form.getAddress());
		kokyakuDomainBuilder.withKokyakuKubun(Kubun.get(KokyakuKubun.class, form.getKokyakuKubun()));
		kokyakuDomainBuilder.withHanbaiKubun(hanbaiKubun);
		kokyakuDomainBuilder.withShukanBusho(bushoCrudService.getDomain(form.getShukanBushoCode()));
		kokyakuDomainBuilder.withRecordId(form.getKokyakuRecordId());

		// 入金掛売り情報は販売区分=掛売の時のみ
		if (hanbaiKubun == HanbaiKubun.掛売) {
			kokyakuDomainBuilder.withNyukinKakeInfo(createNyukinKakeInfoDomain(form));
		} else {
			// TODO set empty
			// kokyakuDomainBuilder.withNyukinKakeInfo(EmptyProxy.domain(NyukinKakeInfoDomain.class));
		}

		kokyakuDomainBuilder.withVersion(form.getKokyakuVersion());
		return kokyakuDomainBuilder.build();
	}

	/**
	 * KokyakuDomainの情報を、U01G002Formにセットして返す
	 *
	 * @param kokyaku
	 *
	 */
	private U01G002Form setForm(KokyakuDomain kokyaku) {

		U01G002Form form = new U01G002Form();
		BushoDomain busho = kokyaku.getShukanBusho();
		NyukinKakeInfoDomain nyukinKakeInfo = kokyaku.getNyukinKakeInfo();

		// kokyaku
		form.setCode(kokyaku.getCode());
		form.setName(kokyaku.getName());
		form.setAddress(kokyaku.getAddress());
		form.setKokyakuKubun(kokyaku.getKokyakuKubun().getCode());
		form.setHanbaiKubun(kokyaku.getHanbaiKubun().getCode());
		form.setKokyakuRecordId(kokyaku.getRecordId());
		form.setKokyakuVersion(kokyaku.getVersion());

		// busho
		form.setShukanBushoCode(busho.getCode());

		// kakeinfo
		if (!nyukinKakeInfo.isEmpty()) {
			form.setNyukinHohoKubun(nyukinKakeInfo.getNyukinHohoKubun().getCode());
			form.setNyukinTsukiKubun(nyukinKakeInfo.getNyukinTsukiKubun().getCode());
			form.setShimebi(nyukinKakeInfo.getShimeDate());
			form.setNyukinDate(nyukinKakeInfo.getNyukinDate());
			form.setNyukinKakeInfoRecordId(nyukinKakeInfo.getRecordId());
			form.setNyukinKakeInfoVersion(nyukinKakeInfo.getVersion());
		}

		return form;

	}
}
