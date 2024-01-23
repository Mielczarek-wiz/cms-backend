package put.poznan.slider

import com.fasterxml.jackson.databind.ObjectMapper
import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import put.poznan.files.FileService
import put.poznan.section.dto.SectionDtoRequest
import put.poznan.section.dto.SectionDtoResponse
import put.poznan.slider.dto.SliderDtoRequest
import put.poznan.slider.dto.SliderDtoResponse

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ["slider"])
class SliderController (
    val sliderService: SliderService,
    val fileService: FileService,
    val mapper: ObjectMapper
) {
    @GetMapping("secured")
    fun getAll(): List<SliderDtoResponse> = sliderService.findAll()

    @PostMapping("secured")
    fun create(@RequestParam("photo") file: MultipartFile, @RequestParam("slider") newSlider: String): ResponseEntity<Map<String, String>> {
        fileService.upload(file)
        val slider = mapper.readValue(newSlider, SliderDtoRequest::class.java)
        return sliderService.create(slider)
    }

    @PutMapping("secured/{id}")
    fun modify(@PathVariable id: Long, @RequestParam("photo") file: MultipartFile, @RequestParam("slider") updatedSlider: String): ResponseEntity<Map<String, String>> {
        fileService.upload(file)
        val slider = mapper.readValue(updatedSlider, SliderDtoRequest::class.java)
        return sliderService.modify(id, slider)
    }

    @DeleteMapping("secured/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Map<String, String>> = sliderService.delete(id)
}