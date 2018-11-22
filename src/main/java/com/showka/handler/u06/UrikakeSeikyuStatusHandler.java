package com.showka.handler.u06;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.showka.domain.u06.Urikake;
import com.showka.domain.u06.UrikakeKeshikomi;
import com.showka.domain.u07.Seikyu;
import com.showka.domain.u08.Keshikomi;
import com.showka.handler.CrudEventListener;
import com.showka.service.persistence.u06.i.UrikakeKeshikomiPersistence;
import com.showka.service.persistence.u06.i.UrikakePersistence;
import com.showka.service.persistence.u06.i.UrikakeSeikyuStatusPersistence;

@Component
public class UrikakeSeikyuStatusHandler {

	@Autowired
	private UrikakePersistence urikakePersistence;

	@Autowired
	private UrikakeKeshikomiPersistence urikakeKeshikomiPersistence;

	@Autowired
	private UrikakeSeikyuStatusPersistence urikakeSeikyuStatusPersistence;

	@Component
	public class Handler1 implements CrudEventListener<Urikake> {
		/**
		 * 売掛新規登録後、請求状態=未請求とする。
		 */
		@Override
		public void afterNewRegister(Urikake urikake) {
			urikakeSeikyuStatusPersistence.toNotYet(urikake.getUriageId());
		}

		/**
		 * 売掛削除時、請求状態をすべて削除する。
		 */
		@Override
		public void beforeDelete(Urikake urikake) {
			urikakeSeikyuStatusPersistence.delete(urikake.getUriageId());
		}
	}

	@Component
	public class Handler2 implements CrudEventListener<Seikyu> {
		/**
		 * 請求保存後
		 * 
		 * <pre>
		 * 売掛.入金予定日 = 請求.支払日
		 * 売掛 => 請求状態=請求済とする。
		 * </pre>
		 */
		@Override
		public void afterSave(Seikyu seikyu) {
			seikyu.getSeikyuMeisai().forEach(m -> {
				Urikake urikake = m.getUrikake();
				urikake.updateNyukinYoteiDate(seikyu.getShiharaiDate());
				urikakePersistence.save(urikake);
				urikakeSeikyuStatusPersistence.toDone(urikake.getRecordId(), seikyu.getRecordId());
			});
		}
	}

	@Component
	public class Handler3 implements CrudEventListener<Keshikomi> {
		/**
		 * 消込後
		 * 
		 * <pre>
		 * 売掛の消込完了なら、完済とする。
		 * 未完了なら、そのまま。
		 * </pre>
		 */
		@Override
		public void afterSave(Keshikomi keshikomi) {
			String urikakeId = keshikomi.getUrikakeId();
			UrikakeKeshikomi urikakeKeshikomoi = urikakeKeshikomiPersistence.getDomain(urikakeId);
			if (urikakeKeshikomoi.done()) {
				urikakeSeikyuStatusPersistence.toSettled(urikakeId);
			} else {
				urikakeSeikyuStatusPersistence.revert(urikakeId);
			}
		}

		/**
		 * 消込削除後、売掛.請求状態=請求済みに戻す。
		 */
		@Override
		public void afterDelete(Keshikomi keshikomi) {
			String urikakeId = keshikomi.getUrikakeId();
			urikakeSeikyuStatusPersistence.revert(urikakeId);
		}
	}
}