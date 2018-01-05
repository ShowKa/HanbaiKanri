package com.showka.system;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.UriageDomain;
import com.showka.domain.UriageMeisaiDomain;
import com.showka.domain.UriageRirekiDomain;
import com.showka.domain.UriageRirekiMeisaiDomain;
import com.showka.domain.builder.UriageDomainBuilder;
import com.showka.domain.builder.UriageMeisaiDomainBuilder;
import com.showka.domain.builder.UriageRirekiDomainBuilder;
import com.showka.domain.builder.UriageRirekiMeisaiDomainBuilder;

public class BiStreamWrapperTest extends SimpleTestCase {

	@Test
	public void test() {

		UriageRirekiDomainBuilder urdb = new UriageRirekiDomainBuilder();
		UriageRirekiMeisaiDomainBuilder urmdb = new UriageRirekiMeisaiDomainBuilder();
		List<UriageRirekiMeisaiDomain> uriageMeisai = new ArrayList<UriageRirekiMeisaiDomain>();

		urmdb.withMeisaiNumber(1);
		uriageMeisai.add(urmdb.build());
		urmdb.withMeisaiNumber(2);
		uriageMeisai.add(urmdb.build());
		urdb.withUriageMeisai(uriageMeisai);
		UriageRirekiDomain old = urdb.build();
		List<UriageRirekiMeisaiDomain> oldMeisai = old.getUriageRirekiMeisai();

		UriageDomainBuilder udb = new UriageDomainBuilder();
		UriageMeisaiDomainBuilder umdb = new UriageMeisaiDomainBuilder();
		List<UriageMeisaiDomain> meisai = new ArrayList<UriageMeisaiDomain>();

		umdb.withMeisaiNumber(1);
		meisai.add(umdb.build());
		umdb.withMeisaiNumber(2);
		meisai.add(umdb.build());
		umdb.withMeisaiNumber(3);
		meisai.add(umdb.build());
		udb.withUriageMeisai(meisai);
		UriageDomain newOne = udb.build();
		List<UriageMeisaiDomain> newMeisai = newOne.getUriageMeisai();

		BiPredicate<UriageRirekiMeisaiDomain, UriageMeisaiDomain> p = (o, n) -> {
			return o.getMeisaiNumber().equals(n.getMeisaiNumber());
		};

		List<UriageMeisaiDomain> l = BiStreamWrapper.of(oldMeisai, newMeisai)
				.filterMatched(p)
				.second()
				.collect(Collectors.toList());

		assertEquals(2, l.size());
	}

}
