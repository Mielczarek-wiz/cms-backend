package put.poznan.section.infobox

import com.fasterxml.jackson.databind.ObjectMapper
import lombok.RequiredArgsConstructor
import org.slf4j.LoggerFactory
import org.springframework.boot.json.GsonJsonParser
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import put.poznan.files.FileService
import put.poznan.page.dto.PageDtoRequest
import put.poznan.section.infobox.dto.InfoboxDtoRequest
import put.poznan.section.infobox.dto.InfoboxDtoResponse

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ["section/infobox"])

class InfoboxController (
    val infoboxService: InfoboxService,
    val fileService: FileService,
    val mapper: ObjectMapper
) {
    @GetMapping("secured")
    fun getAll(): List<InfoboxDtoResponse> = infoboxService.findAll()

    @PostMapping("secured")
    fun create(@RequestParam("icon") file: MultipartFile, @RequestParam("infobox") newInfobox: String): ResponseEntity<Map<String, String>> {
        if (fileService.upload(file) != null) {
            val infobox = mapper.readValue(newInfobox, InfoboxDtoRequest::class.java)
            return infoboxService.create(infobox)
        } else {
            val errorMassage = mapOf("message" to "Only images can be uploaded")
            return ResponseEntity(errorMassage, HttpStatus.BAD_REQUEST)
        }
    }

    @PutMapping("secured/{id}")
    fun modify(@PathVariable id: Long, @RequestParam("icon") file: MultipartFile, @RequestParam("infobox") updatedInfobox: String): ResponseEntity<Map<String, String>> {
        if (fileService.upload(file) != null) {
            val infobox = mapper.readValue(updatedInfobox, InfoboxDtoRequest::class.java)
            return infoboxService.modify(id, infobox)
        } else {
            val errorMassage = mapOf("message" to "Only images can be uploaded")
            return ResponseEntity(errorMassage, HttpStatus.BAD_REQUEST)
        }
   }


    @DeleteMapping("secured/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Map<String, String>> = infoboxService.delete(id)
}