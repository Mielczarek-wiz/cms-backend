package put.poznan.section

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import put.poznan.section.dto.SectionDtoRequest
import put.poznan.section.dto.SectionDtoResponse
import put.poznan.section.infobox.Infobox
import put.poznan.section.infobox.InfoboxRepository
import put.poznan.section.type.Type
import put.poznan.section.type.TypeRepository
import put.poznan.user.UserCMS
import put.poznan.user.UserCMSRepository

@Service
class SectionService (
    val sectionRepository: SectionRepository,
    val userCMSRepository: UserCMSRepository,
    val infoboxRepository: InfoboxRepository,
    val typeRepository: TypeRepository
) {
    fun findAll(): List<SectionDtoResponse> {
        val allSections = sectionRepository.findAll()
        val responseSections = allSections.map { it.toResponse() }
        return responseSections
    }
    fun create(newSection: SectionDtoRequest): ResponseEntity<Map<String, String>> {
        val section = sectionRepository.findSectionByTitle(newSection.title)
        val user = userCMSRepository.findUserCMSByEmail(newSection.user)
        return if(user != null){
            sectionRepository.save(newSection.toModel(user))
            val responseBody = mapOf("message" to "Section created")
            ResponseEntity(responseBody, HttpStatus.OK)
        } else {
            val errorMessage = mapOf("message" to "Cannot create section")
            ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
        }
    }
    fun modify(id: Long, updatedSection: SectionDtoRequest): ResponseEntity<Map<String, String>> {
        val section = sectionRepository.findSectionById(id)
        val user = userCMSRepository.findUserCMSByEmail(updatedSection.user)
        return if(section != null && user != null){
            val sectionCopied = section.copy()
            sectionRepository.save(sectionCopied.toUpdatedModel(user, updatedSection))
            val responseBody = mapOf("message" to "Section updated")
            ResponseEntity(responseBody, HttpStatus.OK)
        } else {
            val errorMessage = mapOf("message" to "Cannot update section")
            ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
        }
    }
    fun delete(id: Long): ResponseEntity<Map<String, String>> {
        val section = sectionRepository.findSectionById(id)
        return if (section != null){
            sectionRepository.delete(section)
            val responseBody = mapOf("message" to "Section deleted")
            ResponseEntity(responseBody, HttpStatus.OK)
        }else {
            val errorMessage = mapOf("message" to "Cannot delete section with id: $id")
            ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
        }
    }
    private fun Section.toResponse(): SectionDtoResponse {
        val user = this.user?.name + " " + this.user?.surname
        return SectionDtoResponse(
            id= this.id,
            title = this.title,
            subtitle = this.subtitle,
            text = this.text,
            imgref = imgref,
            hidden = this.hidden,
            infoboxes = this.infoboxes.map { it.information },
            user = user,
            type = this.type.type
        )
    }
    private fun Section.toUpdatedModel(user: UserCMS, updatedSection: SectionDtoRequest): Section {
        val infoboxes = updatedSection.infoboxes.map { it.toInfoboxes() }
        val section = Section(
            id = this.id,
            title = updatedSection.title,
            subtitle = updatedSection.subtitle,
            text = updatedSection.text,
            imgref = updatedSection.image,
            hidden = updatedSection.hidden,
        )
        section.user = user
        section.type = typeRepository.findTypeByType(updatedSection.type)!!
        section.infoboxes = infoboxes
        return section
    }

    private fun SectionDtoRequest.toModel(user: UserCMS): Section {
        val infoboxes = this.infoboxes.map { it.toInfoboxes() }
        val section = Section(
            title = this.title,
            subtitle = this.subtitle,
            text = this.text,
            imgref = this.image,
            hidden = this.hidden,
        )
        section.user = user
        section.type = typeRepository.findTypeByType(this.type)!!
        section.infoboxes = infoboxes
        return section
    }

    private fun String.toInfoboxes(): Infobox {
        val infobox = infoboxRepository.findInfoboxByInformation(this)
        if (infobox != null) {
            return infobox
        } else {
            return Infobox()
        }
    }
}