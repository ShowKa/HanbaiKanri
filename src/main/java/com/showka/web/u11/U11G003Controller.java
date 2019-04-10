package com.showka.web.u11;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.showka.domain.builder.NyukaBuilder;
import com.showka.domain.builder.ShohinIdoBuilder;
import com.showka.domain.builder.ShohinIdoMeisaiBuilder;
import com.showka.domain.u11.Nyuka;
import com.showka.domain.u11.ShohinIdo;
import com.showka.domain.u11.ShohinIdoMeisai;
import com.showka.domain.z00.Busho;
import com.showka.domain.z00.Shain;
import com.showka.domain.z00.Shohin;
import com.showka.kubun.ShohinIdoKubun;
import com.showka.service.crud.u11.i.NyukaSakiCrud;
import com.showka.service.crud.u11.i.ShohinIdoNyukaCrud;
import com.showka.service.crud.z00.i.BushoCrud;
import com.showka.service.crud.z00.i.ShohinCrud;
import com.showka.service.persistence.u11.i.ShohinIdoNyukaPersistence;
import com.showka.service.persistence.u11.i.ShohinIdoNyukaTeiseiPersistence;
import com.showka.service.query.u11.i.ShohinTanaoroshiQuery;
import com.showka.service.validator.u11.i.NyukaTeiseiValidator;
import com.showka.service.validator.u11.i.NyukaValidator;
import com.showka.service.validator.z00.i.BushoValidator;
import com.showka.service.validator.z00.i.NyukaSakiValidator;
import com.showka.service.validator.z00.i.ShohinValidator;
import com.showka.system.exception.AuthorizationException;
import com.showka.system.exception.validate.ValidateException;
import com.showka.value.EigyoDate;
import com.showka.value.TheTimestamp;
import com.showka.web.ControllerBase;
import com.showka.web.Mode;
import com.showka.web.ModelAndViewExtended;

@Controller
@EnableAutoConfiguration
public class U11G003Controller extends ControllerBase {

	@Autowired
	private BushoCrud bushoCrud;

	@Autowired
	private ShohinCrud shohinCrud;

	@Autowired
	private NyukaSakiCrud nyukaSakiCrud;

	@Autowired
	private ShohinIdoNyukaCrud shohinIdoNyukaCrud;

	@Autowired
	private BushoValidator bushoValidator;

	@Autowired
	private ShohinValidator shoinValidator;

	@Autowired
	private NyukaSakiValidator nyukaSakiValidator;

	@Autowired
	private NyukaValidator nyukaValidator;

	@Autowired
	private NyukaTeiseiValidator nyukaTeiseiValidator;

	@Autowired
	private ShohinIdoNyukaPersistence shohinIdoNyukaPersistence;

	@Autowired
	private ShohinIdoNyukaTeiseiPersistence shohinIdoNyukaTeiseiPersistence;

	@Autowired
	private ShohinTanaoroshiQuery shohinTanaoroshiQuery;

	/** 参照モード. */
	@RequestMapping(value = "/u11g003/refer", method = RequestMethod.GET)
	public ModelAndViewExtended refer(@ModelAttribute U11G003Form form, ModelAndViewExtended model) {
		// set model
		model.addObject("nyukaId", form.getNyukaId());
		model.addForm(form);
		model.setMode(Mode.READ);
		model.setViewName("/u11/u11g003");
		// return
		return model;
	}

	/** 新規登録モード */
	@RequestMapping(value = "/u11g003/registerForm", method = RequestMethod.GET)
	public ModelAndViewExtended registerForm(@ModelAttribute U11G003Form form, ModelAndViewExtended model) {
		// 初期値
		Busho busho = super.getLoginShain().getShozokuBusho();
		form.setBushoCode(busho.getCode());
		// set model
		model.addForm(form);
		model.setMode(Mode.REGISTER);
		model.setViewName("/u11/u11g003");
		// return
		return model;
	}

	/** 商品入荷登録. */
	@RequestMapping(value = "/u11g003/register", method = RequestMethod.POST)
	public ResponseEntity<?> register(@ModelAttribute U11G003Form form, ModelAndViewExtended model) {
		// データ存在チェック
		bushoValidator.validateExistance(form.getBushoCode());
		nyukaSakiValidator.validateExistance(form.getNyukaSakiCode());
		List<U11G003MeisaiForm> meisai = form.getMeisai();
		meisai.forEach(m -> {
			shoinValidator.validateExistance(m.getShohinCode());
		});
		// フォームから登録データ作成
		Nyuka nyuka = buildNyuka(form);
		// 処理可否検証
		this.validateTanaoroshi(nyuka);
		this.validateAuth(nyuka);
		// 整合性検証
		nyukaValidator.validate(nyuka);
		// 登録
		shohinIdoNyukaPersistence.save(nyuka);
		// model
		// XXX 入荷ID = 商品移動IDとしてよいか？
		model.addObject("nyukaId", nyuka.getShohinIdoId());
		// return
		form.setSuccessMessage("新規登録成功");
		model.addForm(form);
		return ResponseEntity.ok(model);
	}

