package com.showka.web.u05;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.thymeleaf.util.StringUtils;

import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u05.Uriage;
import com.showka.service.crud.u01.i.KokyakuCrudService;
import com.showka.service.search.u05.UriageSearchCriteria;
import com.showka.service.search.u05.i.UriageSearchService;
import com.showka.service.validate.u01.i.KokyakuValidateService;
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

	@Autowired
	private KokyakuValidateService kokyakuValidateService;

	// public method called by request
	/**
	 * 参照.
	 *
	 */
	@RequestMapping(value = "/u05g001/refer", method = RequestMethod.GET)
	public ModelAndViewExtended refer(@ModelAttribute U05G001Form form, ModelAndViewExtended model) {
		// ログインユーザーの所属部署営業日
		EigyoDate eigyoDate = super.getLoginShain().getShozokuBusho().getEigyoDate();
		// defaults
		form.setFrom(eigyoDate.withDayOfMonth(1).toDate());
		form.setTo(eigyoDate.toDate());
		form.setOnlyUrikake(false);
		// set model
		model.addForm(form);
		model.setMode(Mode.READ);
		model.setViewName("/u05/u05g001");
		return model;
	}

	/**
	 * 検索.
	 *
	 */
	@RequestMapping(value = "/u05g001/search", method = RequestMethod.POST)
	public ResponseEntity<?> search(@ModelAttribute U05G001Form form, ModelAndViewExtended model) {
		// search 売上
		UriageSearchCriteria criteria = new UriageSearchCriteria();
		// set 顧客
		if (StringUtils.isEmpty(form.getKokyakuCode())) {
			criteria.setKokyaku(Optional.empty());
		} else {
			kokyakuValidateService.validateForRefer(form.getKokyakuCode());
			Kokyaku kokyaku = kokyakuCrudService.getDomain(form.getKokyakuCode());
			criteria.setKokyaku(Optional.of(kokyaku));
		}
		// set 売上日from
		if (form.getFrom() == null) {
			form.setFrom(new Date(0));
		}
		criteria.setFrom(new EigyoDate(form.getFrom()));
		// set 売上日to
		if (form.getTo() == null) {
			form.setTo(new Date(Long.MAX_VALUE));
		}
		criteria.setTo(new EigyoDate(form.getTo()));
		criteria.setOnlyUrikake(form.isOnlyUrikake());
		List<Uriage> searchLislt = uriageSearchService.search(criteria);
		// set model
		List<Map<String, Object>> uriageList = searchLislt.stream().map(uriage -> {
			Map<String, Object> ret = new HashMap<String, Object>();
			ret.put("kokyakuCode", uriage.getKokyaku().getCode());
			ret.put("kokyakuName", uriage.getKokyaku().getName());
			ret.put("denpyoNumber", uriage.getDenpyoNumber());
			ret.put("uriageDate", uriage.getUriageDate().toString());
			ret.put("hanbaiKubun", uriage.getHanbaiKubun().name());
			ret.put("gokeiKakakuZeinuki", uriage.getUriageGokeiKakaku().getZeinukiFormatted());
			return ret;
		}).collect(Collectors.toList());
		model.addObject("uriageList", uriageList);
		model.addForm(form);
		return ResponseEntity.ok(model);
	}
}