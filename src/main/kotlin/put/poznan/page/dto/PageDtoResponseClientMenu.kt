package put.poznan.page.dto

data class PageDtoResponseClientMenu(
    val id: Long,
    val name: String,
    val link: String,
    val hidden: Boolean,
    val subpages: List<PageDtoResponseClientMenu> = listOf()
)
