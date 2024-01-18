package put.poznan.user.dto

import lombok.NoArgsConstructor

@NoArgsConstructor
class UserDtoResponse(
    val id: Long,
    val name: String,
    val surname: String,
    val email: String,
    val role: String,
)