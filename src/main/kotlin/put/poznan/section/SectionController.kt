package put.poznan.section

import com.fasterxml.jackson.databind.ObjectMapper
import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import put.poznan.files.FileService
import put.poznan.section.dto.SectionDtoRequest
import put.poznan.section.dto.SectionDtoResponse

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ["section"])
class SectionController (
    val sectionService: SectionService,
    val fileService: FileService,
    val mapper: ObjectMapper
) {
    @GetMapping("secured")
    fun getAll(): List<SectionDtoResponse> = sectionService.findAll()

    @PostMapping("secured")
    fun create(@RequestParam("image") file: MultipartFile, @RequestParam("section") newSection: String): ResponseEntity<Map<String, String>> {
        return if (fileService.upload(file, "resources/files/section")) {
            val section = mapper.readValue(newSection, SectionDtoRequest::class.java)
            sectionService.create(section)
        } else {
            val errorMassage = mapOf("message" to "Only images can be uploaded")
            ResponseEntity(errorMassage, HttpStatus.BAD_REQUEST)
        }
    }

    @PutMapping("secured/{id}")
    fun modify(@PathVariable id: Long, @RequestParam("image") file: MultipartFile, @RequestParam("section") newSection: String): ResponseEntity<Map<String, String>> {
        return if (fileService.upload(file, "resources/files/section")) {
            val section = mapper.readValue(newSection, SectionDtoRequest::class.java)
            sectionService.modify(id, section)
        } else {
            val errorMassage = mapOf("message" to "Only images can be uploaded")
            ResponseEntity(errorMassage, HttpStatus.BAD_REQUEST)
        }
    }

    @DeleteMapping("secured/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Map<String, String>> = sectionService.delete(id)
}