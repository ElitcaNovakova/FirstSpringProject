package bg.startit.spring.firstspringproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf()
        /**/.disable()
        .cors()
        /**/.disable()
        .headers()
        /**/.frameOptions().sameOrigin().and()
        .formLogin()
        /**/.and()
        .httpBasic()
        /**/.and()
        .logout()
        /**/.and()
        .authorizeRequests()
        /**/.antMatchers("/h2-console/**").permitAll()
        /**/.antMatchers("/explorer/**").authenticated()
        /**/.antMatchers("/users/**").authenticated()
        /**/.antMatchers("/books/**").authenticated();
    //  /**/.antMatchers("/api/v1/**").authenticated();
  }

  @Bean
  PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
