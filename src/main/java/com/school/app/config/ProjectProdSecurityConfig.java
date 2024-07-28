package com.school.app.config;

import com.school.app.filter.CsrfCookieFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

@Configuration
@Profile("prod")
public class ProjectProdSecurityConfig {

    @Bean
    SecurityFilterChain customSecurityFilter(HttpSecurity http) throws Exception {

        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName("_csrf");

        http
                //Session Control added invalidate session URL and Max Session with single User,and its login prevent
                .sessionManagement(smc->smc.invalidSessionUrl("/invalidSession")
                        .maximumSessions(2)
                        .maxSessionsPreventsLogin(true))

                //Accept only HTTP REQUEST requiresSecure will only accept HTTPS Request
               // .requiresChannel(rcc->rcc.anyRequest().requiresInsecure())

                //Handle CSRF TOKEN GENERATION
                .csrf(csrf -> csrf.csrfTokenRequestHandler(requestHandler)
                        .ignoringRequestMatchers("/app/v1/register-new-user")
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))

                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests(request -> request.requestMatchers("/app/v1/register-new-user", "/app/v1/register/student","/invalidSession").permitAll()

                );

        http.formLogin(Customizer.withDefaults());
        http.httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
