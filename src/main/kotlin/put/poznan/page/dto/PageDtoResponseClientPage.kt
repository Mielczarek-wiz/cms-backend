package put.poznan.page.dto

import put.poznan.section.dto.SectionDtoResponseClient

data class PageDtoResponseClientPage(
    val id: Long = -1,
    val name: String = "",
    val sections: List<SectionDtoResponseClient> = listOf()
)
