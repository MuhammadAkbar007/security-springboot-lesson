package uz.pdp.appsecurityfirst.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    /**
     * User ochish uchun method override
     *
     * @param auth user keladi
     * @throws Exception exception e
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication() // spring ning vaqtinchalik xotirasida ushlab turish
                .withUser("director").password(passwordEncoder().encode("dir123")).roles("DIRECTOR")
                .authorities("READ_ALL_PRODUCT", "ADD_PRODUCT", "EDIT_PRODUCT", "DELETE_PRODUCT", "RAED_ONE_PRODUCT")

                .and()

                .withUser("manager").password(passwordEncoder().encode("man123")).roles("MANAGER")
                .authorities("READ_ALL_PRODUCT", "ADD_PRODUCT", "EDIT_PRODUCT", "RAED_ONE_PRODUCT")

                .and()

                .withUser("worker").password(passwordEncoder().encode("wor123")).roles("WORKER")
                .authorities("READ_ALL_PRODUCT", "RAED_ONE_PRODUCT");
    }


    /**
     * konfiguratsiya uchun kerak
     *
     * @param http security keladi
     * @throws Exception exception e
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()

                // role based authorisation
//                .antMatchers(HttpMethod.GET, "/api/product/*").hasRole("USER")
//                .antMatchers(HttpMethod.GET, "/api/product/**").hasAnyRole("DIRECTOR", "MANAGER")
//                .antMatchers("/api/product/**").hasRole("DIRECTOR")

                // permission based authorisation
//                .antMatchers(HttpMethod.DELETE, "/api/product/*").hasAuthority("DELETE_PRODUCT")
//                .antMatchers(HttpMethod.DELETE, "/api/product/**").hasAuthority("RAED_ALL_PRODUCT, READ_ONE_PRODUCT")
//                .antMatchers("/api/product/**").hasAnyAuthority("READ_ALL_PRODUCT", "ADD_PRODUCT", "EDIT_PRODUCT", "RAED_ONE_PRODUCT")
//                .antMatchers("/api/product/**").hasAnyAuthority("READ_ALL_PRODUCT", "ADD_PRODUCT", "EDIT_PRODUCT", "DELETE_PRODUCT", "RAED_ONE_PRODUCT")

                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }


    /**
     * passwordni yopiq holda saqlab beruvchi bean method
     *
     * @return encoded password
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
