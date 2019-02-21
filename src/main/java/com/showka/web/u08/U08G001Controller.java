package com.showka.web.u08;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.showka.domain.u08.Nyukin;
import com.showka.domain.u08.NyukinKeshikomi;
import com.showka.domain.z00.Shain;
import com.showka.kubun.NyukinHohoKubun;
import com.showka.kubun.i.Kubun;
import com.showka.service.crud.u01.i.KokyakuCrud;
import com.showka.service.crud.z00.i.BushoCrud;
import com.showka.service.crud.z00.i.ShainCrud;
import com.showka.service.search.u08.i.NyukinKeshikomiSearch;
import com.showka.service.search.u08.i.NyukinKeshikomiSearchParm;
import com.showka.value.AmountOfMoney;
import com.showka.value.EigyoDate;
import com.showka.web.ControllerBase;
import com.showka.web.MavMap;
import com.showka.web.Mode;
import com.showka.web.ModelAndViewExtended;

@Controller
@EnableAutoConfiguration
public class U08G001Controller extends ControllerBase {

	@Autowired
	private NyukinKeshikomiSearch nyukinKeshikomiSearch;

	@Autowired
	private BushoCrud bushoCrud;

	@Autowired
	private KokyakuCrud kokyakuCrud;

	@Autowired
	private ShainCrud shainCrud;

	@RequestMapping(value = "/u08g001/refer", method = RequestMethod.GET)
	public ModelAndViewExtended refer(@ModelAttribute U08G001Form form, ModelAndViewExtended model) {
		// init
		Shain user = super.getLoginShain();
		form.setBushoCode(user.getShozokuBusho().getCode());
		// set
		model.addForm(form);
		model.setViewName("/u08/u08g001");
		model.setMode(Mode.READ);
		model.addKubun("HanbaiHohoList", NyukinHohoKubun.values());
		return model;
	}

	/**
	 * 検索.
	 */
	@RequestMapping(value = "/u08g001/search", method = RequestMethod.POST)
	public ResponseEntity<?> search(@ModelAttribute U08G001Form form, ModelAndViewExtended model) {
		// parameters
		NyukinKeshikomiSearchParm param = new NyukinKeshikomiSearchParm();
		// 部署
		String bushoCode = form.getBushoCode();
		if (bushoCode != null) {
			boolean exists = bushoCrud.exists(bushoCode);
			if (exists) {
				param.setBusho(bushoCrud.getDomain(bushoCode));
			}
		}
		// 顧客
		String kokyakuCode = form.getKokyakuCode();
		if (kokyakuCode != null) {
			boolean exists = kokyakuCrud.exists(kokyakuCode);
			if (exists) {
				param.setKokyaku(kokyakuCrud.getDomain(kokyakuCode));
			}
		}
		// 担当社員
		String shainCode = form.getTantoShainCode();
		if (shainCode != null) {
			boolean exists = shainCrud.exists(shainCode);
			if (exists) {
				param.setTantoShain(shainCrud.getDomain(shainCode));
			}
		}
		// 入金方法
		List<NyukinHohoKubun> nyukinHohoList = new ArrayList<>();
		for (String _nyukinHoho : form.getNyukinHoho()) {
			nyukinHohoList.add(Kubun.get(NyukinHohoKubun.class, _nyukinHoho));
		}
		param.setNyukinHoho(nyukinHohoList);
		// others
		param.setIncludeKeshikomiDone(form.isIncludeKeshikomiDone());
		if (form.getMaxKingaku() != null) {
			param.setMaxKingaku(new AmountOfMoney(form.getMaxKingaku()));
		}
		if (form.getMinKingaku() != null) {
			param.setMinKingaku(new AmountOfMoney(form.getMinKingaku()));
		}
		if (form.getMaxNyukinDate() != null) {
			param.setMaxNyukinDate(new EigyoDate(form.getMaxNyukinDate()));
		}
		if (form.getMinNyukinDate() != null) {
			param.setMinNyukinDate(new EigyoDate(form.getMinNyukinDate()));
		}
		// search
		List<NyukinKeshikomi> nyukinList = nyukinKeshikomiSearch.search(param);
		// model
		List<MavMap> resultList = nyukinList.stream().map(nk -> {
			Nyukin n = nk.getNyukin();
			MavMap ret = new MavMap();
			ret.put("nyukinId", n.getRecordId());
			ret.put("kokyakuCode", n.getKokyaku().getCode());
			ret.put("kokyakuName", n.getKokyaku().getName());
			ret.put("bushoCode", n.getBusho().getCode());
			ret.put("bushoName", n.getBusho().getName());
			ret.put("nyukinDate", n.getDate());
			ret.put("nyukinHoho", n.getNyukinHohoKubun().name());
			ret.put("nyukinKingaku", n.getKingaku());
			ret.put("keshikomiKingaku", nk.getKeshikomiKingakuGokei());
			ret.put("keshikomiDone", nk.done());
			return ret;
		}).collect(Collectors.toList());
		// return
		model.addObject("nyukinList", resultList);
		model.addForm(form);
		return ResponseEntity.ok(model);
	}
}
