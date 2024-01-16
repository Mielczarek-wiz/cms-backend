package put.poznan.user.role.dto

data class RoleRequest (
    val name: String,
    val hidden: Boolean,
    val user: String
)