	/** 商品入荷更新. */
	@RequestMapping(value = "/u11g003/update", method = RequestMethod.POST)
	public ResponseEntity<?> update(@ModelAttribute U11G003Form form, ModelAndViewExtended model) {
		// データ存在チェック
		List<U11G003MeisaiForm> meisai = form.getMeisai();
		meisai.forEach(m -> {
			shoinValidator.validateExistance(m.getShohinCode());
		});
		// 入荷（更新前）
		Nyuka _nyuka = shohinIdoNyukaCrud.getDomain(form.getNyukaId());
		// 商品移動（更新）
		ShohinIdo _shohinIdo = _nyuka.getNyukaShohinIdo();
		Integer max = _shohinIdo.getMaxMeisaiNumber();
		AtomicInteger i = new AtomicInteger(max + 1);
		List<ShohinIdoMeisai> shohinIdoMeisaiList = meisai.stream().map(m -> {
			// 明細番号
			Integer meisaiNumber = m.getMeisaiNumber() != null ? m.getMeisaiNumber() : i.getAndIncrement();
			// build
			ShohinIdoMeisaiBuilder simb = new ShohinIdoMeisaiBuilder();
			simb.withMeisaiNumber(meisaiNumber);
			simb.withNumber(m.getNyukaSu());
			Shohin shohin = shohinCrud.getDomain(m.getShohinCode());
			simb.withShohinDomain(shohin);
			return simb.build();
		}).collect(Collectors.toList());
		ShohinIdo shohinIdo = new ShohinIdoBuilder().withMeisai(shohinIdoMeisaiList).apply(_shohinIdo);
		// 入荷（更新後）
		NyukaBuilder b = new NyukaBuilder();
		b.withNyukaShohinIdo(shohinIdo);
		Nyuka nyuka = b.apply(_nyuka);
		// OCC
		nyuka.setVersion(form.getVersion());
		// 処理可否検証
		this.validateTanaoroshi(nyuka);
		this.validateAuth(nyuka);
		// 整合性検証
		nyukaValidator.validate(nyuka);
		nyukaValidator.validateForUpdate(nyuka);
		// 更新
		shohinIdoNyukaPersistence.save(nyuka);
		// return
		form.setSuccessMessage("更新成功");
		model.addForm(form);
		return ResponseEntity.ok(model);
	}

	/** 商品入荷削除. */
	@RequestMapping(value = "/u11g003/delete", method = RequestMethod.POST)
	public ResponseEntity<?> delete(@ModelAttribute U11G003Form form, ModelAndViewExtended model) {
		// 入荷（更新前）
		Nyuka nyuka = shohinIdoNyukaCrud.getDomain(form.getNyukaId());
		// OCC
		nyuka.setVersion(form.getVersion());
		// 処理可否検証
		this.validateTanaoroshi(nyuka);
		this.validateAuth(nyuka);
		// 整合性検証
		nyukaValidator.validateForDelete(nyuka);
		// 削除
		shohinIdoNyukaPersistence.delete(nyuka);
		// return
		form.setSuccessMessage("削除成功");
		model.addForm(form);
		return ResponseEntity.ok(model);
	}

	/** 商品入荷訂正登録. */
	@RequestMapping(value = "/u11g003/registerTeisei", method = RequestMethod.POST)
	public ResponseEntity<?> registerTeisei(@ModelAttribute U11G003Form form, ModelAndViewExtended model) {
		// データ存在チェック
		List<U11G003MeisaiForm> meisai = form.getMeisai();
		meisai.forEach(m -> {
			shoinValidator.validateExistance(m.getShohinCode());
		});
		// 入荷（訂正前）
		Nyuka nyuka = shohinIdoNyukaCrud.getDomain(form.getNyukaId());
		// 訂正
		form.getMeisai().forEach(m -> {
			Shohin shohin = shohinCrud.getDomain(m.getShohinCode());
			nyuka.teisei(shohin, m.getNyukaSu());
		});
		// OCC
		nyuka.setVersion(form.getVersion());
		// 処理可否検証
		this.validateTanaoroshi(nyuka);
		this.validateAuth(nyuka);
		// 整合性検証
		EigyoDate eigyoDate = nyuka.getBusho().getEigyoDate();
		String teiseiShohinIdoId = nyuka.getShohinIdoOf(eigyoDate).get().getRecordId();
		nyukaTeiseiValidator.validateForTeisei(nyuka, teiseiShohinIdoId);
		// register
		shohinIdoNyukaTeiseiPersistence.save(nyuka);
		// return
		return ResponseEntity.ok(model);
	}

