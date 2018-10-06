package com.showka.web.u08;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.thymeleaf.util.StringUtils;

import com.showka.domain.builder.KeshikomiBuilder;
import com.showka.domain.builder.NyukinKeshikomiBuilder;
import com.showka.domain.u05.Uriage;
import com.showka.domain.u06.Urikake;
import com.showka.domain.u06.UrikakeKeshikomi;
import com.showka.domain.u08.Keshikomi;
import com.showka.domain.u08.Nyukin;
import com.showka.domain.u08.NyukinKeshikomi;
import com.showka.domain.z00.Busho;
import com.showka.service.crud.u06.i.UrikakeCrudService;
import com.showka.service.crud.u06.i.UrikakeKeshikomiCrudService;
import com.showka.service.crud.u08.i.NyukinCrudService;
import com.showka.service.crud.u08.i.NyukinKeshikomiCrudService;
import com.showka.service.search.u06.i.UrikakeSearchService;
import com.showka.service.validate.u08.i.NyukinKeshikomiValidateService;
import com.showka.value.AmountOfMoney;
import com.showka.value.EigyoDate;
import com.showka.value.TheTimestamp;
import com.showka.web.ControllerBase;
import com.showka.web.Mode;
import com.showka.web.ModelAndViewExtended;

@Controller
@EnableAutoConfiguration
public class U08G003Controller extends ControllerBase {

	@Autowired
	private NyukinCrudService nyukinCrudService;

	@Autowired
	private UrikakeCrudService urikakeCrudService;

	@Autowired
	private NyukinKeshikomiCrudService nyukinKeshikomiCrudService;

	@Autowired
	private NyukinKeshikomiValidateService nyukinKeshikomiValidateService;

	@Autowired
	private UrikakeSearchService urikakeSearchService;

	@Autowired
	private UrikakeKeshikomiCrudService urikakeKeshikomiCrudService;

	@RequestMapping(value = "/u08g003/refer", method = RequestMethod.GET)
	public ModelAndViewExtended refer(@ModelAttribute U08G003Form form, ModelAndViewExtended model) {
		model.addForm(form);
		model.setViewName("/u08/u08g003");
		return model;
	}

	@RequestMapping(value = "/u08g003/get", method = RequestMethod.POST)
	public ResponseEntity<?> get(@ModelAttribute U08G003Form form, ModelAndViewExtended model) {
		// get 入金消込
		NyukinKeshikomi nyukinKeshikomi = nyukinKeshikomiCrudService.getDomain(form.getNyukinId());
		// set model
		// 営業日
		model.addObject("eigyoDate", nyukinKeshikomi.getNyukinBushoEigyoDate().toString());
		// 入金
		Nyukin nyukin = nyukinKeshikomi.getNyukin();
		model.addObject("nyukinKingaku", nyukin.getKingaku().getFormatted());
		model.addObject("kokyakuName", nyukin.getKokyaku().getName());
		model.addObject("nyukinHoho", nyukin.getNyukinHohoKubun().name());
		model.addObject("nyukinDate", nyukin.getDate().toString());
		model.addObject("bushoName", nyukin.getBusho().getName());
		model.addObject("mikeshikomi", nyukinKeshikomi.getMikeshikomi().getFormatted());
		// 消込リスト
		List<Map<String, Object>> keshikomiList = this.buildKeshikomiList(nyukinKeshikomi);
		model.addObject("keshikomiList", keshikomiList);
		// get 売掛消込
		Set<UrikakeKeshikomi> urikakeKeshikomiSet = nyukinKeshikomi.getUrikakeSet().stream().map(u -> {
			return urikakeKeshikomiCrudService.getDomain(u.getRecordId());
		}).collect(Collectors.toSet());
		List<Map<String, Object>> urikakeKeshikomiList = urikakeKeshikomiSet.stream().map(uk -> {
			Map<String, Object> ret = new HashMap<String, Object>();
			// 売上
			Urikake urikake = uk.getUrikake();
			Uriage uriage = urikake.getUriage();
			ret.put("uriageDate", uriage.getUriageDate().toString());
			ret.put("uriageDenpyoNumber", uriage.getDenpyoNumber());
			ret.put("uriageKingaku", uriage.getUriageGokeiKakaku().getZeikomiFormatted());
			// 売掛
			ret.put("urikakeKingaku", urikake.getKingaku().intValue());
			// 売掛消込
			ret.put("thisKeshikomiKingaku", uk.getKeshikomiKingakuOf(nyukin).intValue());
			ret.put("otherKeshikomiKingaku", uk.getKeshikomiKigakuExcluding(nyukin).intValue());
			return ret;
		}).collect(Collectors.toList());
		model.addObject("urikakeKeshikomiList", urikakeKeshikomiList);
		// set form
		form.setVersion(nyukin.getVersion());
		model.addForm(form);
		return ResponseEntity.ok(model);
	}

