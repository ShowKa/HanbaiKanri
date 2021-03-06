package com.showka.service.crud.z00;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.builder.ShohinBuilder;
import com.showka.domain.z00.Shohin;
import com.showka.entity.MShohin;
import com.showka.repository.i.MShohinRepository;
import com.showka.service.crud.z00.i.ShohinCrud;

/**
 * 商品CRUDサービス
 * 
 * @author ShowKa
 *
 */
@Service
public class ShohinCrudImpl implements ShohinCrud {

	/**
	 * 商品マスタリポジトリ.
	 */
	@Autowired
	private MShohinRepository repo;

	@Override
	public Shohin getDomain(String code) {
		// get entity
		MShohin e = repo.getOne(code);
		// set up builder
		ShohinBuilder b = new ShohinBuilder();
		b.withCode(e.getCode());
		b.withHyojunTanka(BigDecimal.valueOf(e.getHyojunTanka()));
		b.withName(e.getName());
		b.withRecordId(e.getRecordId());
		b.withVersion(e.getVersion());
		// build domain
		Shohin m = b.build();
		return m;
	}

	@Override
	public void save(Shohin domain) {
		throw new RuntimeException("未実装");
	}

	@Override
	public void delete(Shohin domain) {
		throw new RuntimeException("未実装");
	}

	@Override
	public boolean exists(String pk) {
		return repo.existsById(pk);
	}
}
