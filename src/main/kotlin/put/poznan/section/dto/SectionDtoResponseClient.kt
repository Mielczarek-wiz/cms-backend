package put.poznan.section.dto

import put.poznan.section.infobox.dto.InfoboxDtoResponseClient

data class SectionDtoResponseClient(
    val id: Long,
    val title: String,
    val subtitle: String,
    val text: String,
    val image: String,
    val type: String,
    val infoboxes: List<InfoboxDtoResponseClient>,
    val hidden: Boolean
)
