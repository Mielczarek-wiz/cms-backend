package put.poznan.section.infobox.dto

data class InfoboxDtoResponseClient(
    val id: Long,
    val imgref: String,
    val information: String,
    val subinformation: String,
    val hidden: Boolean
)
