package put.poznan.auth

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service
import put.poznan.auth.dto.AuthenticationRequest
import put.poznan.auth.dto.AuthenticationResponse
import put.poznan.config.JwtProperties
import put.poznan.token.TokenService
import put.poznan.user.CustomUserDetailsService
import java.util.*

@Service
class AuthenticationService(
    private val authManager: AuthenticationManager,
    private val userDetailsService: CustomUserDetailsService,
    private val tokenService: TokenService,
    private val jwtProperties: JwtProperties
) {
    fun authentication(authRequest: AuthenticationRequest): AuthenticationResponse {
        authManager.authenticate(
            UsernamePasswordAuthenticationToken(authRequest.email, authRequest.password)
        )

        val user = userDetailsService.loadUserByUsername(authRequest.email)
        val accessToken = tokenService.generate(userDetails = user, expirationDate = Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration))

        return AuthenticationResponse(
            accessToken = accessToken
        )
    }

}