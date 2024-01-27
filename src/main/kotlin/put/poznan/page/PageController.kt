package put.poznan.page

import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import put.poznan.page.dto.PageDtoRequest
import put.poznan.page.dto.PageDtoResponse
import put.poznan.page.dto.PageDtoResponseClientMenu
import put.poznan.page.dto.PageDtoResponseClientPage

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ["page"])
class PageController (
        val pageService: PageService
) {
    @GetMapping("exposed/{link}")
    fun getPage(@PathVariable link: String): PageDtoResponseClientPage = pageService.getPage(link)
    @GetMapping("exposed/menu")
    fun getMenu(): List<PageDtoResponseClientMenu> = pageService.getMenu()
    @GetMapping("secured")
    fun getAll(): List<PageDtoResponse> = pageService.findAll()

    @GetMapping("secured/parent-pages/{name}")
    fun getAvailableParentPages(@PathVariable name: String): List<PageDtoResponse> = pageService.findAvailableParentPages(name)

    @PostMapping("secured")
    fun create(@RequestBody newPage: PageDtoRequest): ResponseEntity<Map<String, String>> = pageService.create(newPage)

    @PutMapping("secured/{id}")
    fun modify(@PathVariable id: Long, @RequestBody updatedPage: PageDtoRequest): ResponseEntity<Map<String, String>> = pageService.modify(id, updatedPage)

    @DeleteMapping("secured/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Map<String, String>> = pageService.delete(id)
}