package com.showka.service.crud.z00;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.showka.entity.MUser;
import com.showka.repository.i.MUserRepository;

@Service
public class UserCrudServiceImpl implements UserDetailsService {

	/**
	 * ユーザーマスタリポジトリ.
	 */
	@Autowired
	private MUserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MUser user = new MUser();
		user.setUsername(username);
		Example<MUser> example = Example.of(user);
		Optional<MUser> member = repository.findOne(example);
		if (!member.isPresent()) {
			throw new UsernameNotFoundException("user not found!! : " + username);
		}
		return member.get();
	}

}
