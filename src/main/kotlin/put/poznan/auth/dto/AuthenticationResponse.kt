package put.poznan.auth.dto

data class AuthenticationResponse(
    val name: String,
    val email: String,
    val role: String,
    val accessToken: String
)
