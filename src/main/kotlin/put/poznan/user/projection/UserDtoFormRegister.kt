package put.poznan.user.projection
data class UserDtoFormRegister(
    val name: String,
    val surname: String,
    val email: String,
    val password: String,
    val role: String
)
