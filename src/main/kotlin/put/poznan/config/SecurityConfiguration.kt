package put.poznan.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val authenticationProvider: AuthenticationProvider
) {
    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        jwtAuthenticationFilter: JwtAuthenticationFilter
    ): DefaultSecurityFilterChain =
        http.csrf{ it.disable()}
            .authorizeHttpRequests{
                it
                    .requestMatchers("/auth", "/auth/refresh", "/error")
                    .permitAll()
                    .requestMatchers("/general/exposed/**", "/page/exposed/**", "/section/exposed/**",
                        "/slider/exposed/**", "/section/type/exposed/**", "/section/infobox/exposed/**")
                    .permitAll()
                    .requestMatchers("/user/**")
                    .hasRole("Admin")
                    .requestMatchers("/general/secured/**", "/page/secured/**", "/section/secured/**", "/slider/secured/**",
                        "/section/type/secured/**", "/section/infobox/secured/**")
                    .hasAnyRole("Moderator", "Admin")
                    .anyRequest()
                    .fullyAuthenticated()
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
}