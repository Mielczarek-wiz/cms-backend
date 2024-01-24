package put.poznan.section.infobox.dto

import org.springframework.core.io.Resource
import put.poznan.section.Section
import put.poznan.user.UserCMS

data class InfoboxDtoResponse(
    val id: Long,
    val information: String,
    val subinformation: String,
    val imgref: String,
    val user: String,
    val hidden: Boolean,
)
