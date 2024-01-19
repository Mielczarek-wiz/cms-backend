package put.poznan.general.dto

data class GeneralDtoRequest(
    val key: String,
    val value: String,
    val description: String,
    val hidden: Boolean,
    val user: String
)
