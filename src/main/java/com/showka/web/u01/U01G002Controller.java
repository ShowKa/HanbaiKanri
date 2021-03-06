package com.showka.web.u01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

import com.showka.domain.builder.KokyakuBuilder;
import com.showka.domain.builder.NyukinKakeInfoBuilder;
import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u01.NyukinKakeInfo;
import com.showka.domain.z00.Busho;
import com.showka.kubun.HanbaiKubun;
import com.showka.kubun.KokyakuKubun;
import com.showka.kubun.NyukinHohoKubun;
import com.showka.kubun.NyukinTsukiKubun;
import com.showka.kubun.i.Kubun;
import com.showka.service.crud.u01.i.KokyakuCrud;
import com.showka.service.crud.z00.i.BushoCrud;
import com.showka.service.validator.u01.i.KokyakuValidator;
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
	private KokyakuCrud kokyakuCrud;

	@Autowired
	private BushoCrud bushoCrud;

	@Autowired
	private KokyakuValidator kokyakuValidator;

	// public method called by request
	/**
	 * 登録モード初期表示
	 */
	@RequestMapping(value = "/u01g002/registerForm", method = RequestMethod.GET)
	public ModelAndViewExtended registerForm(ModelAndViewExtended model) {
		model.addForm(new U01G002Form());
		setListToModelAttribute(model);
		model.setMode(Mode.REGISTER);
		model.setViewName("/u01/u01g002");
		return model;
	}

	/**
	 * 参照モード初期表示
	 */
	@RequestMapping(value = "/u01g002/refer", method = RequestMethod.GET)
	public ModelAndViewExtended refer(@Valid @ModelAttribute U01G002Form form, ModelAndViewExtended model) {
		// validate
		String code = form.getCode();
		kokyakuValidator.validateForRefer(code);
		// 顧客codeをもとに該当顧客の情報を取得し、画面に送る
		Kokyaku kokyaku = kokyakuCrud.getDomain(code);
		model.addForm(setForm(form, kokyaku));
		// 選択肢を取得して画面に送る
		setListToModelAttribute(model);
		// 入金サイトを取得して画面に送る
		Optional<NyukinKakeInfo> nyukinKakeInfo = kokyaku.getNyukinKakeInfo();
		if (nyukinKakeInfo.isPresent()) {
			model.addObject("nyukinSight", nyukinKakeInfo.get().getNyukinSight());
		}
		// モード情報を画面に送る。編集できないようにする
		model.setMode(Mode.READ);
		model.setViewName("/u01/u01g002");
		return model;
	}

	/**
	 * 更新モード初期表示
	 */
	@RequestMapping(value = "/u01g002/updateForm", method = RequestMethod.GET)
	public ModelAndViewExtended updateForm(@Valid @ModelAttribute U01G002Form form, ModelAndViewExtended model) {
		// 顧客codeをもとに該当顧客の情報を取得し、画面に送る
		Kokyaku kokyaku = kokyakuCrud.getDomain(form.getCode());
		model.addForm(setForm(form, kokyaku));
		// 選択肢を取得して画面に送る
		setListToModelAttribute(model);
		// モード情報を画面に送る。登録ボタンのリンク先を/u01g002/updateにしておく
		model.setMode(Mode.UPDATE);
		model.setViewName("/u01/u01g002");
		return model;
	}

	/**
	 * 新規登録
	 */
	@RequestMapping(value = "/u01g002/register", method = RequestMethod.POST)
	@Transactional
	public ResponseEntity<?> register(@Valid @ModelAttribute U01G002Form form, BindingResult result,
			ModelAndViewExtended model) {
		// make KokyakuDomain
		Kokyaku kokyakuDomain = createKokyakuDomain(form);
		// validate
		kokyakuValidator.validateForRegister(kokyakuDomain);
		kokyakuValidator.validate(kokyakuDomain);
		// register
		kokyakuCrud.save(kokyakuDomain);
		// jump refer
		form.setSuccessMessage("登録成功");
		model.addForm(form);
		return ResponseEntity.ok(model);
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/u01g002/update", method = RequestMethod.POST)
	@Transactional
	public ResponseEntity<?> update(@Valid @ModelAttribute U01G002Form form, BindingResult result,
			ModelAndViewExtended model) {
		// make KokyakuDomain
		Kokyaku kokyakuDomain = createKokyakuDomain(form);
		// validate
		kokyakuValidator.validate(kokyakuDomain);
		// update
		kokyakuCrud.save(kokyakuDomain);
		// jump refer
		form.setSuccessMessage("更新成功");
		model.addForm(form);
		return ResponseEntity.ok(model);
	}

	/**
	 * 削除
	 */
	@RequestMapping(value = "/u01g002/delete", method = RequestMethod.POST)
	@Transactional
	public ResponseEntity<?> delete(@Valid @ModelAttribute U01G002Form form, ModelAndViewExtended model) {
		// get 顧客
		Kokyaku kokyaku = kokyakuCrud.getDomain(form.getCode());
		kokyaku.setVersion(form.getKokyakuVersion());
		// validate
		kokyakuValidator.validateForDelete(form.getCode());
		// delete
		kokyakuCrud.delete(kokyaku);
		// jump search
		form.setSuccessMessage("削除成功");
		model.addForm(form);
		return ResponseEntity.ok(model);
	}

	// private method
	/**
	 * 部署の一覧、締日の候補日 の一覧を取得してmodelにセット
	 *
	 * @param model
	 *
	 */
	private void setListToModelAttribute(ModelAndViewExtended model) {
		// 部署一覧
		model.addObject("bushoList", bushoCrud.getMBushoList());
		// 締日の候補日
		List<Integer> shimebiList = new ArrayList<Integer>(Arrays.asList(5, 10, 15, 20, 25, 30));
		model.addObject("shimebiList", shimebiList);
	}

	/**
	 * formの内容をNyukinKakeInfoDomainBuilderにセットする。versionは別途設定する必要がある。
	 */
	private NyukinKakeInfo createNyukinKakeInfoDomain(U01G002Form form) {
		NyukinKakeInfoBuilder nyukinKakeInfoBuilder = new NyukinKakeInfoBuilder();
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
	 */
	private Kokyaku createKokyakuDomain(U01G002Form form) {
		// values
		final String kokyakuCode = form.getCode();
		HanbaiKubun hanbaiKubun = Kubun.get(HanbaiKubun.class, form.getHanbaiKubun());
		// builder
		KokyakuBuilder kokyakuDomainBuilder = new KokyakuBuilder();
		kokyakuDomainBuilder.withCode(kokyakuCode);
		kokyakuDomainBuilder.withName(form.getName());
		kokyakuDomainBuilder.withAddress(form.getAddress());
		kokyakuDomainBuilder.withKokyakuKubun(Kubun.get(KokyakuKubun.class, form.getKokyakuKubun()));
		kokyakuDomainBuilder.withHanbaiKubun(hanbaiKubun);
		kokyakuDomainBuilder.withShukanBusho(bushoCrud.getDomain(form.getShukanBushoCode()));
		kokyakuDomainBuilder.withRecordId(form.getKokyakuRecordId());
		// 入金掛売り情報は販売区分=掛売の時のみ
		if (hanbaiKubun == HanbaiKubun.掛売) {
			kokyakuDomainBuilder.withNyukinKakeInfo(createNyukinKakeInfoDomain(form));
		}
		kokyakuDomainBuilder.withVersion(form.getKokyakuVersion());
		return kokyakuDomainBuilder.build();
	}

	/**
	 * KokyakuDomainの情報を、U01G002Formにセットして返す
	 */
	private U01G002Form setForm(U01G002Form form, Kokyaku kokyaku) {
		// 主幹部署
		Busho busho = kokyaku.getShukanBusho();
		// 入金掛情報
		Optional<NyukinKakeInfo> _nyukinKakeInfo = kokyaku.getNyukinKakeInfo();
		// 顧客
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
		if (_nyukinKakeInfo.isPresent()) {
			NyukinKakeInfo nyukinKakeInfo = _nyukinKakeInfo.get();
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
