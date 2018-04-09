package com.showka.entity;

import java.util.Date;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(TUriage.class)
public abstract class TUriage_ {
	public static volatile SingularAttribute<TUriage, TUriagePK> pk;
	public static volatile SingularAttribute<TUriage, Date> uriageDate;
	public static volatile SingularAttribute<TUriage, Date> keijoDate;
	public static volatile SingularAttribute<TUriage, String> hanbaiKubun;
	public static volatile SingularAttribute<TUriage, Double> shohizeiritsu;
	public static volatile ListAttribute<TUriage, TUriageMeisai> meisai;
	public static volatile SingularAttribute<TUriage, MKokyaku> kokyaku;
	public static volatile SingularAttribute<TUriage, CUriage> cancel;
}