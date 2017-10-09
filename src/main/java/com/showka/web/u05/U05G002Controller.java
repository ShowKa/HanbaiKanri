package com.showka.web.u05;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.showka.domain.KokyakuDomain;
import com.showka.domain.UriageDomain;
import com.showka.domain.UriageMeisaiDomain;
import com.showka.entity.TUriagePK;
import com.showka.service.crud.u01.i.KokyakuCrudService;
import com.showka.service.crud.u05.i.UriageCrudService;
import com.showka.web.Mode;
import com.showka.web.ModelAndViewExtended;

@Controller
@EnableAutoConfiguration
public class U05G002Controller {

	@Autowired
	private KokyakuCrudService kokyakuCrudService;

	@Autowired
	private UriageCrudService uriageCrudService;

	/**
	 * 参照モード初期表示
	 *
	 */
	@RequestMapping(value = "/u01g002/refer", method = RequestMethod.GET)
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
}
