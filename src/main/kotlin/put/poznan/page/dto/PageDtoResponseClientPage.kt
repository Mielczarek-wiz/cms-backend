package put.poznan.page.dto

import put.poznan.section.dto.SectionDtoResponseClient

data class PageDtoResponseClientPage(
    val id: Long,
    val name: String,
    val sections: List<SectionDtoResponseClient>
)
