package com.showka.service.validator.u05;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.showka.domain.u05.Uriage;
import com.showka.domain.u05.UriageMeisai;
import com.showka.domain.z00.Busho;
import com.showka.entity.TUriagePK;
import com.showka.repository.i.CUriageRepository;
import com.showka.repository.i.TUriageRepository;
import com.showka.service.crud.u05.i.UriageCrud;
import com.showka.service.query.u05.i.UriageKeijoQuery;
import com.showka.service.query.z00.i.BushoDateQuery;
import com.showka.service.validator.u05.i.UriageMeisaiValidator;
import com.showka.service.validator.u05.i.UriageValidator;
import com.showka.system.exception.AlreadyExistsException;
import com.showka.system.exception.CanNotUpdateOrDeleteException;
import com.showka.system.exception.EmptyException;
import com.showka.system.exception.NotEigyoDateException;
import com.showka.system.exception.ValidateException;
import com.showka.value.TheDate;

@Service
public class UriageValidatorImpl implements UriageValidator {

	@Autowired
	private TUriageRepository uriageRepo;

	@Autowired
	private UriageCrud uriageCrud;

	@Autowired
	private UriageMeisaiValidator uriageMeisaiValidate;

	@Autowired
	private CUriageRepository cUriageRepository;

	@Autowired
	private UriageKeijoQuery uriageKeijoQuery;

	@Autowired
	private BushoDateQuery bushoDateBusinessService;

	@Override
	public void validate(Uriage domain) throws ValidateException {
		// 明細なしエラー
		List<? extends UriageMeisai> meisaiList = domain.getUriageMeisai();
		if (CollectionUtils.isEmpty(meisaiList)) {
			throw new EmptyException("売上明細");
		}
		// 明細検証
		for (UriageMeisai meisai : meisaiList) {
			uriageMeisaiValidate.validate(meisai);
		}
	}

	@Override
	public void validateForRegister(Uriage domain) throws ValidateException {
		// 売上日が営業日でない場合、エラー
		this.validateUriageDate(domain);
		// 既存チェック
		TUriagePK pk = new TUriagePK();
		pk.setDenpyoNumber(domain.getDenpyoNumber());
		pk.setKokyakuId(domain.getKokyaku().getRecordId());
		boolean exists = uriageRepo.existsById(pk);
		if (exists) {
			throw new AlreadyExistsException("売上伝票", domain.getDenpyoNumber());
		}
	}

	@Override
	public void validateForUpdate(Uriage domain) throws ValidateException {
		boolean exists = cUriageRepository.existsById(domain.getRecordId());
		if (exists) {
			throw new CanNotUpdateOrDeleteException("キャンセル済の売上のため");
		}
	}

	@Override
	public void validateForDelete(TUriagePK pk) throws ValidateException {
		Uriage domain = uriageCrud.getDomain(pk);
		// キャンセル済み検証
		boolean exists = cUriageRepository.existsById(domain.getRecordId());
		if (exists) {
			throw new CanNotUpdateOrDeleteException("キャンセル済の売上のため");
		}
		// 計上済み検証
		boolean keijoZumi = uriageKeijoQuery.hasDone(domain);
		if (keijoZumi) {
			throw new CanNotUpdateOrDeleteException("計上済の売上のため");
		}
	}

	@Override
	public void validateForCancel(TUriagePK pk) throws ValidateException {
		Uriage domain = uriageCrud.getDomain(pk);
		validateForUpdate(domain);
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
		boolean isEigyoDate = bushoDateBusinessService.isEigyoDate(busho, date);
		if (!isEigyoDate) {
			throw new NotEigyoDateException("売上日", date);
		}
	}
}
