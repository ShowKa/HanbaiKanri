package com.showka.entity;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(TUrikake.class)
public abstract class TUrikake_ {
	public static volatile SingularAttribute<TUrikake, String> uriageId;
	public static volatile SingularAttribute<TUrikake, Integer> kingaku;
	public static volatile SingularAttribute<TUrikake, Date> nyukinYoteiDate;
	public static volatile SingularAttribute<TUrikake, TUriage> uriage;
}
