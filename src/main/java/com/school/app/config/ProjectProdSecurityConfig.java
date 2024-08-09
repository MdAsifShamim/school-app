package com.school.app.config;

import com.school.app.exception.CustomAccessDeniedHandler;
import com.school.app.exception.CustomBasicAuthenticationEntryPoint;
import com.school.app.filter.CsrfCookieFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
@Profile("prod")
public class ProjectProdSecurityConfig {

    @Value("${schoolApp.version}")
    private String version;

    @Value("${schoolapp.allow.origin}")
    private String allowOrigin;

    @Bean
    SecurityFilterChain customSecurityFilter(HttpSecurity http) throws Exception {

        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName("_csrf");

        http
                //Session Control added invalidate session URL and Max Session with single User,and its login prevent
                .sessionManagement(smc->smc.invalidSessionUrl("/invalidSession")
                        .maximumSessions(2)
                        .maxSessionsPreventsLogin(true))

                //ACCEPT ONLY HTTPS REQUEST
                .requiresChannel(rcc->rcc.anyRequest().requiresSecure())

                //CORS Config
                .cors(csc->csc.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                        CorsConfiguration corsConfig=new CorsConfiguration();
                        corsConfig.setAllowedOrigins(List.of(allowOrigin)); //Allow 4200
                        corsConfig.setAllowedMethods(List.of("*")); //Allow all type of method
                        corsConfig.setAllowCredentials(true); //Allow authentication send with request
                        corsConfig.setAllowedHeaders(List.of("*"));  //Allow header
                        corsConfig.setMaxAge(3600L);
                        return corsConfig;
                    }
                }))
                //Handle CSRF TOKEN GENERATION
                .csrf(csrf -> csrf.csrfTokenRequestHandler(requestHandler)
                        .ignoringRequestMatchers(version+"/register-new-user")
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                //CUSTOM FILTER
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)

                .authorizeHttpRequests(request -> request.requestMatchers(version+"/register-new-user",
                        "/invalidSession").permitAll()
                        .requestMatchers(version+"/user-detail",version+"/all-user-detail").authenticated()
                );

        http.formLogin(Customizer.withDefaults());

        //Added to see custom Exception in POSTMAN
        http.httpBasic(hbc->hbc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
        http.exceptionHandling(exc->exc.accessDeniedHandler(new CustomAccessDeniedHandler()));


        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
