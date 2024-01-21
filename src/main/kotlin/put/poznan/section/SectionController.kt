package put.poznan.section

import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import put.poznan.section.dto.SectionDtoRequest
import put.poznan.section.dto.SectionDtoResponse

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ["section"])
class SectionController (
    val sectionService: SectionService
) {
    @GetMapping("secured")
    fun getAll(): List<SectionDtoResponse> = sectionService.findAll()

    @PostMapping("secured")
    fun create(@RequestBody newSection: SectionDtoRequest): ResponseEntity<Map<String, String>> = sectionService.create(newSection)

    @PutMapping("secured/{id}")
    fun modify(@PathVariable id: Long, @RequestBody updateSection: SectionDtoRequest): ResponseEntity<Map<String, String>> = sectionService.modify(id, updateSection)

    @DeleteMapping("secured/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Map<String, String>> = sectionService.delete(id)
}