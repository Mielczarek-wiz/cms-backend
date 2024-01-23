package put.poznan.slider

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import put.poznan.slider.dto.SliderDtoRequest
import put.poznan.slider.dto.SliderDtoResponse
import put.poznan.user.UserCMS
import put.poznan.user.UserCMSRepository

@Service
class SliderService (
    val sliderRepository: SliderRepository,
    val userCMSRepository: UserCMSRepository
) {
    fun findAll(): List<SliderDtoResponse> {
        val allSliders = sliderRepository.findAll()
        val responseSliders = allSliders.map { it.toResponse() }
        return responseSliders
    }
    private fun Slider.toResponse(): SliderDtoResponse {
        val user = this.user?.name + " " + this.user?.surname
        return SliderDtoResponse(
            id= this.id,
            title = this.title,
            text = this.text,
            imgref = this.imgref,
            hidden = this.hidden,
            user = user
        )
    }
    fun create(newSlider: SliderDtoRequest): ResponseEntity<Map<String, String>> {
        val section = sliderRepository.findSliderByTitle(newSlider.title)
        val user = userCMSRepository.findUserCMSByEmail(newSlider.user)
        return if(section == null && user != null){
            sliderRepository.save(newSlider.toModel(user))
            val responseBody = mapOf("message" to "Slider created")
            ResponseEntity(responseBody, HttpStatus.OK)
        } else {
            val errorMessage = mapOf("message" to "Cannot create slider")
            ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
        }
    }
    fun modify(id: Long, updatedSlider: SliderDtoRequest): ResponseEntity<Map<String, String>> {
        val slider = sliderRepository.findSliderById(id)
        val user = userCMSRepository.findUserCMSByEmail(updatedSlider.user)
        return if(slider != null && user != null){
            val sectionCopied = slider.copy()
            sliderRepository.save(sectionCopied.toUpdatedModel(user, updatedSlider))
            val responseBody = mapOf("message" to "Slider updated")
            ResponseEntity(responseBody, HttpStatus.OK)
        } else {
            val errorMessage = mapOf("message" to "Cannot update slider")
            ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
        }
    }
    fun delete(id: Long): ResponseEntity<Map<String, String>> {
        val slider = sliderRepository.findSliderById(id)
        return if (slider != null){
            sliderRepository.delete(slider)
            val responseBody = mapOf("message" to "Slider deleted")
            ResponseEntity(responseBody, HttpStatus.OK)
        }else {
            val errorMessage = mapOf("message" to "Cannot delete slider with id: $id")
            ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
        }
    }
    private fun Slider.toUpdatedModel(user: UserCMS, updatedSlider: SliderDtoRequest): Slider {
        val slider = Slider(
            id = this.id,
            title = updatedSlider.title,
            text = updatedSlider.text,
            imgref = updatedSlider.photo,
            hidden = updatedSlider.hidden,
        )
        slider.user = user
        return slider
    }
    private fun SliderDtoRequest.toModel(user: UserCMS): Slider {
        val slider = Slider(
            title = this.title,
            text = this.text,
            imgref = this.photo,
            hidden = this.hidden,
        )
        slider.user = user
        return slider
    }
}