package put.poznan.auth.dto

data class AuthenticationRequest(
    val email: String,
    val password: String
)