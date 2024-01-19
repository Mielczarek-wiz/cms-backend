package put.poznan.general

import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import put.poznan.general.dto.GeneralDtoRequest
import put.poznan.general.dto.GeneralDtoResponse

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ["general"])
class GeneralController(
    val generalService: GeneralService
) {

    @GetMapping("secured")
    fun getAll(): List<GeneralDtoResponse> = generalService.findAll()

    @PostMapping("secured")
    fun create(@RequestBody newGeneral: GeneralDtoRequest): ResponseEntity<Map<String, String>> = generalService.create(newGeneral)

    @PutMapping("secured/{id}")
    fun modify(@PathVariable id: Long, @RequestBody updatedGeneral: GeneralDtoRequest): ResponseEntity<Map<String, String>> = generalService.modify(id, updatedGeneral)

    @DeleteMapping("secured/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Map<String, String>> = generalService.delete(id)

}