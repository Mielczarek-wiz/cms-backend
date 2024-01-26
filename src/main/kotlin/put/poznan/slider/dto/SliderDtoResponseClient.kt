package put.poznan.slider.dto

data class SliderDtoResponseClient(
    val id: Long,
    val title: String,
    val text: String,
    val image: String,
    val hidden: Boolean
)
