package com.showka.web.u01;

import java.util.Arrays;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
import com.showka.service.validate.u01.NyukinKakeInfoValidateServiceImpl;
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

	@Autowired
	private NyukinKakeInfoValidateServiceImpl nyukinKakeInfoValidateService;

	// public method called by request
	/**
	 * 登録モード初期表示
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/u01g002/registerForm", method = RequestMethod.GET)
	public String registerForm(Model model) {
		// すっからかんのフォームを表示する

		// 選択肢を取得して画面に送る
		setListToModelAttribute(model);

		// モード情報を画面に送る。登録ボタンのリンク先を/u01g002/registerにしておく
		model.addAttribute("mode", "register");

		return "/u01/u01g002";
	}

	/**
	 * 参照モード初期表示
	 *
	 * @param kokyakuCode
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/u01g002/refer", method = RequestMethod.GET)
	public String refer(@RequestParam String code, Model model) {

		// 顧客codeをもとに該当顧客の情報を全て取得
		KokyakuDomain kokyaku = kokyakuCrudService.getDomain(code);
		model.addAttribute("kokyaku", kokyaku);

		// 選択肢を取得して画面に送る
		setListToModelAttribute(model);

		// 入金サイトを取得して画面に送る
		NyukinKakeInfoDomain nyukinKakeInfo = kokyaku.getNyukinKakeInfo();
		if (nyukinKakeInfo != null) {
			model.addAttribute("nyukinDate", nyukinKakeInfo.getNyukinSight());
		}

		// モード情報を画面に送る。編集できないようにする
		model.addAttribute("mode", "read");

		return "/u01/u01g002";
	}

	/**
	 * 更新モード初期表示
	 *
	 * @param kokyakuCode
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/u01g002/updateForm", method = RequestMethod.GET)
	public String updateForm(@RequestParam String kokyakuCode, Model model) {

		// 顧客codeをもとに該当顧客の情報を全て取得
		KokyakuDomain kokyaku = kokyakuCrudService.getDomain(kokyakuCode);
		model.addAttribute("kokyaku", kokyaku);

		// 選択肢を取得して画面に送る
		setListToModelAttribute(model);

		// モード情報を画面に送る。登録ボタンのリンク先を/u01g002/updateにしておく
		model.addAttribute("mode", "update");

		return "/u01/u01g002";
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
		if (kokyakuDomain.getHanbaiKubun() == HanbaiKubun.掛売) {
			nyukinKakeInfoValidateService.validate(kokyakuDomain.getNyukinKakeInfo());
		}

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
	public ResponseEntity<?> update(@Valid @ModelAttribute U01G002Form form, BindingResult result,
			ModelAndViewExtended model) {

		// make KokyakuDomain
		KokyakuDomain kokyakuDomain = createKokyakuDomain(form);

		// validate
		kokyakuValidateService.validate(kokyakuDomain);
		if (kokyakuDomain.getHanbaiKubun() == HanbaiKubun.掛売) {
			nyukinKakeInfoValidateService.validate(kokyakuDomain.getNyukinKakeInfo());
		}

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
	 * 部署の一覧、顧客区分の一覧、販売区分の一覧、入金方法区分の一覧、 入金月区分の一覧を取得してmodelにセット
	 *
	 * @param model
	 *
	 */
	private void setListToModelAttribute(Model model) {

		// 部署一覧
		model.addAttribute("bushoList", bushoCrudService.getMBushoList());

		// 顧客区分
		KokyakuKubun[] kokyakuKubunArray = KokyakuKubun.values();
		model.addAttribute("kokyakuKubunList", Arrays.asList(kokyakuKubunArray));

		// 販売区分
		HanbaiKubun[] hanbaiKubunArray = HanbaiKubun.values();
		model.addAttribute("hanbaiKubunList", Arrays.asList(hanbaiKubunArray));

		// 入金方法区分
		NyukinHohoKubun[] nyukinHohoKubunArray = NyukinHohoKubun.values();
		model.addAttribute("nyukinHohoKubunList", Arrays.asList(nyukinHohoKubunArray));

		// 入金月区分
		NyukinTsukiKubun[] nyukinTsukiKubunArray = NyukinTsukiKubun.values();
		model.addAttribute("nyukinTsukiKubunList", Arrays.asList(nyukinTsukiKubunArray));

	}

	/**
	 * formの内容をNyukinKakeInfoDomainBuilderにセットする。versionは別途設定する必要がある。
	 *
	 * @param form
	 *
	 */
	private NyukinKakeInfoDomain createNyukinKakeInfoDomain(U01G002Form form) {

		final String kokyakuCode = form.getCode();

		NyukinKakeInfoDomainBuilder nyukinKakeInfoBuilder = new NyukinKakeInfoDomainBuilder();
		nyukinKakeInfoBuilder.withKokyakuId(kokyakuCode);
		nyukinKakeInfoBuilder.withNyukinDate(form.getNyukinDate());
		nyukinKakeInfoBuilder.withNyukinHohoKubun(Kubun.get(NyukinHohoKubun.class, form.getNyukinHohoKubun()));
		nyukinKakeInfoBuilder.withNyukinTsukiKubun(Kubun.get(NyukinTsukiKubun.class, form.getNyukinTsukiKubun()));
		nyukinKakeInfoBuilder.withShimeDate(form.getShimebi());
		nyukinKakeInfoBuilder.withRecordId(form.getNyukinKakeInfoRecordId());
		nyukinKakeInfoBuilder.withVersion(form.getNyukinKakeInfoVersion());
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

		KokyakuDomainBuilder kokyakuDomainBuilder = new KokyakuDomainBuilder();
		kokyakuDomainBuilder.withCode(kokyakuCode);
		kokyakuDomainBuilder.withName(form.getName());
		kokyakuDomainBuilder.withAddress(form.getAddress());
		kokyakuDomainBuilder.withKokyakuKubun(Kubun.get(KokyakuKubun.class, form.getKokyakuKubun()));
		kokyakuDomainBuilder.withHanbaiKubun(Kubun.get(HanbaiKubun.class, form.getHanbaiKubun()));
		kokyakuDomainBuilder.withShukanBusho(bushoCrudService.getDomain(form.getShukanBushoCode()));
		kokyakuDomainBuilder.withRecordId(form.getKokyakuRecordId());
		kokyakuDomainBuilder.withNyukinKakeInfo(createNyukinKakeInfoDomain(form));
		kokyakuDomainBuilder.withVersion(form.getKokyakuVersion());
		return kokyakuDomainBuilder.build();
	}

}
