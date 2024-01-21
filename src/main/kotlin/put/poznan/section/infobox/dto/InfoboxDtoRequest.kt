package put.poznan.section.infobox.dto

import put.poznan.section.Section

data class InfoboxDtoRequest(
    val imgref: String,
    val information: String,
    val subinformation: String,
    val hidden: Boolean,
    val user: String,
    val section: Section
)
