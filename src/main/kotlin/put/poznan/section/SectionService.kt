package put.poznan.section

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import put.poznan.section.dto.SectionDtoRequest
import put.poznan.section.dto.SectionDtoResponse
import put.poznan.section.type.Type
import put.poznan.user.UserCMS
import put.poznan.user.UserCMSRepository

@Service
class SectionService (
    val sectionRepository: SectionRepository,
    val userCMSRepository: UserCMSRepository
) {
    fun findAll(): List<SectionDtoResponse> {
        val allSections = sectionRepository.findAll()
        val responseSections = allSections.map { it.toResponse() }
        return responseSections
    }
    fun create(newSection: SectionDtoRequest): ResponseEntity<Map<String, String>> {
        val section = sectionRepository.findSectionByTitle(newSection.title)
        val user = userCMSRepository.findUserCMSByEmail(newSection.user)
        val type = section?.type!!
        return if(user != null){
            sectionRepository.save(newSection.toModel(user, type))
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
        val type = section?.type
        return if(section != null && user != null){
            val sectionCopied = section.copy()
            sectionRepository.save(sectionCopied.toUpdatedModel(user, type!!, updatedSection))
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
        val type = this.type.type
        return SectionDtoResponse(
            id= this.id,
            title = this.title,
            subtitle = this.subtitle,
            text = this.text,
            imgref = this.imgref,
            hidden = this.hidden,
            user = user,
            type = type
        )
    }
    private fun Section.toUpdatedModel(user: UserCMS, type: Type, updatedSection: SectionDtoRequest): Section {
        val section = Section(
            id = this.id,
            title = updatedSection.title,
            subtitle = updatedSection.subtitle,
            text = updatedSection.text,
            imgref = updatedSection.imgref,
            hidden = updatedSection.hidden,
        )
        section.user = user
        section.type = type
        return section
    }

    private fun SectionDtoRequest.toModel(user: UserCMS, type: Type): Section {
        val section = Section(
            title = this.title,
            subtitle = this.subtitle,
            text = this.text,
            imgref = this.imgref,
            hidden = this.hidden,
        )
        section.user = user
        section.type = type
        return section
    }
}