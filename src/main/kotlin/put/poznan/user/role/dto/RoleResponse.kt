package put.poznan.user.role.dto

data class RoleResponse(
    val id: Long,
    val name: String,
    val user: String,
    val hidden: Boolean,
)