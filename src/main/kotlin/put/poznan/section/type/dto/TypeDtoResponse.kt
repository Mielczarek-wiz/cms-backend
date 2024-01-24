package put.poznan.section.type.dto

data class TypeDtoResponse(
    val id: Long,
    val type: String,
    val hidden: Boolean,
    val user: String
)