	/** 商品入荷訂正更新. */
	@RequestMapping(value = "/u11g003/updateTeisei", method = RequestMethod.POST)
	public ResponseEntity<?> updateTeisei(@ModelAttribute U11G003Form form, ModelAndViewExtended model) {
		// データ存在チェック
		List<U11G003MeisaiForm> meisai = form.getMeisai();
		meisai.forEach(m -> {
			shoinValidator.validateExistance(m.getShohinCode());
		});
		// 入荷（訂正前）
		Nyuka nyuka = shohinIdoNyukaCrud.getDomain(form.getNyukaId());
		// 訂正
		form.getMeisai().forEach(m -> {
			Shohin shohin = shohinCrud.getDomain(m.getShohinCode());
			nyuka.teisei(shohin, m.getNyukaSu());
		});
		// OCC
		nyuka.setVersion(form.getVersion());
		// 処理可否検証
		this.validateTanaoroshi(nyuka);
		this.validateAuth(nyuka);
		// 整合性検証
		EigyoDate eigyoDate = nyuka.getBusho().getEigyoDate();
		String teiseiShohinIdoId = nyuka.getShohinIdoOf(eigyoDate).get().getRecordId();
		nyukaTeiseiValidator.validateForTeiseiUpdate(nyuka, teiseiShohinIdoId);
		// register
		shohinIdoNyukaTeiseiPersistence.save(nyuka);
		// return
		return ResponseEntity.ok(model);
	}

	/** 商品入荷訂正削除. */
	@RequestMapping(value = "/u11g003/deleteTeisei", method = RequestMethod.POST)
	public ResponseEntity<?> deleteTeisei(@ModelAttribute U11G003Form form, ModelAndViewExtended model) {
		// 入荷（訂正前）
		Nyuka nyuka = shohinIdoNyukaCrud.getDomain(form.getNyukaId());
		// OCC
		nyuka.setVersion(form.getVersion());
		// 処理可否検証
		this.validateTanaoroshi(nyuka);
		this.validateAuth(nyuka);
		// 整合性検証
		EigyoDate eigyoDate = nyuka.getBusho().getEigyoDate();
		String teiseiShohinIdoId = nyuka.getShohinIdoOf(eigyoDate).get().getRecordId();
		nyukaTeiseiValidator.validateForTeiseiDelete(nyuka, teiseiShohinIdoId);
		// register
		shohinIdoNyukaTeiseiPersistence.delete(nyuka);
		// return
		return ResponseEntity.ok(model);
	}

	/** 商品入荷取得. */
	@RequestMapping(value = "/u11g003/get", method = RequestMethod.POST)
	public ResponseEntity<?> get(@ModelAttribute U11G003Form form, ModelAndViewExtended model) {
		// 入荷取得
		Nyuka nyuka = shohinIdoNyukaCrud.getDomain(form.getNyukaId());
		// 明細リスト_入荷
		ShohinIdo nyukaShohinIdo = nyuka.getNyukaShohinIdo();
		List<ShohinIdoMeisai> meisaiList = nyukaShohinIdo.getMeisai();
		Collections.sort(meisaiList);
		List<Map<String, Object>> meisaiList_Nyuka = meisaiList.stream().map(si -> {
			Map<String, Object> m = new HashMap<String, Object>();
			Shohin shohin = si.getShohinDomain();
			m.put("meisaiNumber", si.getMeisaiNumber());
			m.put("shohinCode", shohin.getCode());
			m.put("shohinName", shohin.getName());
			m.put("nyukaSu", nyuka.getNumber(shohin));
			return m;
		}).collect(Collectors.toList());
		// 明細リスト_入荷訂正履歴
		List<Map<String, Object>> meisaiList_NyukaTeisei = new ArrayList<>();
		List<ShohinIdo> allShohinIdoList = nyuka.getAllShohinIdoList();
		allShohinIdoList.forEach(shohinIdo -> {
			shohinIdo.getMeisai().forEach(shohinIdoMeisai -> {
				Shohin shohin = shohinIdoMeisai.getShohinDomain();
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("meisaiNumber", shohinIdoMeisai.getMeisaiNumber());
				m.put("shohinCode", shohin.getCode());
				m.put("shohinName", shohin.getName());
				m.put("date", shohinIdo.getDate().toDate());
				m.put("number", shohinIdoMeisai.getNumber());
				meisaiList_NyukaTeisei.add(m);
			});
		});
		// model
		model.addObject("nyukaId", nyuka.getShohinIdoId());
		model.addObject("bushoCode", nyuka.getBusho().getCode());
		model.addObject("nyukaSakiCode", nyuka.getNyukaSaki().getCode());
		model.addObject("nyukaDate", nyuka.getNyukaDate());
		model.addObject("meisaiList_Nyuka", meisaiList_Nyuka);
		model.addObject("meisaiList_NyukaTeisei", meisaiList_NyukaTeisei);
		model.addObject("version", nyuka.getVersion());
		// mode
		// 対象伝票
		EigyoDate eigyoDate = nyuka.getBusho().getEigyoDate();
		boolean nyukaDenpyo = nyuka.getNyukaDate().equals(eigyoDate);
		model.addObject("target", nyukaDenpyo ? "nyuka" : "nyukaTeisei");
		// 当日訂正
		boolean done = nyuka.doneTeisei(eigyoDate);
		model.addObject("teiseiDone", done ? "done" : "notYet");
		// return
		return ResponseEntity.ok(model);
	}

