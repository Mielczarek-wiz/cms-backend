package put.poznan.section.type.dto

data class TypeDtoRequest(
    val type: String,
    val hidden: Boolean,
    val user: String
)
