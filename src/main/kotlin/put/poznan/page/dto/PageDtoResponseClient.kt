package put.poznan.page.dto

data class PageDtoResponseClient(
    val id: Long,
    val name: String,
    val link: String,
    val hidden: Boolean,
    val subpages: List<PageDtoResponseClient> = listOf()
)
