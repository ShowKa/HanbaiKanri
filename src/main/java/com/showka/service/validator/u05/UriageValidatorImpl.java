package com.showka.service.validator.u05;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.u05.Uriage;
import com.showka.domain.u05.UriageMeisai;
import com.showka.domain.u06.Urikake;
import com.showka.domain.u06.UrikakeKeshikomi;
import com.showka.domain.z00.Busho;
import com.showka.entity.TUriagePK;
import com.showka.repository.i.CUriageRepository;
import com.showka.service.crud.u05.i.UriageCrud;
import com.showka.service.crud.u06.i.UrikakeCrud;
import com.showka.service.query.u05.i.UriageKeijoQuery;
import com.showka.service.query.u06.i.UrikakeKeshikomiQuery;
import com.showka.service.query.z00.i.BushoDateQuery;
import com.showka.service.validator.u05.i.UriageMeisaiValidator;
import com.showka.service.validator.u05.i.UriageValidator;
import com.showka.system.exception.validate.AlreadyExistsException;
import com.showka.system.exception.validate.CanNotUpdateOrDeleteException;
import com.showka.system.exception.validate.EmptyException;
import com.showka.system.exception.validate.NotEigyoDateException;
import com.showka.system.exception.validate.ValidateException;
import com.showka.value.TheDate;

@Service
public class UriageValidatorImpl implements UriageValidator {

	@Autowired
	private CUriageRepository cUriageRepository;

	@Autowired
	private UriageCrud uriageCrud;

	@Autowired
	private UrikakeCrud urikakeCrud;

	@Autowired
	private UriageKeijoQuery uriageKeijoQuery;

	@Autowired
	private UrikakeKeshikomiQuery urikakeKeshikomiQuery;

	@Autowired
	private BushoDateQuery bushoDateQuery;

	@Autowired
	private UriageMeisaiValidator uriageMeisaiValidate;

	@Override
	public void validate(Uriage domain) throws ValidateException {
		// 明細なしエラー
		this.validateMeisaiSize(domain);
		// 明細検証
		List<UriageMeisai> meisaiList = domain.getUriageMeisai();
		meisaiList.forEach(uriageMeisaiValidate::validate);
	}

	@Override
	public void validateForRegister(Uriage domain) throws ValidateException {
		// 売上日が営業日でない場合、エラー
		this.validateUriageDate(domain);
		// 既存チェック
		this.validateExistsSameNumber(domain);
	}

	@Override
	public void validateForUpdate(Uriage domain) throws ValidateException {
		// キャンセル済みの場合、エラー
		this.validateCanceld(domain);
		// 消込済みの場合、エラー
		this.validateHasKeshikomi(domain);
	}

	@Override
	public void validateForDelete(TUriagePK pk) throws ValidateException {
		// キャンセル済みの場合、エラー
		Uriage domain = uriageCrud.getDomain(pk);
		this.validateCanceld(domain);
		// 計上済みの場合、エラー
		this.validateKeijo(domain);
		// 消込済みの場合、エラー
		this.validateHasKeshikomi(domain);
	}

	@Override
	public void validateForCancel(TUriagePK pk) throws ValidateException {
		// キャンセル済みの場合、エラー
		Uriage domain = uriageCrud.getDomain(pk);
		this.validateCanceld(domain);
	}

	/**
	 * 同一番号の売上伝票がある場合、エラー
	 * 
	 * @param domain
	 *            売上
	 * @throws AlreadyExistsException
	 *             売上伝票はすでに存在しています。
	 */
	void validateExistsSameNumber(Uriage domain) throws AlreadyExistsException {
		TUriagePK pk = new TUriagePK();
		pk.setDenpyoNumber(domain.getDenpyoNumber());
		pk.setKokyakuId(domain.getKokyaku().getRecordId());
		boolean exists = uriageCrud.exists(pk);
		if (exists) {
			throw new AlreadyExistsException("売上伝票", domain.getDenpyoNumber());
		}
	}

	/**
	 * 明細リスト=0件の場合、エラー
	 * 
	 * @param domain
	 *            売上
	 * @throws EmptyException
	 *             売上明細を設定する必要があります。
	 */
	void validateMeisaiSize(Uriage domain) throws EmptyException {
		List<UriageMeisai> meisaiList = domain.getUriageMeisai();
		if (meisaiList.size() == 0) {
			throw new EmptyException("売上明細");
		}
	}

	/**
	 * 計上済みの場合、エラー
	 * 
	 * @param domain
	 *            売上
	 * @throws CanNotUpdateOrDeleteException
	 *             更新・削除不可（計上済みの売上のため）
	 */
	void validateKeijo(Uriage domain) throws CanNotUpdateOrDeleteException {
		boolean keijoZumi = uriageKeijoQuery.hasDone(domain);
		if (keijoZumi) {
			throw new CanNotUpdateOrDeleteException("計上済の売上のため");
		}
	}

	/**
	 * キャンセル済みの場合、エラー
	 * 
	 * @param domain
	 *            売上
	 * @throws CanNotUpdateOrDeleteException
	 *             更新・削除不可（キャンセル済みの売上のため)
	 */
	void validateCanceld(Uriage domain) throws CanNotUpdateOrDeleteException {
		boolean exists = cUriageRepository.existsById(domain.getRecordId());
		if (exists) {
			throw new CanNotUpdateOrDeleteException("キャンセル済の売上のため");
		}
	}

	/**
	 * 売上日が営業日でない場合、エラー
	 * 
	 * @param domain
	 *            売上
	 * @throws NotEigyoDateException
	 *             売上日: yyyy/MM/dd は営業日ではありません。
	 */
	void validateUriageDate(Uriage domain) throws NotEigyoDateException {
		Busho busho = domain.getKokyaku().getShukanBusho();
		TheDate date = domain.getUriageDate();
		boolean isEigyoDate = bushoDateQuery.isEigyoDate(busho, date);
		if (!isEigyoDate) {
			throw new NotEigyoDateException("売上日", date);
		}
	}

	/**
	 * 消込データがある場合、エラー
	 * 
	 * @param domain
	 *            売上
	 * @throws CanNotUpdateOrDeleteException
	 *             更新・削除できません。(すでに消込が存在するため)
	 */
	void validateHasKeshikomi(Uriage domain) throws CanNotUpdateOrDeleteException {
		String uriageId = domain.getRecordId();
		boolean hasUrikake = urikakeCrud.exists(uriageId);
		if (!hasUrikake) {
			return;
		}
		Urikake urikake = urikakeCrud.getDomain(uriageId);
		String urikakeId = urikake.getRecordId();
		UrikakeKeshikomi urikakeKeshikomi = urikakeKeshikomiQuery.get(urikakeId);
		if (urikakeKeshikomi.hasKeshikomi()) {
			throw new CanNotUpdateOrDeleteException("すでに消込が存在するため");
		}
	}
}
