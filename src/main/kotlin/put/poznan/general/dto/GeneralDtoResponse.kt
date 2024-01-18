package put.poznan.general.dto

data class GeneralDtoResponse(
    val id: Long,
    val key: String,
    val value: String,
    val description: String,
    val user: String,
    val hidden: Boolean

)
