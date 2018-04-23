package com.showka.web.u05;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.showka.domain.Kokyaku;
import com.showka.domain.Uriage;
import com.showka.service.crud.u01.i.KokyakuCrudService;
import com.showka.service.search.u05.UriageSearchCriteria;
import com.showka.service.search.u05.i.UriageSearchService;
import com.showka.value.EigyoDate;
import com.showka.web.ControllerBase;
import com.showka.web.Mode;
import com.showka.web.ModelAndViewExtended;

@Controller
@EnableAutoConfiguration
public class U05G001Controller extends ControllerBase {

	@Autowired
	private UriageSearchService uriageSearchService;

	@Autowired
	private KokyakuCrudService kokyakuCrudService;

	// public method called by request
	/**
	 * 参照.
	 *
	 */
	@RequestMapping(value = "/u05g001/refer", method = RequestMethod.GET)
	public ModelAndViewExtended refer(@ModelAttribute U05G001Form form, ModelAndViewExtended model) {
		// ログインユーザーの所属部署営業日
		EigyoDate eigyoDate = super.getLoginShain().getShozokuBusho().getEigyoDate();
		form.setFrom(eigyoDate.toDate());
		form.setTo(eigyoDate.toDate());
		// set model
		model.addForm(form);
		model.setMode(Mode.READ);
		model.setViewName("/u05/ug05001");
		return model;
	}

	/**
	 * 検索.
	 *
	 */
	@RequestMapping(value = "/u05g001/search", method = RequestMethod.GET)
	public ResponseEntity<?> search(@ModelAttribute U05G001Form form, ModelAndViewExtended model) {
		// get 顧客
		Kokyaku kokyaku = kokyakuCrudService.getDomain(form.getKokyakuCode());
		// search 売上
		UriageSearchCriteria criteria = new UriageSearchCriteria();
		criteria.setKokyaku(Optional.of(kokyaku));
		criteria.setFrom(new EigyoDate(form.getFrom()));
		criteria.setTo(new EigyoDate(form.getTo()));
		criteria.setOnlyUrikake(form.isOnlyUrikake());
		List<Uriage> uriageList = uriageSearchService.search(criteria);
		// set model
		uriageList.forEach(uriage -> {
			model.addObject("kokyakuCode", uriage.getKokyaku().getCode());
			model.addObject("kokyakuName", uriage.getKokyaku().getName());
			model.addObject("denpyoNumber", uriage.getDenpyoNumber());
			model.addObject("uriageDate", uriage.getUriageDate());
			model.addObject("hanbaiKubun", uriage.getHanbaiKubun().name());
			model.addObject("gokeiKakakuZeinuki", uriage.getUriageGokeiKakaku().getZeinukiFormatted());
		});
		model.addForm(form);
		return ResponseEntity.ok(model);
	}
}