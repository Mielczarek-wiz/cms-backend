package put.poznan.page.dto

import put.poznan.section.Section

data class PageDtoResponseClientPage(
    val id: Long,
    val name: String,
    val sections: List<Section>
)
