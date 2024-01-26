package put.poznan.section.infobox.dto

data class InfoboxDtoRequest(
    val icon: String,
    val information: String,
    val subinformation: String,
    val hidden: Boolean,
    val user: String
)
