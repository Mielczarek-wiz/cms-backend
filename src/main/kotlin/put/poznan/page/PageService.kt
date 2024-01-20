package put.poznan.page

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import put.poznan.general.General
import put.poznan.general.dto.GeneralDtoRequest
import put.poznan.page.dto.PageDtoRequest
import put.poznan.page.dto.PageDtoResponse
import put.poznan.user.UserCMS
import put.poznan.user.UserCMSRepository

@Service
class PageService(
        val pageRepository: PageRepository,
        val userCMSRepository: UserCMSRepository
) {
    fun findAll(): List<PageDtoResponse> {
        val allPages = pageRepository.findAll()
        val responsePages = allPages.map { it.toResponse() }
        return responsePages
    }

    fun create(newPage: PageDtoRequest): ResponseEntity<Map<String, String>> {
        val page = pageRepository.findPageByName(newPage.name)
        val user = userCMSRepository.findUserCMSByEmail(newPage.user)
        val parentPage = page?.page!!
        return if(user != null) {
            pageRepository.save(newPage.toModel(user, parentPage))
            val responseBody = mapOf("message" to "Page created")
            ResponseEntity(responseBody, HttpStatus.OK)
        } else {
            val errorMassage = mapOf("massage" to "Cannot create page")
            ResponseEntity(errorMassage, HttpStatus.BAD_REQUEST)
        }
    }

    fun modify(id: Long, updatedPage: PageDtoRequest): ResponseEntity<Map<String, String>> {
        val page = pageRepository.findPageById(id)
        val user = userCMSRepository.findUserCMSByEmail(updatedPage.user)
        return if(page != null && user != null){
            val generalCopied = page.copy()
            pageRepository.save(generalCopied.toUpdatedModel(user, updatedPage))
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
        val page = this.page
        return PageDtoResponse(
                id = this.id,
                name = this.name,
                link = this.link,
                hidden = this.hidden,
                user = user,
                page = page
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
        page.page = updatedPage.page
        return page
    }
    private fun PageDtoRequest.toModel(user: UserCMS, parentPage: Page): Page {
        val page = Page(
                name = this.name,
                link = this.link,
                hidden = this.hidden
        )
        page.user = user
        page.page = parentPage
        return page
    }
}