package put.poznan.slider.dto

data class SliderDtoRequest(
    val title: String,
    val text: String,
    val photo: String,
    val hidden: Boolean,
    val user: String
)
