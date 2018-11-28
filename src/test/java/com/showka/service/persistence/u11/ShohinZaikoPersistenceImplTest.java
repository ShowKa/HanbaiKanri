package com.showka.service.persistence.u11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.builder.BushoBuilder;
import com.showka.domain.builder.ShohinBuilder;
import com.showka.domain.builder.ShohinZaikoBuilder;
import com.showka.domain.u11.ShohinZaiko;
import com.showka.domain.z00.Busho;
import com.showka.domain.z00.Shohin;
import com.showka.entity.TShohinZaikoPK;
import com.showka.service.crud.u11.i.ShohinZaikoCrud;
import com.showka.service.query.u11.i.ShohinZaikoQuery;
import com.showka.service.query.z00.i.BushoDateQuery;
import com.showka.value.EigyoDate;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

public class ShohinZaikoPersistenceImplTest extends SimpleTestCase {

	@Tested
	private ShohinZaikoPersistenceImpl service;

	@Injectable
	private ShohinZaikoCrud shohinZaikoCrud;

	@Injectable
	private BushoDateQuery bushoDateQuery;

	@Injectable
	private ShohinZaikoQuery shohinZaikoQuery;

	/**
	 * 繰越.
	 * 
	 * <pre>
	 * 入力：商品在庫<br>
	 * 条件：在庫あり<br>
	 * 結果：次営業日の在庫データができる。
	 * 
	 * <pre>
	 */
	@Test
	public void test_kurikoshi_01() throws Exception {
		// input
		// 部署
		BushoBuilder bb = new BushoBuilder();
		bb.withCode("BS01");
		Busho busho = bb.build();
		// 営業日
		EigyoDate eigyoDate = new EigyoDate(2017, 1, 1);
		EigyoDate nextEigyoDate = new EigyoDate(2017, 1, 2);
		// mock
		// 商品
		ShohinBuilder sb = new ShohinBuilder();
		sb.withCode("SH01");
		Shohin shohin = sb.build();
		// 在庫
		ShohinZaikoBuilder szb = new ShohinZaikoBuilder();
		szb.withBusho(busho);
		szb.withDate(eigyoDate);
		szb.withShohin(shohin);
		szb.withKurikoshiNumber(1);
		szb.withShohinIdoList(new ArrayList<>());
		ShohinZaiko zaiko = szb.build();
		List<ShohinZaiko> zaikoList = Arrays.asList(zaiko);
		// 在庫繰越
		ShohinZaiko kurikoshi = new ShohinZaikoBuilder().withDate(nextEigyoDate).apply(zaiko);
		new Expectations() {
			{
				shohinZaikoQuery.get(busho, eigyoDate);
				result = zaikoList;
				bushoDateQuery.getNext(busho, eigyoDate);
				result = nextEigyoDate;
				shohinZaikoCrud.save(kurikoshi);
			}
		};
		// do
		service.kurikoshi(busho, eigyoDate);
		// verify
		new Verifications() {
			{
				shohinZaikoQuery.get(busho, eigyoDate);
				times = 1;
				bushoDateQuery.getNext(busho, eigyoDate);
				times = 1;
				shohinZaikoCrud.save(kurikoshi);
				times = 1;
			}
		};
	}

	/**
	 * 繰越.
	 * 
	 * <pre>
	 * 入力：商品在庫<br>
	 * 条件：在庫なし<br>
	 * 結果：次営業日の在庫データができない。
	 * 
	 * <pre>
	 */
	@Test
	public void test_kurikoshi_02() throws Exception {
		// input
		// 部署
		BushoBuilder bb = new BushoBuilder();
		bb.withCode("BS01");
		Busho busho = bb.build();
		// 営業日
		EigyoDate eigyoDate = new EigyoDate(2017, 1, 1);
		EigyoDate nextEigyoDate = new EigyoDate(2017, 1, 2);
		// mock
		// 商品
		ShohinBuilder sb = new ShohinBuilder();
		sb.withCode("SH01");
		Shohin shohin = sb.build();
		// 在庫
		ShohinZaikoBuilder szb = new ShohinZaikoBuilder();
		szb.withBusho(busho);
		szb.withDate(eigyoDate);
		szb.withShohin(shohin);
		szb.withKurikoshiNumber(0);
		szb.withShohinIdoList(new ArrayList<>());
		ShohinZaiko zaiko = szb.build();
		List<ShohinZaiko> zaikoList = Arrays.asList(zaiko);
		new Expectations() {
			{
				shohinZaikoQuery.get(busho, eigyoDate);
				result = zaikoList;
				bushoDateQuery.getNext(busho, eigyoDate);
				result = nextEigyoDate;
			}
		};
		// do
		service.kurikoshi(busho, eigyoDate);
		// verify
		new Verifications() {
			{
				shohinZaikoQuery.get(busho, eigyoDate);
				times = 1;
				bushoDateQuery.getNext(busho, eigyoDate);
				times = 1;
				shohinZaikoCrud.save((ShohinZaiko) any);
				times = 0;
			}
		};
	}

	/**
	 * ゼロ在庫レコード登録.
	 * 
	 * <pre>
	 * 入力：部署、日付、商品
	 * 条件：在庫レコード未登録
	 * 結果：ゼロの在庫レコードが登録される
	 * </pre>
	 */
	@Test
	public void test_saveZeroIfEmpty_01() throws Exception {
		// input
		// 部署
		Busho busho = new BushoBuilder().build();
		busho.setRecordId("r-BS01");
		// 日付
		EigyoDate date = new EigyoDate(2017, 1, 1);
		// 商品
		Shohin shohin = new ShohinBuilder().build();
		shohin.setRecordId("r-SH01");
		// mock
		TShohinZaikoPK pk = new TShohinZaikoPK();
		pk.setBushoId("r-BS01");
		pk.setShohinId("r-SH01");
		pk.setEigyoDate(date.toDate());
		// expect
		new Expectations() {
			{
				shohinZaikoCrud.exists(pk);
				result = false;
			}
		};
		// do
		service.saveZeroIfEmpty(busho, date, shohin);
		// verify
		new Verifications() {
			{
				shohinZaikoCrud.exists(pk);
				times = 1;
				shohinZaikoCrud.save((ShohinZaiko) any);
				times = 1;
			}
		};
	}

	/**
	 * ゼロ在庫レコード登録.
	 * 
	 * <pre>
	 * 入力：部署、日付、商品
	 * 条件：在庫レコード登録済
	 * 結果：ゼロの在庫レコードが登録されない（既存のまま）
	 * </pre>
	 */
	@Test
	public void test_saveZeroIfEmpty_02() throws Exception {
		// input
		// 部署
		Busho busho = new BushoBuilder().build();
		busho.setRecordId("r-BS01");
		// 日付
		EigyoDate date = new EigyoDate(2017, 1, 1);
		// 商品
		Shohin shohin = new ShohinBuilder().build();
		shohin.setRecordId("r-SH01");
		// mock
		TShohinZaikoPK pk = new TShohinZaikoPK();
		pk.setBushoId("r-BS01");
		pk.setShohinId("r-SH01");
		pk.setEigyoDate(date.toDate());
		// expect
		new Expectations() {
			{
				shohinZaikoCrud.exists(pk);
				result = true;
			}
		};
		// do
		service.saveZeroIfEmpty(busho, date, shohin);
		// verify
		new Verifications() {
			{
				shohinZaikoCrud.exists(pk);
				times = 1;
				shohinZaikoCrud.save((ShohinZaiko) any);
				times = 0;
			}
		};
	}
}
