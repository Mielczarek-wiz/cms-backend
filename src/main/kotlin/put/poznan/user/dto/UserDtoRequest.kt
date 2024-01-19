package put.poznan.user.dto
data class UserDtoRequest(
    val name: String,
    val surname: String,
    val email: String,
    val password: String,
    val role: String
)
