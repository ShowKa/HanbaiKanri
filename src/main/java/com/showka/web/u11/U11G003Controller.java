package com.showka.web.u11;

import java.util.ArrayList;
import java.util.List;

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
import com.showka.domain.u11.ShohinIdoMeisai;
import com.showka.kubun.ShohinIdoKubun;
import com.showka.service.crud.u11.i.NyukaSakiCrud;
import com.showka.service.crud.z00.i.BushoCrud;
import com.showka.service.crud.z00.i.ShohinCrud;
import com.showka.service.persistence.u11.i.ShohinIdoNyukaPersistence;
import com.showka.service.validator.z00.i.BushoValidator;
import com.showka.service.validator.z00.i.NyukaSakiValidator;
import com.showka.service.validator.z00.i.ShohinValidator;
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
	private BushoValidator bushoValidator;

	// @Autowired
	private ShohinValidator shoinValidator;

	// @Autowired
	private NyukaSakiValidator nyukaSakiValidator;

	// @Autowired
	private ShohinIdoNyukaPersistence shohinIdoNyukaPersistence;

	/** 参照モード. */
	@RequestMapping(value = "/u11g003/refer", method = RequestMethod.GET)
	public ModelAndViewExtended refer(@ModelAttribute U11G003Form form, ModelAndViewExtended model) {
		// set model
		model.addForm(form);
		model.setMode(Mode.READ);
		model.setViewName("/u11/u11g003");
		// return
		return model;
	}

	/** 新規登録モード */
	@RequestMapping(value = "/u11g003/registerForm", method = RequestMethod.GET)
	public ModelAndViewExtended registerForm(@ModelAttribute U11G003Form form, ModelAndViewExtended model) {
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
		nyukaSakiValidator.validateExistance(form.getNyukasakiCode());
		List<U11G003MeisaiForm> meisai = form.getMeisai();
		for (int i = 0; i < meisai.size(); i++) {
			shoinValidator.validateExistance(meisai.get(i).getShohinCode());
		}
		// フォームから登録データ作成
		Nyuka nyuka = buildDomainNyukaFromForm(form);
		// register
		shohinIdoNyukaPersistence.save(nyuka);
		// set model
		model.addForm(form);
		// return
		return ResponseEntity.ok(model);
	}

	/** 商品入荷更新. */
	@RequestMapping(value = "/u11g003/update", method = RequestMethod.POST)
	public ResponseEntity<?> update(@ModelAttribute U11G003Form form, ModelAndViewExtended model) {
		// return
		return ResponseEntity.ok(model);
	}

	/** 商品入荷削除. */
	@RequestMapping(value = "/u11g003/delete", method = RequestMethod.POST)
	public ResponseEntity<?> delete(@ModelAttribute U11G003Form form, ModelAndViewExtended model) {
		// return
		return ResponseEntity.ok(model);
	}

	/** 商品入荷訂正登録. */
	@RequestMapping(value = "/u11g003/registerTeisei", method = RequestMethod.POST)
	public ResponseEntity<?> registerTeisei(@ModelAttribute U11G003Form form, ModelAndViewExtended model) {
		// return
		return ResponseEntity.ok(model);
	}

	/** 商品入荷訂正更新. */
	@RequestMapping(value = "/u11g003/updateTeisei", method = RequestMethod.POST)
	public ResponseEntity<?> updateTeisei(@ModelAttribute U11G003Form form, ModelAndViewExtended model) {
		// return
		return ResponseEntity.ok(model);
	}

	/** 商品入荷訂正削除. */
	@RequestMapping(value = "/u11g003/deleteTeisei", method = RequestMethod.POST)
	public ResponseEntity<?> deleteTeisei(@ModelAttribute U11G003Form form, ModelAndViewExtended model) {
		// return
		return ResponseEntity.ok(model);
	}

	/** 商品入荷取得. */
	@RequestMapping(value = "/u11g003/get", method = RequestMethod.POST)
	public ResponseEntity<?> get(@ModelAttribute U11G003Form form, ModelAndViewExtended model) {

		// return
		return ResponseEntity.ok(model);
	}

	/** ヘッダー整合性検証. */
	@RequestMapping(value = "/u11g003/validateHeader", method = RequestMethod.POST)
	public ResponseEntity<?> validateHeader(@ModelAttribute U11G003Form form, ModelAndViewExtended model) {
		// return
		return ResponseEntity.ok(model);
	}

	/** 明細整合性検証. */
	@RequestMapping(value = "/u11g003/validateMeisai", method = RequestMethod.POST)
	public ResponseEntity<?> validateMeisai(@ModelAttribute U11G003Form form, ModelAndViewExtended model) {
		// return
		return ResponseEntity.ok(model);
	}

	// private

	/**
	 * 説明
	 */
	private Nyuka buildDomainNyukaFromForm(U11G003Form form) {
		// 入荷商品移動
		ShohinIdoBuilder shohinIdoBuilder = new ShohinIdoBuilder();
		// record_id 採番
		String recordId = form.getRecordId();
		// 明細
		List<ShohinIdoMeisai> meisai = new ArrayList<ShohinIdoMeisai>();
		for (U11G003MeisaiForm mf : form.getMeisai()) {
			// meisai build
			ShohinIdoMeisaiBuilder mb = new ShohinIdoMeisaiBuilder();
			// TODO formに番号がなければ、採番
			mb.withMeisaiNumber(mf.getMeisaiNumber());
			mb.withNumber(mf.getNyukaSu());
			mb.withRecordId(mf.getRecordId());
			mb.withShohinDomain(shohinCrud.getDomain(mf.getShohinCode()));
			mb.withVersion(mf.getVersion());
			// add list
			meisai.add(mb.build());
		}
		shohinIdoBuilder.withBusho(bushoCrud.getDomain(form.getBushoCode()));
		shohinIdoBuilder.withDate(new EigyoDate(form.getDate()));
		shohinIdoBuilder.withKubun(ShohinIdoKubun.入荷);
		shohinIdoBuilder.withMeisai(meisai);
		// recordIDを設定しない=勝手に採番
		// shohinIdoBuilder.withRecordId(recordId);
		shohinIdoBuilder.withTimestamp(new TheTimestamp());
		shohinIdoBuilder.withVersion(form.getVersion());
		// NyukaBuilder
		NyukaBuilder nb = new NyukaBuilder();
		nb.withNyukaSaki(nyukaSakiCrud.getDomain(form.getNyukasakiCode()));
		nb.withNyukaShohinIdo(shohinIdoBuilder.build());
		nb.withRecordId(recordId);
		// 省略
		nb.withTeiseiList(new ArrayList<>());
		nb.withVersion(form.getVersion());
		return nb.build();
	}

}
