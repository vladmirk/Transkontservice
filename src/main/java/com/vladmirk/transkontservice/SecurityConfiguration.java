package com.vladmirk.transkontservice;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private AccessDeniedHandler accessDeniedHandler;


  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests().
        antMatchers("/webjars/**", "/css/**").permitAll();
    http.authorizeRequests().anyRequest().authenticated().and().formLogin().loginPage("/login").permitAll().and().logout().permitAll().and().csrf()
        .disable().headers().frameOptions().disable();


//        http.csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/login", "/webjars/**").permitAll()
//                .antMatchers("/admin/**").hasAnyRole("ADMIN")
//                .antMatchers("/*/game/**").hasAnyRole("USER")  // "/index", "/user/**"
//                .anyRequest().authenticated()
//                .and()
//                .formLogin().loginPage("/login").permitAll()
//                .and()
//                .logout().permitAll()
//                .and()
//                .exceptionHandling().accessDeniedHandler(accessDeniedHandler)
//                .and()
//                .csrf().disable()
//                .headers().frameOptions().disable();
  }

//    @Autowired
//    public void configureJPAUsers(AuthenticationManagerBuilder auth, AppUserDetailsService detailsService) throws Exception {
//        auth.userDetailsService(detailsService);
//    }
}
