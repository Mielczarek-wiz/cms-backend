package put.poznan.slider

import com.fasterxml.jackson.databind.ObjectMapper
import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import put.poznan.files.FileService
import put.poznan.slider.dto.SliderDtoRequest
import put.poznan.slider.dto.SliderDtoResponseClient
import put.poznan.slider.dto.SliderDtoResponse

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ["slider"])
class SliderController (
    val sliderService: SliderService,
    val fileService: FileService,
    val mapper: ObjectMapper
) {
    @GetMapping("exposed")
    fun getAllClient(): List<SliderDtoResponseClient> = sliderService.findAllClient()

    @GetMapping("secured")
    fun getAll(): List<SliderDtoResponse> = sliderService.findAll()

    @PostMapping("secured")
    fun create(@RequestParam("photo") file: MultipartFile, @RequestParam("slider") newSlider: String): ResponseEntity<Map<String, String>> {
        if (fileService.upload(file, "resources/files/slider")) {
            val slider = mapper.readValue(newSlider, SliderDtoRequest::class.java)
            return sliderService.create(slider)
        } else {
            val errorMassage = mapOf("message" to "Only images can be uploaded")
            return ResponseEntity(errorMassage, HttpStatus.BAD_REQUEST)
        }
    }

    @PutMapping("secured/{id}")
    fun modify(@PathVariable id: Long, @RequestParam("photo") file: MultipartFile, @RequestParam("slider") updatedSlider: String): ResponseEntity<Map<String, String>> {
        if (fileService.upload(file, "resources/files/slider")) {
            val slider = mapper.readValue(updatedSlider, SliderDtoRequest::class.java)
            return sliderService.modify(id, slider)
        } else {
            val errorMassage = mapOf("message" to "Only images can be uploaded")
            return ResponseEntity(errorMassage, HttpStatus.BAD_REQUEST)
        }
    }

    @DeleteMapping("secured/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Map<String, String>> = sliderService.delete(id)
}