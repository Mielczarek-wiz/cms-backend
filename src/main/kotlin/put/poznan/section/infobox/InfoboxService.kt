package put.poznan.section.infobox

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import put.poznan.section.Section
import put.poznan.section.infobox.dto.InfoboxDtoRequest
import put.poznan.section.infobox.dto.InfoboxDtoResponse
import put.poznan.user.UserCMS
import put.poznan.user.UserCMSRepository

@Service
class InfoboxService (
    val infoboxRepository: InfoboxRepository,
    val userCMSRepository: UserCMSRepository
) {
    fun findAll(): List<InfoboxDtoResponse> {
        val allInfoboxes = infoboxRepository.findAll()
        val responseInfoboxes = allInfoboxes.map { it.toResponse() }
        return responseInfoboxes
    }

    fun create(newInfobox: InfoboxDtoRequest): ResponseEntity<Map<String, String>> {
        val user = userCMSRepository.findUserCMSByEmail(newInfobox.user)
        val parentSection = newInfobox.section
        return if (user !== null) {
            infoboxRepository.save(newInfobox.toModel(user, parentSection))
            val responseBody = mapOf("message" to "Infobox created")
            ResponseEntity(responseBody, HttpStatus.OK)
        } else {
            val errorMassage = mapOf("massage" to "Cannot create infobox")
            ResponseEntity(errorMassage, HttpStatus.BAD_REQUEST)
        }
    }

    fun modify(id: Long, updatedInfobox: InfoboxDtoRequest): ResponseEntity<Map<String, String>> {
        val infobox = infoboxRepository.findInfoboxById(id)
        val user = userCMSRepository.findUserCMSByEmail(updatedInfobox.user)
        return if(infobox != null && user != null){
            val infoboxCopied = infobox.copy()
            infoboxRepository.save(infoboxCopied.toUpdatedModel(user, updatedInfobox))
            val responseBody = mapOf("message" to "Infobox updated")
            ResponseEntity(responseBody, HttpStatus.OK)
        } else {
            val errorMessage = mapOf("message" to "Cannot update page")
            ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
        }
    }

    fun delete(id: Long): ResponseEntity<Map<String, String>> {
        val infobox = infoboxRepository.findInfoboxById(id)
        return if (infobox != null){
            infoboxRepository.delete(infobox)
            val responseBody = mapOf("message" to "Infobox deleted")
            ResponseEntity(responseBody, HttpStatus.OK)
        } else {
            val errorMessage = mapOf("message" to "Cannot delete infobox with id: $id")
            ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
        }
    }

    private fun Infobox.toResponse(): InfoboxDtoResponse {
        val user = this.user?.name + " " + this.user?.surname
        return InfoboxDtoResponse(
            id = this.id,
            imgref = this.imgref,
            information = this.information,
            subinformation = this.subinformation,
            hidden = this.hidden,
            user = user,
            section = this.section
        )
    }

    private fun Infobox.toUpdatedModel(user: UserCMS, updatedInfobox: InfoboxDtoRequest): Infobox {
        val infobox = Infobox(
            id = this.id,
            imgref = this.imgref,
            information = this.information,
            subinformation = this.subinformation,
            hidden = this.hidden
        )
        infobox.user = user
        infobox.section = updatedInfobox.section
        return infobox
    }

    private fun InfoboxDtoRequest.toModel(user: UserCMS, parentSection: Section): Infobox {
        val infobox = Infobox(
            imgref = this.imgref,
            information = this.information,
            subinformation = this.subinformation,
            hidden = this.hidden
        )
        infobox.user = user
        infobox.section = parentSection
        return infobox
    }

}