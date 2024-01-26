package put.poznan.section.infobox.dto

data class InfoboxDtoResponse(
    val id: Long,
    val information: String,
    val subinformation: String,
    val imgref: String,
    val user: String,
    val hidden: Boolean,
)
