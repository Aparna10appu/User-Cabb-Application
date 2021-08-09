package com.cabapplication.user.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cabapplication.user.filters.JwtRequestFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	//for db auth
//	@Autowired
//	private MyUserDetailService myUserDetailService;
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	//for db authentication
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//			auth.userDetailsService(myUserDetailService).passwordEncoder(new BCryptPasswordEncoder());
//	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable()
			.authorizeRequests().antMatchers("/authenticate", "/login.html", "/admins").permitAll()
			.anyRequest().authenticated()

			.and()
				.formLogin().loginPage("/login.html")
			
			//if not authenticated redirect to login
			.and().exceptionHandling().authenticationEntryPoint((request, response, ex) -> response.sendRedirect("/login.html"))
			
			.and()
				.logout().logoutUrl("/logout")
				//back to login page
				.logoutSuccessUrl("/login.html")
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				.clearAuthentication(true);
				
//			.and()
//				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
				
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**", "/js/**", "/images/**", "/Fonts/**", "**.js", "**.css", "**.png");
	}
	
//	@Override
//	@Bean
//	public AuthenticationManager authenticationManagerBean() throws Exception {
//		return super.authenticationManagerBean();
//	}

}