	@RequestMapping(value = "/u08g003/updateForm", method = RequestMethod.POST)
	public ResponseEntity<?> updateForm(@ModelAttribute U08G003Form form, ModelAndViewExtended model) {
		// get 入金消込
		NyukinKeshikomi nyukinKeshikomi = nyukinKeshikomiCrudService.getDomain(form.getNyukinId());
		Nyukin nyukin = nyukinKeshikomi.getNyukin();
		// 本日営業にのみ抽出
		EigyoDate eigyoDate = nyukinKeshikomi.getNyukinBushoEigyoDate();
		nyukinKeshikomi.removeKeshikomiBefore(eigyoDate);
		// set to model
		List<Map<String, Object>> keshikomiList = this.buildKeshikomiList(nyukinKeshikomi);
		model.addObject("keshikomiList", keshikomiList);
		// 消込済売掛IDリスト
		Set<String> keshikomiDoneUrikakIdSet = nyukinKeshikomi.getKeshikomiSet()
				.stream()
				.map(Keshikomi::getUrikakeId)
				.collect(Collectors.toSet());
		// get 売掛
		String kokyakuId = nyukinKeshikomi.getNyukin().getKokyakuId();
		List<Urikake> urikake = urikakeSearchService.getUrikakeOfKokyaku(kokyakuId);
		// この入金によって消込まれた売掛は除去
		urikake.removeIf(u -> {
			String thisUrikakeId = u.getRecordId();
			return keshikomiDoneUrikakIdSet.contains(thisUrikakeId);
		});
		// set to model
		List<Map<String, Object>> newKeshikomiList = urikake.stream().map(u -> {
			Map<String, Object> ret = new HashMap<String, Object>();
			// 売上
			Uriage uriage = u.getUriage();
			ret.put("uriageDate", uriage.getUriageDate().toString());
			ret.put("uriageDenpyoNumber", uriage.getDenpyoNumber());
			// 売掛
			ret.put("urikakeKingaku", u.getKingaku().intValue());
			// 売掛消込
			UrikakeKeshikomi urikakeKeshikomi = urikakeKeshikomiCrudService.getDomain(u.getRecordId());
			AmountOfMoney otherNyukinKeshikomiKingaku = urikakeKeshikomi.getKeshikomiKigakuExcluding(nyukin);
			ret.put("otherNyukinKeshikomiKingaku", otherNyukinKeshikomiKingaku.intValue());
			AmountOfMoney otherKeshikomiKingaku = urikakeKeshikomi.getKeshikomiKingakuOf(nyukin);
			ret.put("otherKeshikomiKingaku", otherKeshikomiKingaku.intValue());
			// 消込
			ret.put("keshikomiId", null);
			ret.put("urikakeId", u.getRecordId());
			ret.put("urikakeVersion", u.getVersion());
			ret.put("kingaku", 0);
			ret.put("date", eigyoDate.toString());
			ret.put("version", null);
			return ret;
		}).collect(Collectors.toList());
		model.addObject("newKeshikomiList", newKeshikomiList);
		// form
		model.setMode(Mode.UPDATE);
		model.addForm(form);
		return ResponseEntity.ok(model);
	}

