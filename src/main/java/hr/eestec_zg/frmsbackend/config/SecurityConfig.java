package hr.eestec_zg.frmsbackend.config;

import hr.eestec_zg.frmsbackend.config.security.CustomUserDetailsService;
import hr.eestec_zg.frmsbackend.config.security.Http401UnauthorizedEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        // set custom service for authentication
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        // change response code to 401 when unauthorized
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint());
        http
                .authorizeRequests().anyRequest().authenticated()
                .and().httpBasic();
//        http
//                .formLogin()
//                .loginProcessingUrl("/authenticate")
//                .successHandler(authSuccessHandler)
//                .failureHandler(authFailureHandler)
//                .permitAll()
//                .and()
//
//                .logout()
//                .logoutUrl("/logout")
//                .logoutSuccessHandler(logoutSuccessHandler)
//                .deleteCookies("JSESSIONID")
//                .permitAll()
//                .and()
//
//                .authorizeRequests()
//                .antMatchers("/initialize-admin-account").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .httpBasic();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new Http401UnauthorizedEntryPoint();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
}
