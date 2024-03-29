package put.poznan.page

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import put.poznan.files.FileService
import put.poznan.page.dto.PageDtoRequest
import put.poznan.page.dto.PageDtoResponse
import put.poznan.page.dto.PageDtoResponseClientMenu
import put.poznan.page.dto.PageDtoResponseClientPage
import put.poznan.section.Section
import put.poznan.section.SectionRepository
import put.poznan.section.dto.SectionDtoResponseClient
import put.poznan.section.infobox.Infobox
import put.poznan.section.infobox.dto.InfoboxDtoResponseClient
import put.poznan.user.UserCMS
import put.poznan.user.UserCMSRepository

@Service
class PageService(
    private val pageRepository: PageRepository,
    private val userCMSRepository: UserCMSRepository,
    private val sectionRepository: SectionRepository,
    private val fileService: FileService
) {

    fun getPage(link: String): PageDtoResponseClientPage {
        val page = pageRepository.findPageByLinkAndHiddenIsFalse(link)
        return page?.toResponseClientPage() ?: PageDtoResponseClientPage()

    }
    fun getMenu(): List<PageDtoResponseClientMenu> {
        val allPages = pageRepository.findPagesByPageIsNullAndHiddenIsFalse()
        val responsePages = allPages.map { it.toResponseClientMenu() }
        return responsePages

    }
    fun findAll(): List<PageDtoResponse> {
        val allPages = pageRepository.findAll()
        val responsePages = allPages.map { it.toResponse() }
        return responsePages
    }

    fun findAvailableParentPages(name: String): List<PageDtoResponse> {
        val allPages = pageRepository.findPagesByHiddenIsFalse().toMutableList()
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
        return PageDtoResponse(
                id = this.id,
                name = this.name,
                link = this.link,
                hidden = this.hidden,
                user = user,
                parentPage = this.page?.name ?: "",
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
        page.page = setParentPage(updatedPage.parentPage)
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
        page.page = setParentPage(this.parentPage)
        page.sections = this.sections.map { it.toSections() }
        return page
    }

    private fun String.toSections(): Section {
        val section = sectionRepository.findSectionByTitle(this)
        if (section != null) {
            return section
        } else {
            return Section()
        }
    }


    private fun Page.toResponseClientMenu(): PageDtoResponseClientMenu {
        val subpages = pageRepository.findPagesByPageIdAndHiddenIsFalse(this.id)
        return PageDtoResponseClientMenu(
                id = this.id,
                name = this.name,
                link = this.link,
                hidden = this.hidden,
                subpages = subpages.map { it.toResponseClientMenu() }
        )
    }

    private fun Page.toResponseClientPage(): PageDtoResponseClientPage {
        val sections = this.sections.toMutableList()
        sections.removeIf { it.hidden}

        return PageDtoResponseClientPage(
                id = this.id,
                name = this.name,
                sections = sections.map { it.toResponseSectionClient() }
        )
    }

    private fun Section.toResponseSectionClient(): SectionDtoResponseClient {
        var image = ""
        if (this.imgref != "") {
            image = fileService.download("resources/files/section/" + this.imgref)
        }
        val infoboxes = this.infoboxes.toMutableList()
        infoboxes.removeIf { it.hidden }

        return SectionDtoResponseClient(
                id = this.id,
                title = this.title,
                subtitle = this.subtitle,
                text = this.text,
                image = image,
                hidden = this.hidden,
                infoboxes = infoboxes.map { it.toResponseInfoboxClient() },
                type = this.type.type
        )
    }
    private fun Infobox.toResponseInfoboxClient(): InfoboxDtoResponseClient {
        val image = fileService.download("resources/files/infobox/" + this.imgref)
        return InfoboxDtoResponseClient(
                id = this.id,
                image = image,
                information = this.information,
                subinformation = this.subinformation,
                hidden = this.hidden,

        )
    }
    private fun setParentPage(page: String?): Page? {
        if(page == null || page == ""){
            return null
        }
        else {
            return pageRepository.findPageByName(page)
        }
    }


}