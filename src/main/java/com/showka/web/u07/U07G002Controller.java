package com.showka.web.u07;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u01.NyukinKakeInfo;
import com.showka.domain.u05.Uriage;
import com.showka.domain.u06.Urikake;
import com.showka.domain.u07.Seikyu;
import com.showka.domain.u07.SeikyuMeisai;
import com.showka.entity.TSeikyuPK;
import com.showka.kubun.NyukinHohoKubun;
import com.showka.service.crud.u01.i.KokyakuCrud;
import com.showka.service.crud.u07.i.SeikyuCrud;
import com.showka.value.Kakaku;
import com.showka.web.ControllerBase;
import com.showka.web.Mode;
import com.showka.web.ModelAndViewExtended;

@Controller
@EnableAutoConfiguration
public class U07G002Controller extends ControllerBase {

	@Autowired
	private KokyakuCrud kokyakuCrud;

	@Autowired
	private SeikyuCrud seikyuCrud;

	/**
	 * 参照.
	 *
	 */
	@RequestMapping(value = "/u07g002/refer", method = RequestMethod.GET)
	public ModelAndViewExtended refer(@ModelAttribute U07G002Form form, ModelAndViewExtended model) {
		// set model
		model.addForm(form);
		model.setMode(Mode.READ);
		model.setViewName("/u07/u07g002");
		// get 請求
		this.setSeikyu(form, model);
		// return
		return model;
	}

	/**
	 * 顧客の請求全取得し設定.
	 *
	 */
	private void setSeikyu(@ModelAttribute U07G002Form form, ModelAndViewExtended model) {
		// get 顧客
		Kokyaku kokyaku = kokyakuCrud.getDomain(form.getKokyakuCode());
		// 請求PK
		TSeikyuPK pk = new TSeikyuPK();
		pk.setKokyakuId(kokyaku.getRecordId());
		pk.setSeikyuDate(form.getSeikyuDate());
		// get 請求
		Seikyu seikyu = seikyuCrud.getDomain(pk);
		// set model
		model.addObject("kokyakuCode", kokyaku.getCode());
		model.addObject("kokyakuName", kokyaku.getName());
		model.addObject("kokyakuAddress", kokyaku.getAddress());
		model.addObject("seikyuDate", seikyu.getSeikyuDate());
		model.addObject("shiharaiDate", seikyu.getShiharaiDate());
		model.addObject("gokeiKingaku", seikyu.getGokeiKingaku().getFormatted());
		Optional<NyukinKakeInfo> _nyukinKakeInfo = kokyaku.getNyukinKakeInfo();
		NyukinHohoKubun nyukinHoho = NyukinHohoKubun.EMPTY;
		if (_nyukinKakeInfo.isPresent()) {
			nyukinHoho = _nyukinKakeInfo.get().getNyukinHohoKubun();
		}
		model.addObject("nyukinHoho", nyukinHoho.toString());
		// set model 明細
		List<SeikyuMeisai> seikyuMeisai = seikyu.getSeikyuMeisai();
		List<Map<String, Object>> meisaiList = seikyuMeisai.stream().map(m -> {
			Urikake urikake = m.getUrikake();
			Uriage uriage = urikake.getUriage();
			Kakaku uriageGokei = uriage.getUriageGokeiKakaku();
			Map<String, Object> ret = new HashMap<String, Object>();
			ret.put("denpyoNumber", uriage.getDenpyoNumber());
			ret.put("uriageDate", uriage.getUriageDate().toString());
			ret.put("zeikomiKakaku", uriageGokei.getZeikomi().getFormatted());
			ret.put("zeiKakaku", uriageGokei.getShohizei().getFormatted());
			ret.put("rate", uriageGokei.getShohizeiRate().toPercentage());
			return ret;
		}).collect(Collectors.toList());
		model.addObject("meisaiList", meisaiList);
	}
}
