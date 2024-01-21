package put.poznan.section.infobox

import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import put.poznan.page.dto.PageDtoRequest
import put.poznan.section.infobox.dto.InfoboxDtoRequest
import put.poznan.section.infobox.dto.InfoboxDtoResponse

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ["section/infobox"])
class InfoboxController (
    val infoboxService: InfoboxService
) {
    @GetMapping("secured")
    fun getAll(): List<InfoboxDtoResponse> = infoboxService.findAll()

    @PostMapping("secured")
    fun create(@RequestBody newInfobox: InfoboxDtoRequest): ResponseEntity<Map<String, String>> = infoboxService.create(newInfobox)

    @PutMapping("secured/{id}")
    fun modify(@PathVariable id: Long, @RequestBody updatedInfobox: InfoboxDtoRequest): ResponseEntity<Map<String, String>> = infoboxService.modify(id, updatedInfobox)

    @DeleteMapping("secured/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Map<String, String>> = infoboxService.delete(id)
}