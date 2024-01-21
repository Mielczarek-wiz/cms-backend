package put.poznan.section.type

import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import put.poznan.section.infobox.dto.InfoboxDtoRequest
import put.poznan.section.infobox.dto.InfoboxDtoResponse
import put.poznan.section.type.dto.TypeDtoRequest
import put.poznan.section.type.dto.TypeDtoResponse

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ["section/type"])
class TypeController (
    val typeService: TypeService
) {
    @GetMapping("secured")
    fun getAll(): List<TypeDtoResponse> = typeService.findAll()

    @PostMapping("secured")
    fun create(@RequestBody newType: TypeDtoRequest): ResponseEntity<Map<String, String>> = typeService.create(newType)

    @PutMapping("secured/{id}")
    fun modify(@PathVariable id: Long, @RequestBody updateType: TypeDtoRequest): ResponseEntity<Map<String, String>> = typeService.modify(id, updateType)

    @DeleteMapping("secured/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Map<String, String>> = typeService.delete(id)
}