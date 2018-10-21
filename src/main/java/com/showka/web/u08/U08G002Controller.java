package com.showka.web.u08;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.showka.domain.builder.ShukinBuilder;
import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u08.Shukin;
import com.showka.domain.z00.Busho;
import com.showka.domain.z00.Shain;
import com.showka.kubun.NyukinHohoKubun;
import com.showka.service.crud.u01.i.KokyakuCrudService;
import com.showka.service.crud.u08.i.ShukinCrudService;
import com.showka.service.crud.z00.i.BushoCrudService;
import com.showka.service.crud.z00.i.ShainCrudService;
import com.showka.service.validate.u01.i.KokyakuValidateService;
import com.showka.service.validate.u08.i.ShukinValidateService;
import com.showka.service.validate.z00.i.BushoValidateService;
import com.showka.service.validate.z00.i.ShainValidateService;
import com.showka.system.exception.AuthorizationException;
import com.showka.value.AmountOfMoney;
import com.showka.web.ControllerBase;
import com.showka.web.Mode;
import com.showka.web.ModelAndViewExtended;

@Controller
@EnableAutoConfiguration
public class U08G002Controller extends ControllerBase {

	@Autowired
	private ShukinValidateService shukinValidateService;

	@Autowired
	private ShukinCrudService shukinCrudService;

	@Autowired
	private KokyakuValidateService kokyakuValidateService;

	@Autowired
	private BushoValidateService bushoValidateService;

	@Autowired
	private ShainValidateService shainValidateService;

	@Autowired
	private BushoCrudService bushoCrudService;

	@Autowired
	private KokyakuCrudService kokyakuCrudService;

	@Autowired
	private ShainCrudService shainCrudService;

	@RequestMapping(value = "/u08g002/refer", method = RequestMethod.GET)
	public ModelAndViewExtended refer(@ModelAttribute U08G002Form form, ModelAndViewExtended model) {
		model.addForm(form);
		model.setViewName("/u08/u08g002");
		model.setMode(Mode.READ);
		return model;
	}

	@RequestMapping(value = "/u08g002/registerForm", method = RequestMethod.GET)
	public ModelAndViewExtended registerForm(@ModelAttribute U08G002Form form, ModelAndViewExtended model) {
		Shain user = super.getLoginShain();
		form.setBushoCode(user.getShozokuBusho().getCode());
		form.setTantoShainCode(user.getCode());
		model.addForm(form);
		model.setViewName("/u08/u08g002");
		model.setMode(Mode.REGISTER);
		return model;
	}

	/**
	 * 登録.
	 */
	@RequestMapping(value = "/u08g002/register", method = RequestMethod.POST)
	@Transactional
	public ResponseEntity<?> register(@ModelAttribute U08G002Form form, ModelAndViewExtended model) {
		// データ存在チェック
		kokyakuValidateService.validateForRefer(form.getKokyakuCode());
		bushoValidateService.validateExistance(form.getBushoCode());
		shainValidateService.validateExistance(form.getTantoShainCode());
		// 集金の構築
		ShukinBuilder sb = new ShukinBuilder();
		Busho busho = bushoCrudService.getDomain(form.getBushoCode());
		sb.withBusho(busho);
		sb.withDate(busho.getEigyoDate());
		sb.withDenpyoNumber(form.getDenpyoNumber());
		sb.withKingaku(new AmountOfMoney(form.getKingaku()));
		Kokyaku kokyaku = kokyakuCrudService.getDomain(form.getKokyakuCode());
		sb.withKokyaku(kokyaku);
		sb.withNyukinHohoKubun(NyukinHohoKubun.集金);
		Shain tantoShain = shainCrudService.getDomain(form.getTantoShainCode());
		sb.withTantoShain(tantoShain);
		Shukin shukin = sb.build();
		// ユーザー権限チェック
		this.validateAuth(shukin);
		// 集金の業務整合性検証
		shukinValidateService.validate(shukin);
		// 集金の登録整合性検証
		shukinValidateService.validateForRegister(shukin);
		// 集金の保存
		shukinCrudService.save(shukin);
		// return
		form.setSuccessMessage("集金を登録しました。");
		form.setNyukinId(shukin.getNyukinId());
		model.addForm(form);
		return ResponseEntity.ok(model);
	}

	/**
	 * 更新.
	 */
	@RequestMapping(value = "/u08g002/update", method = RequestMethod.POST)
	@Transactional
	public ResponseEntity<?> update(@ModelAttribute U08G002Form form, ModelAndViewExtended model) {
		// データ存在チェック
		shainValidateService.validateExistance(form.getTantoShainCode());
		// 登録済み
		Shukin old = shukinCrudService.getDomain(form.getNyukinId());
		// 集金の構築
		ShukinBuilder sb = new ShukinBuilder();
		Shain tantoShain = shainCrudService.getDomain(form.getTantoShainCode());
		sb.withTantoShain(tantoShain);
		sb.withKingaku(new AmountOfMoney(form.getKingaku()));
		Shukin shukin = sb.apply(old);
		// OCC
		shukin.setVersion(form.getVersion());
		// ユーザー権限チェック
		this.validateAuth(shukin);
		// 集金の業務整合性検証
		shukinValidateService.validate(shukin);
		// 集金の更新整合性検証
		shukinValidateService.validateForUpdate(shukin);
		// 集金の保存
		shukinCrudService.save(shukin);
		// return
		form.setSuccessMessage("集金を更新しました。");
		model.addForm(form);
		return ResponseEntity.ok(model);
	}

	/**
	 * 削除.
	 */
	@RequestMapping(value = "/u08g002/delete", method = RequestMethod.POST)
	@Transactional
	public ResponseEntity<?> delete(@ModelAttribute U08G002Form form, ModelAndViewExtended model) {
		// 集金の取得
		Shukin shukin = shukinCrudService.getDomain(form.getNyukinId());
		// ユーザー権限チェック
		this.validateAuth(shukin);
		// 集金の更新整合性検証
		shukinValidateService.validateForDelete(shukin);
		// 集金の保存
		shukinCrudService.delete(form.getNyukinId(), form.getVersion());
		// return
		form.setSuccessMessage("集金を削除しました。");
		model.addForm(form);
		return ResponseEntity.ok(model);
	}

	/**
	 * 取得.
	 */
	@RequestMapping(value = "/u08g002/get", method = RequestMethod.POST)
	public ResponseEntity<?> get(@ModelAttribute U08G002Form form, ModelAndViewExtended model) {
		// 集金の取得
		Shukin shukin = shukinCrudService.getDomain(form.getNyukinId());
		// set form
		form.setBushoCode(shukin.getBusho().getCode());
		form.setDenpyoNumber(shukin.getDenpyoNumber());
		form.setKingaku(shukin.getKingaku().intValue());
		form.setKokyakuCode(shukin.getKokyaku().getCode());
		form.setNyukinDate(shukin.getDate().toDate());
		form.setVersion(shukin.getVersion());
		form.setTantoShainCode(shukin.getTantoShain().getCode());
		// return
		model.addForm(form);
		return ResponseEntity.ok(model);
	}

	/**
	 * 認可検証.
	 * 
	 * @param shukin
	 *            集金
	 */
	private void validateAuth(Shukin shukin) {
		Shain loginShain = super.getLoginShain();
		if (!loginShain.getShozokuBusho().equals(shukin.getBusho())) {
			throw new AuthorizationException("所属部署の集金のみ登録・更新・削除が行なえます。");
		}
	}
}