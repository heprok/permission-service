package com.briolink.permissionservice.api.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Configuration
class SecurityConfig : WebSecurityConfigurerAdapter() {

    private val AUTH_WHITELIST = arrayOf(
        // -- Swagger UI v2
        "/v2/api-docs",
        "/swagger-resources",
        "/swagger-resources/**",
        "/configuration/ui",
        "/configuration/security",
        "/swagger-ui.html",
        "/webjars/**",
        "/v3/api-docs/**",
        "/swagger-ui/**",
        "/api/v1/**/*",
    )

    override fun configure(http: HttpSecurity) {
        http
            .csrf {
                it.disable()
            }
            .authorizeRequests { reg ->
                reg
                    .antMatchers(
                        "/v2/api-docs",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/api/v1/**/*",
                    ).permitAll()
                    .anyRequest().authenticated()
            }
            .oauth2ResourceServer { configurer ->
                configurer
                    .jwt()
            }
    }
}
