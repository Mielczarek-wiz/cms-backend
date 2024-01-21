package put.poznan.slider.dto

data class SliderDtoResponse(
    val id: Long,
    val title: String,
    val text: String,
    val imgref: String,
    val hidden: Boolean,
    val user: String
)
