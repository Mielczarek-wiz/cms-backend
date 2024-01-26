package put.poznan.page

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import put.poznan.page.dto.PageDtoRequest
import put.poznan.page.dto.PageDtoResponse
import put.poznan.section.Section
import put.poznan.section.SectionRepository
import put.poznan.user.UserCMS
import put.poznan.user.UserCMSRepository

@Service
class PageService(
        val pageRepository: PageRepository,
        val userCMSRepository: UserCMSRepository,
        val sectionRepository: SectionRepository
) {
    fun findAll(): List<PageDtoResponse> {
        val allPages = pageRepository.findAll()
        val responsePages = allPages.map { it.toResponse() }
        return responsePages
    }

    fun findAvailableParentPages(name: String): List<PageDtoResponse> {
        val allPages = pageRepository.findAll()
        allPages.removeIf{ name == it.name || it.page?.page != null }
        val responsePages = allPages.map { it.toResponse() }
        return responsePages
    }

    fun create(newPage: PageDtoRequest): ResponseEntity<Map<String, String>> {
        val page = pageRepository.findPageByName(newPage.name)
        val user = userCMSRepository.findUserCMSByEmail(newPage.user)
        return if(page == null && user != null) {
            pageRepository.save(newPage.toModel(user))
            val responseBody = mapOf("message" to "Page created")
            ResponseEntity(responseBody, HttpStatus.OK)
        } else {
            val errorMassage = mapOf("message" to "Cannot create page")
            ResponseEntity(errorMassage, HttpStatus.BAD_REQUEST)
        }
    }

    fun modify(id: Long, updatedPage: PageDtoRequest): ResponseEntity<Map<String, String>> {
        val page = pageRepository.findPageById(id)
        val user = userCMSRepository.findUserCMSByEmail(updatedPage.user)
        return if(page != null && user != null){
            val pageCopied = page.copy()
            pageRepository.save(pageCopied.toUpdatedModel(user, updatedPage))
            val responseBody = mapOf("message" to "Page updated")
            ResponseEntity(responseBody, HttpStatus.OK)
        } else {
            val errorMessage = mapOf("message" to "Cannot update page")
            ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
        }
    }

    fun delete(id: Long): ResponseEntity<Map<String, String>> {
        val page = pageRepository.findPageById(id)
        return if (page != null){
            pageRepository.delete(page)
            val responseBody = mapOf("message" to "Page deleted")
            ResponseEntity(responseBody, HttpStatus.OK)
        } else {
            val errorMessage = mapOf("message" to "Cannot delete page with id: $id")
            ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
        }
    }

    private fun Page.toResponse(): PageDtoResponse {
        val user = this.user?.name + " " + this.user?.surname
        val parentPage = ""
        return PageDtoResponse(
                id = this.id,
                name = this.name,
                link = this.link,
                hidden = this.hidden,
                user = user,
                parentPage = parentPage,
                sections = this.sections.map { it.title }
        )
    }
    private fun Page.toUpdatedModel(user: UserCMS, updatedPage: PageDtoRequest): Page {
        val page = Page(
                id = this.id,
                name = updatedPage.name,
                link = updatedPage.link,
                hidden = updatedPage.hidden
        )
        page.user = user
        if(updatedPage.parentPage == null || updatedPage.parentPage == ""){
            page.page = null
        }
        else {
            page.page = pageRepository.findPageByName(updatedPage.parentPage)
        }
        page.sections = updatedPage.sections.map { it.toSections() }
        return page
    }
    private fun PageDtoRequest.toModel(user: UserCMS): Page {
        val page = Page(
                name = this.name,
                link = this.link,
                hidden = this.hidden
        )
        page.user = user
        if(this.parentPage == null || this.parentPage == ""){
            page.page = null
        }
        else {
            page.page = pageRepository.findPageByName(this.parentPage)
        }
        page.sections = this.sections.map { it.toSections() }
        return page
    }

    private fun String.toSections(): Section {
        return sectionRepository.findSectionByTitle(this) ?: Section()
    }
}