	/**
	 * 更新.
	 */
	@Transactional
	@RequestMapping(value = "/u08g003/update", method = RequestMethod.POST)
	public ResponseEntity<?> update(@ModelAttribute U08G003Form form, ModelAndViewExtended model) {
		// build 入金消込
		NyukinKeshikomi nyukinKeshikomi = this.buildNyukinKeshikomiFromForm(form);
		// get 入金消込更新前
		NyukinKeshikomi nyukinKeshikomiBeforeUpdate = nyukinKeshikomiCrudService.getDomain(form.getNyukinId());
		EigyoDate eigyoDate = nyukinKeshikomiBeforeUpdate.getNyukinBushoEigyoDate();
		nyukinKeshikomiBeforeUpdate.removeKeshikomiOf(eigyoDate);
		// merge 消込セット
		// FIXME mergeは逆のほうがよい? 古い方を新しい振込SETで上書き。
		nyukinKeshikomi.mergeKeshikomiSet(nyukinKeshikomiBeforeUpdate);
		// set 売掛 version for OCC
		Map<String, Integer> versionMap = form.getMeisai().stream().collect(Collectors.toMap(m -> {
			return m.getUrikakeId();
		}, m -> {
			return m.getUrikakeVersion();
		}));
		Set<Urikake> urikakeSet = nyukinKeshikomi.getUrikakeSetOf(eigyoDate);
		urikakeSet.forEach(u -> {
			Integer v = versionMap.get(u.getRecordId());
			u.setVersion(v);
		});
		// validate
		nyukinKeshikomiValidateService.validate(nyukinKeshikomi);
		// save
		nyukinKeshikomiCrudService.save(eigyoDate, nyukinKeshikomi);
		// return model
		form.setSuccessMessage("更新成功");
		model.addForm(form);
		return ResponseEntity.ok(model);
	}

	@RequestMapping(value = "/u08g003/cancelForm", method = RequestMethod.POST)
	public ResponseEntity<?> cancelForm(@ModelAttribute U08G003Form form, ModelAndViewExtended model) {
		// get 入金消込
		NyukinKeshikomi nyukinKeshikomi = nyukinKeshikomiCrudService.getDomain(form.getNyukinId());
		// 本日営業日のみ削除(キャンセル対象ではないため)
		EigyoDate eigyoDate = nyukinKeshikomi.getNyukinBushoEigyoDate();
		nyukinKeshikomi.removeKeshikomiOf(eigyoDate);
		// set to model
		List<Map<String, Object>> keshikomiList = this.buildKeshikomiList(nyukinKeshikomi);
		model.addObject("keshikomiList", keshikomiList);
		// form
		model.setMode(Mode.UPDATE);
		model.addForm(form);
		return ResponseEntity.ok(model);
	}

	@Transactional
	@RequestMapping(value = "/u08g003/cancel", method = RequestMethod.POST)
	public ResponseEntity<?> cancel(@ModelAttribute U08G003Form form, ModelAndViewExtended model) {
		// 消込対象の消込ID
		Set<String> target = form.getMeisai().stream().map(m -> {
			return m.getKeshikomiId();
		}).collect(Collectors.toSet());
		// 入金消込
		NyukinKeshikomi nyukinKeshikomi = nyukinKeshikomiCrudService.getDomain(form.getNyukinId());
		// OCC
		Nyukin nyukin = nyukinKeshikomi.getNyukin();
		nyukin.setVersion(form.getVersion());
		// 消込
		nyukinKeshikomiCrudService.cancel(nyukin, target);
		// return model
		model.addForm(form);
		return ResponseEntity.ok(model);
	}