	/** ヘッダー整合性検証. */
	@RequestMapping(value = "/u11g003/validateHeader", method = RequestMethod.POST)
	public ResponseEntity<?> validateHeader(@ModelAttribute U11G003Form form, ModelAndViewExtended model) {
		// データ存在チェック
		bushoValidator.validateExistance(form.getBushoCode());
		nyukaSakiValidator.validateExistance(form.getNyukaSakiCode());
		// return
		return ResponseEntity.ok(model);
	}

	/** 明細整合性検証. */
	@RequestMapping(value = "/u11g003/validateMeisai", method = RequestMethod.POST)
	public ResponseEntity<?> validateMeisai(@ModelAttribute U11G003Form form, ModelAndViewExtended model) {
		// データ存在チェック
		List<U11G003MeisaiForm> meisai = form.getMeisai();
		meisai.forEach(m -> {
			shoinValidator.validateExistance(m.getShohinCode());
		});
		// return
		return ResponseEntity.ok(model);
	}

	/** 棚卸検証 */
	private void validateTanaoroshi(Nyuka nyuka) {
		boolean onGoing = shohinTanaoroshiQuery.onGoing(nyuka.getBusho());
		if (onGoing) {
			throw new ValidateException("棚卸中のため登録・更新・削除できません。");
		}
	}

	/** ユーザー権限検証 */
	private void validateAuth(Nyuka nyuka) {
		Shain user = super.getLoginShain();
		if (!user.getShozokuBusho().equals(nyuka.getBusho())) {
			throw new AuthorizationException("所属部署の入荷のみ登録・更新・削除が行なえます。");
		}
	}

	// private
	/**
	 * form -> $入荷
	 */
	private Nyuka buildNyuka(U11G003Form form) {
		// 商品移動
		ShohinIdo shohinIdo = this.buildShohinIdo(form);
		// 入荷
		NyukaBuilder nb = new NyukaBuilder();
		nb.withNyukaSaki(nyukaSakiCrud.getDomain(form.getNyukaSakiCode()));
		nb.withNyukaShohinIdo(shohinIdo);
		nb.withRecordId(form.getNyukaId());
		nb.withTeiseiList(new ArrayList<>());
		nb.withVersion(form.getVersion());
		return nb.build();
	}

	/**
	 * 商品移動構築
	 */
	private ShohinIdo buildShohinIdo(U11G003Form form) {
		// 明細
		List<ShohinIdoMeisai> meisai = this.buildShohinIdoMeisai(form.getMeisai());
		// 部署
		Busho busho = bushoCrud.getDomain(form.getBushoCode());
		// 商品移動
		ShohinIdoBuilder b = new ShohinIdoBuilder();
		b.withBusho(busho);
		b.withDate(busho.getEigyoDate());
		b.withKubun(ShohinIdoKubun.入荷);
		b.withMeisai(meisai);
		b.withTimestamp(new TheTimestamp());
		b.withRecordId(form.getNyukaId());
		b.withVersion(form.getVersion());
		ShohinIdo shohinIdo = b.build();
		return shohinIdo;
	}

	/**
	 * 商品移動明細構築
	 */
	private List<ShohinIdoMeisai> buildShohinIdoMeisai(List<U11G003MeisaiForm> meisaiList) {
		List<ShohinIdoMeisai> meisai = new ArrayList<ShohinIdoMeisai>();
		int meisaiNumber = 0;
		for (U11G003MeisaiForm mf : meisaiList) {
			// 明細番号採番
			meisaiNumber = mf.getMeisaiNumber() != null ? mf.getMeisaiNumber() : ++meisaiNumber;
			// build
			ShohinIdoMeisaiBuilder mb = new ShohinIdoMeisaiBuilder();
			mb.withMeisaiNumber(meisaiNumber);
			mb.withNumber(mf.getNyukaSu());
			mb.withRecordId(mf.getRecordId());
			mb.withShohinDomain(shohinCrud.getDomain(mf.getShohinCode()));
			mb.withVersion(mf.getVersion());
			meisai.add(mb.build());
		}
		return meisai;
	}

}
