package com.showka.service.query.u11;

import org.springframework.stereotype.Service;

import com.showka.domain.z00.Busho;
import com.showka.service.query.u11.i.ShohinTanaoroshiQuery;
import com.showka.value.TheTimestamp;

@Service
public class ShohinTanaoroshiQueryImpl implements ShohinTanaoroshiQuery {
	// ダミー実装
	// 設計中のためすべてfalseで返却。
	@Override
	public boolean onGoing(Busho busho) {
		return false;
	}

	@Override
	public boolean doneBetween(Busho busho, TheTimestamp start, TheTimestamp end) {
		return false;
	}

	@Override
	public boolean doneBetween(Busho busho, TheTimestamp start) {
		return false;
	}
}
