package com.showka.service.query.u08;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.showka.domain.u08.Nyukin;
import com.showka.entity.JNyukinFBFurikomi;
import com.showka.repository.i.JNyukinFBFurikomiRepository;
import com.showka.service.crud.u08.i.NyukinCrud;
import com.showka.service.query.u08.i.NyukinFBFurikomiQuery;

@Service
public class NyukinFBFurikomiQueryImpl implements NyukinFBFurikomiQuery {

	@Autowired
	private NyukinCrud nyukinCrud;

	@Autowired
	private JNyukinFBFurikomiRepository repo;

	@Override
	public Nyukin getNyukin(String fbFurikomiId) {
		JNyukinFBFurikomi result = this.findByFbFurikomiId(fbFurikomiId);
		String nyukinId = result.getNyukinId();
		Nyukin nyukin = nyukinCrud.getDomain(nyukinId);
		return nyukin;
	}

	/**
	 * 入金FB振込レコード取得.
	 * 
	 * @param fbFurikomiId
	 *            FB振込ID
	 * @return 入金FB振込レコード
	 */
	JNyukinFBFurikomi findByFbFurikomiId(String fbFurikomiId) {
		JNyukinFBFurikomi e = new JNyukinFBFurikomi();
		e.setFbFurikomiId(fbFurikomiId);
		Example<JNyukinFBFurikomi> example = Example.of(e);
		JNyukinFBFurikomi result = repo.findOne(example).get();
		return result;
	}
}
