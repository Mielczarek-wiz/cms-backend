package put.poznan.user.projection

import lombok.NoArgsConstructor

@NoArgsConstructor
class UserDto(
    val id: Long,
    val name: String,
    val surname: String,
    val email: String
)