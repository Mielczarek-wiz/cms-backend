package put.poznan.page

import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import put.poznan.general.dto.GeneralDtoRequest
import put.poznan.page.dto.PageDtoRequest
import put.poznan.page.dto.PageDtoResponse

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ["page"])
class PageController (
        val pageService: PageService
) {
    @GetMapping("secured")
    fun getAll(): List<PageDtoResponse> = pageService.findAll()

    @PostMapping("secured")
    fun create(@RequestBody newPage: PageDtoRequest): ResponseEntity<Map<String, String>> = pageService.create(newPage)

    @PutMapping("secured/{id}")
    fun modify(@PathVariable id: Long, @RequestBody updatedPage: PageDtoRequest): ResponseEntity<Map<String, String>> = pageService.modify(id, updatedPage)

    @DeleteMapping("secured/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Map<String, String>> = pageService.delete(id)
}