package com.showka;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * WebSecurityConfig.
 * 
 * @author ShowKa
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	/**
	 * 認証済みでなかったら/u00g000 ログイン画面へ.
	 * 
	 * <pre>
	 * ログイン画面のレイアウトは整えたいので、静的コンテンツは無条件でアクセス許可.
	 * </pre>
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/", "/bower_components/**", "/common/**")
				.permitAll()
				.anyRequest()
				.authenticated()
				.and()
				.formLogin()
				.loginPage("/u00g000")
				.permitAll()
				.and()
				.logout()
				.permitAll();
	}

}
