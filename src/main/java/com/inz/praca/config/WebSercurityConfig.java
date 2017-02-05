package com.inz.praca.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
@EnableWebSecurity
@Profile("development")
public class WebSercurityConfig extends WebSecurityConfigurerAdapter {
	private static final String REMEMBER_ME = "remember-me";

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
				.inMemoryAuthentication()
				.withUser("administrator").password("administrator").roles("ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding("UTF-8");
		filter.setForceEncoding(true);
		http
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
				.and()
				.authorizeRequests()
				.antMatchers("/register**", "/login**")
				.permitAll()
				// .antMatchers(HttpMethod.POST,"/api/**").hasAuthority("ADMIN")
				// .antMatchers(HttpMethod.PUT,"/api/**").hasAuthority("ADMIN")
				// .antMatchers(HttpMethod.DELETE,"/api/**").hasAuthority("ADMIN")
				.anyRequest().fullyAuthenticated()

				.and()
				.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/products")
				.failureUrl("/login-error")
				.permitAll()
				.and()
				.rememberMe()
				.and()
				.logout()
				.logoutUrl("/logout")
				.deleteCookies(REMEMBER_ME)
				.logoutSuccessUrl("/login")
				.permitAll()
				.and()
				.csrf().disable().headers().frameOptions().disable();

	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web
				.ignoring()
				.antMatchers("/resources/**");
	}
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
				.userDetailsService(userDetailsService)
				.passwordEncoder(new BCryptPasswordEncoder());
	}
}
