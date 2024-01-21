package put.poznan.slider

import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import put.poznan.section.dto.SectionDtoRequest
import put.poznan.section.dto.SectionDtoResponse
import put.poznan.slider.dto.SliderDtoRequest
import put.poznan.slider.dto.SliderDtoResponse

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ["slider"])
class SliderController (
    val sliderService: SliderService
) {
    @GetMapping("secured")
    fun getAll(): List<SliderDtoResponse> = sliderService.findAll()

    @PostMapping("secured")
    fun create(@RequestBody newSlider: SliderDtoRequest): ResponseEntity<Map<String, String>> = sliderService.create(newSlider)

    @PutMapping("secured/{id}")
    fun modify(@PathVariable id: Long, @RequestBody updatedSlider: SliderDtoRequest): ResponseEntity<Map<String, String>> = sliderService.modify(id, updatedSlider)

    @DeleteMapping("secured/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Map<String, String>> = sliderService.delete(id)
}