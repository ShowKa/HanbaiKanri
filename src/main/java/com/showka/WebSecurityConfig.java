package com.showka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
		http.headers()
				.frameOptions()
				.sameOrigin()
				.and()
				.csrf()
				.disable()
				.authorizeRequests()
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

	@Configuration
	static class AuthenticationConfig extends GlobalAuthenticationConfigurerAdapter {

		@Autowired
		private UserDetailsService userDetailsService;

		@Bean
		public PasswordEncoder passwordEncoder() {
			// パスワードの暗号化方式を宣言
			// ただし現時点ではテスト用のダミーエンコーダを使用
			return NoOpPasswordEncoder.getInstance();
		}

		@Override
		public void init(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		}

	}

}
