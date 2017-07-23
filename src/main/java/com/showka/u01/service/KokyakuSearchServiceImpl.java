package com.showka.u01.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.showka.entity.MBusho;
import com.showka.entity.MKokyaku;
import com.showka.repository.i.MKokyakuRepository;
import com.showka.u01.service.i.KokyakuSearchService;

/**
 * 顧客Crud
 * 
 * @author ShowKa
 *
 */
@Service
@Transactional
public class KokyakuSearchServiceImpl implements KokyakuSearchService {

	/**
	 * 顧客Repository
	 * 
	 */
	@Autowired
	private MKokyakuRepository repository;

	/**
	 * 顧客名検索.
	 */
	@Override
	public List<MKokyaku> getKokyauByName(String name) {

		// 条件
		MKokyaku k = new MKokyaku();
		k.setName(name);
		ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("name", match -> match.startsWith());
		Example<MKokyaku> example = Example.of(k, matcher);

		// 検索
		List<MKokyaku> kokyakuList = repository.findAll(example);
		return kokyakuList;
	}

	/**
	 * 全件検索
	 * 
	 * @return
	 */
	public List<MKokyaku> getAllKokyaku() {
		return repository.findAll();
	}

	/**
	 * 顧客検索
	 */
	@Override
	public List<MKokyaku> search(KokyakuSearchCriteria criteria) {
		// 条件
		// 顧客名
		MKokyaku k = new MKokyaku();
		k.setName(criteria.getName());

		// 部署名
		MBusho b = new MBusho();
		b.setName(criteria.getBushoName());
		k.setShukanBusho(b);

		ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("name", match -> match.startsWith());
		Example<MKokyaku> example = Example.of(k, matcher);

		// 検索
		List<MKokyaku> kokyakuList = repository.findAll(example);
		return kokyakuList;
	}

}
