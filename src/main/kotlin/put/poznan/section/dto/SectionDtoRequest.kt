package put.poznan.section.dto

data class SectionDtoRequest(
    val title: String,
    val subtitle: String,
    val text: String,
    val image: String,
    val infoboxes: List<String>,
    val hidden: Boolean,
    val user: String,
    val type: String
)
