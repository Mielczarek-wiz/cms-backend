package put.poznan.section.infobox.dto

import put.poznan.section.Section
import put.poznan.user.UserCMS

data class InfoboxDtoResponse(
    val id: Long,
    val imgref: String,
    val information: String,
    val subinformation: String,
    val hidden: Boolean,
    val user: String,
    val section: Section
)
