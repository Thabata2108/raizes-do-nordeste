package br.com.raizesdonordeste.config;

import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.source.ImmutableSecret;

@Configuration
public class SecurityConfig {

    @Value("${JWT_SECRET}")
    private String chaveJwt;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        SecretKey chave = new SecretKeySpec(
                chaveJwt.getBytes(StandardCharsets.UTF_8),
                "HmacSHA256");

        return new NimbusJwtEncoder(
                new ImmutableSecret<>(chave));
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        SecretKey chave = new SecretKeySpec(
                chaveJwt.getBytes(StandardCharsets.UTF_8),
                "HmacSHA256");

        return NimbusJwtDecoder
                .withSecretKey(chave)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {

        JwtAuthenticationConverter converter =
                new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            String perfil = jwt.getClaimAsString("perfil");

            return List.of(
                    new SimpleGrantedAuthority(
                            "ROLE_" + perfil));
        });

        return converter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthenticationConverter converter) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth

                // Swagger / OpenAPI
                .requestMatchers(
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/v3/api-docs/**"
                ).permitAll()

                // Login e cadastro
                .requestMatchers("/login").permitAll()

                .requestMatchers(
                    HttpMethod.POST,
                    "/usuarios"
                ).permitAll()

                // Pontos
                .requestMatchers(
                    HttpMethod.GET,
                    "/usuarios/*/pontos"
                ).authenticated()

                // Usuários
                .requestMatchers("/usuarios/**")
                    .hasRole("ADMINISTRADOR")

                // Relatórios
                .requestMatchers("/relatorios/**")
                    .hasRole("ADMINISTRADOR")

                // Produto por unidade / estoque
                .requestMatchers("/produtos-unidade/**")
                    .hasAnyRole(
                        "FUNCIONARIO",
                        "ADMINISTRADOR"
                    )

                // Unidades
                .requestMatchers(
                    HttpMethod.GET,
                    "/unidades/**"
                ).authenticated()

                .requestMatchers("/unidades/**")
                    .hasRole("ADMINISTRADOR")

                // Produtos
                .requestMatchers(
                    HttpMethod.GET,
                    "/produtos/**"
                ).authenticated()

                .requestMatchers("/produtos/**")
                    .hasRole("ADMINISTRADOR")

                // Pedidos
                .requestMatchers(
                    HttpMethod.POST,
                    "/pedidos"
                ).authenticated()

                .requestMatchers(
                    HttpMethod.GET,
                    "/pedidos"
                ).hasAnyRole(
                    "FUNCIONARIO",
                    "ADMINISTRADOR"
                )

                .requestMatchers(
                    HttpMethod.GET,
                    "/pedidos/*"
                ).authenticated()

                .requestMatchers(
                    HttpMethod.PATCH,
                    "/pedidos/*/status"
                ).hasAnyRole(
                    "FUNCIONARIO",
                    "ADMINISTRADOR"
                )

                // Pagamentos
                .requestMatchers(
                    HttpMethod.POST,
                    "/pagamentos"
                ).authenticated()

                .requestMatchers(
                    HttpMethod.GET,
                    "/pagamentos/**"
                ).hasAnyRole(
                    "FUNCIONARIO",
                    "ADMINISTRADOR"
                )

                .anyRequest().authenticated()
            )

            .oauth2ResourceServer(oauth -> oauth
                .jwt(jwt ->
                    jwt.jwtAuthenticationConverter(converter))
            );

        return http.build();
    }
}