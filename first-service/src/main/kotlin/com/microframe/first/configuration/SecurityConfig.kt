package com.microframe.first.configuration

import com.nimbusds.jose.proc.JWSAlgorithmFamilyJWSKeySelector
import com.nimbusds.jose.proc.JWSKeySelector
import com.nimbusds.jose.proc.SecurityContext
import com.nimbusds.jwt.proc.DefaultJWTProcessor
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.web.DefaultSecurityFilterChain
import java.net.URL


@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Value("\${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    private lateinit var jwkSetUri: String

//    @Bean
//    protected fun sessionAuthenticationStrategy(): SessionAuthenticationStrategy? {
//        return RegisterSessionAuthenticationStrategy(SessionRegistryImpl())
//    }
//
//    @Bean
//    @Throws(java.lang.Exception::class)
//    fun authenticationManager(http: HttpSecurity): AuthenticationManager? {
//        return http.getSharedObject(AuthenticationManagerBuilder::class.java)
//            .build()
//    }

    @Bean
    fun jwtDecoder(): JwtDecoder {
        // makes a request to the JWK Set endpoint
        val jwsKeySelector: JWSKeySelector<SecurityContext> = JWSAlgorithmFamilyJWSKeySelector.fromJWKSetURL<SecurityContext>(URL(jwkSetUri))
        val jwtProcessor: DefaultJWTProcessor<SecurityContext> = DefaultJWTProcessor()
        jwtProcessor.jwsKeySelector = jwsKeySelector
        return NimbusJwtDecoder(jwtProcessor)
    }

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): DefaultSecurityFilterChain? {
        http
            .authorizeHttpRequests().anyRequest().authenticated().and()
            .oauth2ResourceServer().jwt()
        return http.build()
    }
}