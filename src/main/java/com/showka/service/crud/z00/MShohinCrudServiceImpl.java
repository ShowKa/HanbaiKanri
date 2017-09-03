package com.showka.service.crud.z00;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.ShohinDomain;
import com.showka.domain.builder.ShohinDomainBuilder;
import com.showka.entity.MShohin;
import com.showka.repository.i.MShohinRepository;
import com.showka.service.crud.z00.i.MShohinCrudService;

/**
 * 商品CRUDサービス
 * 
 * @author ShowKa
 *
 */
@Service
public class MShohinCrudServiceImpl implements MShohinCrudService {

	/**
	 * 商品マスタリポジトリ.
	 */
	@Autowired
	private MShohinRepository repo;

	@Override
	public ShohinDomain getDomain(String code) {
		// get entity
		MShohin e = repo.getOne(code);

		// set up builder
		ShohinDomainBuilder b = new ShohinDomainBuilder();
		b.withCode(e.getCode());
		b.withHyojunTanka(BigDecimal.valueOf(e.getHyojunTanka()));
		b.withName(e.getName());
		b.withRecordId(e.getRecordId());
		b.withVersion(e.getVersion());

		// build domain
		ShohinDomain m = b.build();
		return m;
	}

}
