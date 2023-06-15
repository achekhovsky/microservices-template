package com.microframe.gateway.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler
import org.springframework.security.oauth2.client.registration.*
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler


@Configuration
class SecurityConfig {
    @Value("\${spring.security.oauth2.client.registration.keycloak.provider}")
    private var registrationId = "keycloak"
    @Value("\${spring.security.oauth2.client.registration.keycloak.client-id}")
    private var clientId = "microframe-realm-client"
    @Value("\${spring.security.oauth2.client.registration.keycloak.client-secret}")
    private var clientSecret = "V2cqkv1QNrxCssaBr1U6J8NkOkPfiJLa"
    @Value("\${spring.security.oauth2.client.registration.keycloak.client-authentication-method}")
    private var clientAuthenticationMethod = "client_secret_basic"
    @Value("\${spring.security.oauth2.client.registration.keycloak.authorization-grant-type}")
    private var authorizationGrantType = "authorization_code"
    @Value("\${spring.security.oauth2.client.registration.keycloak.redirect-uri}")
    private var redirectUri = "{baseUrl}/login/oauth2/code/{registrationId}"
    @Value("\${spring.security.oauth2.client.registration.keycloak.scope}")
    private var scope = arrayOf<String>("openid")
    @Value("\${spring.security.oauth2.client.provider.keycloak.authorization-uri}")
    private var authorizationUri = "http://localhost:9080/auth/realms/microframe/protocol/openid-connect/auth"
    @Value("\${spring.security.oauth2.client.provider.keycloak.token-uri}")
    private var tokenUri = "http://keycloak:8080/auth/realms/microframe/protocol/openid-connect/token"
    @Value("\${spring.security.oauth2.client.provider.keycloak.user-info-uri}")
    private var userInfoUri = "http://keycloak:8080/auth/realms/microframe/protocol/openid-connect/userinfo"
    @Value("\${spring.security.oauth2.client.provider.keycloak.userNameAttribute}")
    private var userNameAttributeName = "sub"
    @Value("\${spring.security.oauth2.client.provider.keycloak.jwk-set-uri}")
    private var jwkSetUri = "http://keycloak:8080/auth/realms/microframe/protocol/openid-connect/certs"
    @Value("\${spring.security.oauth2.client.registration.keycloak.client-name}")
    private var clientName = "keycloak"

    @Bean
    fun springSecurityFilterChain(
        http: ServerHttpSecurity,
        handler: ServerLogoutSuccessHandler?
    ): SecurityWebFilterChain? {
        http
            .csrf().disable()
            .authorizeExchange()
            .pathMatchers("/actuator/**", "/", "/logout.html")
            .permitAll()

            .and()
            .authorizeExchange()
            .pathMatchers(HttpMethod.POST)
            .permitAll()

            .and()
            .authorizeExchange()
            .pathMatchers(HttpMethod.PUT)
            .permitAll()

            .and()
            .authorizeExchange()
            .pathMatchers(HttpMethod.DELETE)
            .permitAll()

            .and()
            .authorizeExchange()
            .anyExchange()
//            .pathMatchers(HttpMethod.GET)
            .authenticated()
            .and()
            .oauth2Login()
            .clientRegistrationRepository(clientRegistrationRepository())
            .and()
            .logout()
            .logoutSuccessHandler(handler)
        return http.build()
    }

    @Bean
    fun keycloakLogoutSuccessHandler(repository: ReactiveClientRegistrationRepository?): ServerLogoutSuccessHandler? {
        val oidcLogoutSuccessHandler = OidcClientInitiatedServerLogoutSuccessHandler(repository)
        oidcLogoutSuccessHandler.setPostLogoutRedirectUri("{baseUrl}/logout.html")
        return oidcLogoutSuccessHandler
    }

    @Bean
    fun clientRegistrationRepository(): InMemoryReactiveClientRegistrationRepository {
        return InMemoryReactiveClientRegistrationRepository(keycloakClientRegistration())
    }

    private fun keycloakClientRegistration(): ClientRegistration? {
        return ClientRegistration.withRegistrationId(registrationId)
            .clientId(clientId)
            .clientSecret(clientSecret)
                //ClientAuthenticationMethod.CLIENT_SECRET_BASIC
            .clientAuthenticationMethod(ClientAuthenticationMethod(clientAuthenticationMethod))
                //AuthorizationGrantType.AUTHORIZATION_CODE
            .authorizationGrantType(AuthorizationGrantType(authorizationGrantType))
            .redirectUri(redirectUri)
            .scope(*scope)
            .authorizationUri(authorizationUri)
            .tokenUri(tokenUri)
            .userInfoUri(userInfoUri)
                //IdTokenClaimNames.SUB
            .userNameAttributeName(userNameAttributeName)
            .jwkSetUri(jwkSetUri)
            .clientName(clientName)
            .build()
    }

}