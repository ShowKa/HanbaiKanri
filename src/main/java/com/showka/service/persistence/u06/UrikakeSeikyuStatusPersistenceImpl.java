package com.showka.service.persistence.u06;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.u07.Seikyu;
import com.showka.entity.SUrikakeSeikyuDone;
import com.showka.entity.SUrikakeSeikyuNotYet;
import com.showka.repository.i.SUrikakeSeikyuDoneRepository;
import com.showka.repository.i.SUrikakeSeikyuNotYetRepository;
import com.showka.service.persistence.u06.i.UrikakeSeikyuStatusPersistence;
import com.showka.service.search.u07.i.SeikyuSearchService;

@Service
public class UrikakeSeikyuStatusPersistenceImpl implements UrikakeSeikyuStatusPersistence {

	@Autowired
	private SUrikakeSeikyuNotYetRepository seikyuNotYetRepo;

	@Autowired
	private SUrikakeSeikyuDoneRepository seikyuDoneRepo;

	@Autowired
	private SeikyuSearchService seikyuSearchService;

	@Override
	public void toNotYet(String urikakeId) {
		// 未請求状態とする
		Optional<SUrikakeSeikyuNotYet> _e = seikyuNotYetRepo.findById(urikakeId);
		SUrikakeSeikyuNotYet e = _e.orElse(new SUrikakeSeikyuNotYet());
		e.setUrikakeId(urikakeId);
		if (!_e.isPresent()) {
			// ID = 売掛ID
			e.setRecordId(urikakeId);
		}
		seikyuNotYetRepo.save(e);
	}

	@Override
	public void toDone(String urikakeId, String seikyuId) {
		// 未請求状態解除
		this.deleteNotYetIfExists(urikakeId);
		// 請求済状態とする
		Optional<SUrikakeSeikyuDone> _e = seikyuDoneRepo.findById(urikakeId);
		SUrikakeSeikyuDone e = _e.orElse(new SUrikakeSeikyuDone());
		e.setUrikakeId(urikakeId);
		e.setSeikyuId(seikyuId);
		if (!_e.isPresent()) {
			// ID = 売掛ID
			e.setRecordId(urikakeId);
		}
		seikyuDoneRepo.save(e);
	}

	@Override
	public void revert(String urikakeId) {
		// レコード存在チェック
		// 既存の場合は何もせず処理終了。
		boolean exists = seikyuDoneRepo.existsById(urikakeId);
		if (exists) {
			return;
		}
		// 最新請求を取得。
		// 未請求の場合、未請求状態とする。
		Optional<Seikyu> _newestSeikyu = seikyuSearchService.getNewestOf(urikakeId);
		if (!_newestSeikyu.isPresent()) {
			this.toNotYet(urikakeId);
			return;
		}
		// 最新請求
		Seikyu seikyu = _newestSeikyu.get();
		// save
		this.toDone(urikakeId, seikyu.getRecordId());
	}

	@Override
	public void toSettled(String urikakeId) {
		this.deleteNotYetIfExists(urikakeId);
		this.deleteDoneIfExists(urikakeId);
	}

	@Override
	public void delete(String urikakeId) {
		this.deleteNotYetIfExists(urikakeId);
		this.deleteDoneIfExists(urikakeId);
	}

	/**
	 * 未請求状態テーブルのレコードを削除（存在すれば）
	 * 
	 * @param urikakeId
	 *            売掛ID
	 */
	void deleteNotYetIfExists(String urikakeId) {
		boolean exists = seikyuNotYetRepo.existsById(urikakeId);
		if (exists) {
			seikyuNotYetRepo.deleteById(urikakeId);
		}
	}

	/**
	 * 請求済状態テーブルのレコードを削除（存在すれば）
	 * 
	 * @param urikakeId
	 *            売掛ID
	 */
	void deleteDoneIfExists(String urikakeId) {
		boolean exists = seikyuDoneRepo.existsById(urikakeId);
		if (exists) {
			seikyuDoneRepo.deleteById(urikakeId);
		}
	}
}
