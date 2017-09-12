package com.showka.web.u01;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.showka.domain.KokyakuDomain;
import com.showka.kubun.HanbaiKubun;
import com.showka.kubun.KokyakuKubun;
import com.showka.kubun.NyukinHohoKubun;
import com.showka.kubun.NyukinTsukiKubun;
import com.showka.service.crud.u01.KokyakuCrudServiceImpl;
import com.showka.service.crud.u01.NyukinKakeInfoCrudServiceImpl;
import com.showka.service.crud.z00.i.BushoCrudService;
import com.showka.service.validate.u01.KokyakuValidateServiceImpl;
import com.showka.service.validate.u01.NyukinKakeInfoValidateServiceImpl;

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
	private NyukinKakeInfoCrudServiceImpl nyukinKakeInfoCrudService;

	@Autowired
	private BushoCrudService bushoCrudService;

	@Autowired
	private KokyakuValidateServiceImpl kokyakuValidateService;

	@Autowired
	private NyukinKakeInfoValidateServiceImpl nyukinKakeInfoValidateService;

	// private method

	/**
	 * 顧客区分の一覧を取得
	 *
	 */
	private List<KokyakuKubun> getKokyakuKubunList() {
		KokyakuKubun[] kokyakuKubunArray = KokyakuKubun.values();
		return Arrays.asList(kokyakuKubunArray);
	}

	/**
	 * 販売区分の一覧を取得
	 *
	 */
	private List<HanbaiKubun> getHanbaiKubunList() {
		HanbaiKubun[] hanbaiKubunArray = HanbaiKubun.values();
		return Arrays.asList(hanbaiKubunArray);
	}

	/**
	 * 入金方法区分の一覧を取得
	 *
	 */
	private List<NyukinHohoKubun> getNyukinHohoKubunnList() {
		NyukinHohoKubun[] nyukinHohoKubunArray = NyukinHohoKubun.values();
		return Arrays.asList(nyukinHohoKubunArray);
	}

	/**
	 * 入金月区分の一覧を取得
	 *
	 */
	private List<NyukinTsukiKubun> getNyukinTsukiKubunList() {
		NyukinTsukiKubun[] nyukinTsukiKubunArray = NyukinTsukiKubun.values();
		return Arrays.asList(nyukinTsukiKubunArray);
	}

	// public method called by request
	/**
	 * 登録モード初期表示
	 *
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/u01g002/registerIndex", method = RequestMethod.GET)
	public String registerIndex(Model model, HttpSession session) {
		System.out.println("doing registerIndex");
		// すっからかんのフォームを表示する

		// 選択肢を取得して画面に送る
		// 部署一覧
		model.addAttribute("bushoList", bushoCrudService.getMBushoList());
		// 顧客区分一覧
		model.addAttribute("kokyakuKubunList", getKokyakuKubunList());
		// 販売区分一覧
		model.addAttribute("hanbaiKubunList", getHanbaiKubunList());
		// 入金方法区分一覧
		model.addAttribute("nyukinHohoKubunnList", getNyukinHohoKubunnList());
		// 入金方法区分一覧
		model.addAttribute("nyukinTsukiKubunnList", getNyukinTsukiKubunList());

		// モード情報を画面に送る。登録ボタンのリンク先を/u01g002/registerにしておく
		model.addAttribute("mode", "register");

		return "/u01/u01g002";
	}

	/**
	 * 参照モード初期表示
	 *
	 * @param kokyakuCode
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/u01g002/getIndex", method = RequestMethod.GET)
	public String getIndex(@RequestParam String kokyakuCode, Model model, HttpSession session) {
		System.out.println("doing getIndex");

		// 顧客codeをもとに該当顧客の情報を全て取得
		KokyakuDomain kokyaku = kokyakuCrudService.getDomain(kokyakuCode);
		model.addAttribute("kokyaku", kokyaku);

		// 選択肢を取得
		// 部署一覧
		model.addAttribute("bushoList", bushoCrudService.getMBushoList());
		// 顧客区分一覧
		model.addAttribute("kokyakuKubunList", getKokyakuKubunList());
		// 販売区分一覧
		model.addAttribute("hanbaiKubunList", getHanbaiKubunList());
		// 入金方法区分一覧
		model.addAttribute("nyukinHohoKubunnList", getNyukinHohoKubunnList());
		// 入金方法区分一覧
		model.addAttribute("nyukinTsukiKubunnList", getNyukinTsukiKubunList());

		model.addAttribute("nyukinSaito", kokyaku.getNyukinKakeInfo().getNyukinSight());

		// モード情報を画面に送る。編集できないようにする
		model.addAttribute("mode", "read");

		return "/u01/u01g002";
	}

	/**
	 * 更新モード初期表示
	 *
	 * @param kokyakuCode
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/u01g002/updateIndex", method = RequestMethod.GET)
	public String updateIndex(@RequestParam String kokyakuCode, Model model, HttpSession session) {
		System.out.println("doing updateIndex");

		// 顧客codeをもとに該当顧客の情報を全て取得
		KokyakuDomain kokyaku = kokyakuCrudService.getDomain(kokyakuCode);
		model.addAttribute("kokyaku", kokyaku);

		// 選択肢を取得
		// 部署一覧
		model.addAttribute("bushoList", bushoCrudService.getMBushoList());
		// 顧客区分一覧
		model.addAttribute("kokyakuKubunList", getKokyakuKubunList());
		// 販売区分一覧
		model.addAttribute("hanbaiKubunList", getHanbaiKubunList());
		// 入金方法区分一覧
		model.addAttribute("nyukinHohoKubunnList", getNyukinHohoKubunnList());
		// 入金方法区分一覧
		model.addAttribute("nyukinTsukiKubunnList", getNyukinTsukiKubunList());

		model.addAttribute("nyukinSaito", kokyaku.getNyukinKakeInfo().getNyukinSight());

		// モード情報を画面に送る。顧客codeを編集できないようにする
		// モード情報を画面に送る。登録ボタンのリンク先を/u01g002/updateにしておく
		model.addAttribute("mode", "update");

		return "/u01/u01g002";
	}

}
