package com.spring.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// this annotation tells spring that this class is a web security configuration class
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	/**
	 * used for AUTHENTICATION
	 * when we override this method spring will call our method instead of the default one
	 * it takes AuthenticationManagerBuilder which help us to configure AuthenticationManager
	 * as we directly dont use it
	 *
	 * This methods does inMemoryAuthentication so for hdbc authentication use
	 * authenticationManagerBuilder.jdbcAuthentication() method
 	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// here we have to set authentication to the auth object
		// here we will do in memory authentication
		// we can use ".and" to add multiple users
		auth.inMemoryAuthentication()
				.withUser("abhishek").password("star-tech").roles("admin")
				.and()
				.withUser("warrior").password("war").roles("user");
	}

	/**
	 * used for AUTHORIZATION
	 * HttpSecurity  lets us configure what are the paths and
	 * what are the access restrictions to those paths.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// here we are setting access to url based on there roles
		http.authorizeRequests()
				.antMatchers("/admin").hasRole("admin")
				.antMatchers("/user").hasAnyRole("user", "admin")   // we do this so because sprin dont know that admin is of higher role
				.antMatchers("/").permitAll()
				.and()
				.formLogin();
	}

	/**
	 * We also have to do password encoding
	 * So make a @Bean of PassWordEncoder and spring looks for all beans if any one of them is
	 * a password encoder then spring uses that.
	 */
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
}

/**
 *
 * << Extra Info >>
 *
 * H2 is an embedded database.
 * When we add an embedded database to spring boot creates the datasource itself.
 * Spring-security also has some default schema opinions which we can use.
 * The way to use default schema is
 * auth.jdbcAuthentication().dataSource(dataSource).withDefaultSchema()
 *     .withUser(User.withUsername("abhishek").password("786").roles("admin"))
 *      .withUser(User.withUsername("sharma").password("786").roles("user"));
 *
 *
 *  To override spring boot default expectation
 *  use methods these except query as parameter :-
 *  authenticationManagerBuilder.usersByUsernameQuery("select ....") ,
 *  authenticationManagerBuilder.authoritiesByUsernameQuery("select ....")
 *
 *
 */