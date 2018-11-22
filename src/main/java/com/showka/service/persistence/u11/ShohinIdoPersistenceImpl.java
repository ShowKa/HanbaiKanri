package com.showka.service.persistence.u11;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.u11.ShohinIdo;
import com.showka.service.crud.u11.i.ShohinIdoCrud;
import com.showka.service.persistence.u11.i.ShohinIdoPersistence;
import com.showka.service.specification.u11.i.ShohinIdoSpecification;
import com.showka.system.exception.UnsatisfiedSpecificationException;

@Service
public class ShohinIdoPersistenceImpl implements ShohinIdoPersistence {

	@Autowired
	private ShohinIdoCrud shohinIdoCrud;

	@Override
	public void shohinIdo(ShohinIdoSpecification specification) throws UnsatisfiedSpecificationException {
		// 業務的仕様を満たすか判定
		specification.ascertainSatisfaction();
		// 削除対象の商品移動を削除
		List<ShohinIdo> idoListForDelete = specification.getShohinIdoForDelete();
		idoListForDelete.forEach(d -> shohinIdoCrud.delete(d));
		// 新たに商品移動を登録
		List<ShohinIdo> idoList = specification.getShohinIdo();
		idoList.forEach(shohinIdoCrud::save);
	}

	@Override
	public void shohinIdoForcibly(ShohinIdoSpecification specification) {
		// 削除対象の商品移動を削除
		List<ShohinIdo> idoListForDelete = specification.getShohinIdoForDelete();
		idoListForDelete.forEach(d -> shohinIdoCrud.delete(d));
		// 新たに商品移動を登録
		List<ShohinIdo> idoList = specification.getShohinIdo();
		idoList.forEach(shohinIdoCrud::save);
	}
}