	/**
	 * フォームから入金消込をビルドする.
	 * 
	 * @param form
	 *            フォーム
	 * @return 入金消込
	 */
	private NyukinKeshikomi buildNyukinKeshikomiFromForm(U08G003Form form) {
		// 入金
		Nyukin nyukin = nyukinCrudService.getDomain(form.getNyukinId());
		// OCC
		nyukin.setVersion(form.getVersion());
		// 部署
		Busho busho = nyukin.getBusho();
		// 営業日
		EigyoDate eigyoDate = busho.getEigyoDate();
		// 売掛消込
		Set<Keshikomi> keshikomiSet = form.getMeisai().stream().map(m -> {
			// 売掛
			String urikakeId = m.getUrikakeId();
			Urikake urikake = urikakeCrudService.getDomain(urikakeId);
			// FIXME
			String id = m.getKeshikomiId();
			String keshikomiId = !StringUtils.isEmpty(id) ? id : "dummy_" + UUID.randomUUID().toString();
			// build 消込
			KeshikomiBuilder b = new KeshikomiBuilder();
			b.withNyukin(nyukin);
			b.withUrikake(urikake);
			b.withDate(eigyoDate);
			b.withTimestamp(new TheTimestamp());
			b.withKingaku(m.getKingaku());
			b.withVersion(m.getVersion());
			b.withRecordId(keshikomiId);
			return b.build();
		}).collect(Collectors.toSet());
		// remove 消込.金額 = 0
		keshikomiSet.removeIf(k -> {
			return k.getKingaku().intValue() == 0;
		});
		// 入金消込
		NyukinKeshikomiBuilder b = new NyukinKeshikomiBuilder();
		b.withNyukin(nyukin);
		b.withKeshikomiSet(keshikomiSet);
		NyukinKeshikomi nyukinKeshikomi = b.build();
		return nyukinKeshikomi;
	}

	/**
	 * 入金消込から消込リストを生成.
	 * 
	 * @param nyukinKeshikomi
	 *            入金消込
	 * @return
	 */
	private List<Map<String, Object>> buildKeshikomiList(NyukinKeshikomi nyukinKeshikomi) {
		// build
		Set<Keshikomi> keshikomiSet = nyukinKeshikomi.getKeshikomiSet();
		Nyukin nyukin = nyukinKeshikomi.getNyukin();
		return keshikomiSet.stream().map(keshikomi -> {
			Map<String, Object> ret = new HashMap<String, Object>();
			// 売上
			Uriage uriage = keshikomi.getUrikake().getUriage();
			ret.put("uriageDate", uriage.getUriageDate().toString());
			ret.put("uriageDenpyoNumber", uriage.getDenpyoNumber());
			ret.put("uriageKingaku", uriage.getUriageGokeiKakaku().getZeikomiFormatted());
			// 売掛
			Urikake urikake = keshikomi.getUrikake();
			ret.put("urikakeKingaku", urikake.getKingaku().intValue());
			// 売掛消込
			UrikakeKeshikomi urikakeKeshikomi = urikakeKeshikomiCrudService.getDomain(urikake.getRecordId());
			AmountOfMoney otherNyukinKeshikomiKingaku = urikakeKeshikomi.getKeshikomiKigakuExcluding(nyukin);
			ret.put("otherNyukinKeshikomiKingaku", otherNyukinKeshikomiKingaku.intValue());
			AmountOfMoney keshikomiKingaku = urikakeKeshikomi.getKeshikomiKingakuOf(nyukin);
			AmountOfMoney thisKeshikomi = keshikomi.getKingaku();
			ret.put("otherKeshikomiKingaku", keshikomiKingaku.subtract(thisKeshikomi).intValue());
			// 消込
			ret.put("keshikomiId", keshikomi.getRecordId());
			ret.put("urikakeId", keshikomi.getUrikakeId());
			ret.put("urikakeVersion", keshikomi.getUrikake().getVersion());
			ret.put("kingaku", thisKeshikomi.intValue());
			ret.put("date", keshikomi.getDate().toString());
			ret.put("version", keshikomi.getVersion());
			return ret;
		}).collect(Collectors.toList());
	}
}
