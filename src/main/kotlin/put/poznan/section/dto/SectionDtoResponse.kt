package put.poznan.section.dto

data class SectionDtoResponse(
    val id: Long,
    val title: String,
    val subtitle: String,
    val text: String,
    val imgref: String,
    val type: String,
    val infoboxes: List<String>,
    val user: String,
    val hidden: Boolean
)
