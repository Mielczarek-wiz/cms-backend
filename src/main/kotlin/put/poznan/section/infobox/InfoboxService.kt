package put.poznan.section.infobox

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import put.poznan.files.FileService
import put.poznan.section.infobox.dto.InfoboxDtoRequest
import put.poznan.section.infobox.dto.InfoboxDtoResponse
import put.poznan.user.UserCMS
import put.poznan.user.UserCMSRepository

@Service
class InfoboxService (
    val infoboxRepository: InfoboxRepository,
    val userCMSRepository: UserCMSRepository,
    val fileService: FileService
) {
    fun findAll(): List<InfoboxDtoResponse> {
        val allInfoboxes = infoboxRepository.findAll()
        val responseInfoboxes = allInfoboxes.map { it.toResponse() }
        return responseInfoboxes
    }

    fun create(newInfobox: InfoboxDtoRequest): ResponseEntity<Map<String, String>> {
        val user = userCMSRepository.findUserCMSByEmail("basia@o2.pl")
        return if (user != null) {
            infoboxRepository.save(newInfobox.toModel(user))
            val responseBody = mapOf("message" to "Infobox created")
            ResponseEntity(responseBody, HttpStatus.OK)
        } else {
            val errorMassage = mapOf("massage" to "Cannot create infobox")
            ResponseEntity(errorMassage, HttpStatus.BAD_REQUEST)
        }
    }

    fun modify(id: Long, updatedInfobox: InfoboxDtoRequest): ResponseEntity<Map<String, String>> {
        val infobox = infoboxRepository.findInfoboxById(id)
        val user = userCMSRepository.findUserCMSByEmail("basia@o2.pl")
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
            information = this.information,
            subinformation = this.subinformation,
            imgref = this.imgref,
            user = user,
            hidden = this.hidden,
        )
    }

    private fun Infobox.toUpdatedModel(user: UserCMS, updatedInfobox: InfoboxDtoRequest): Infobox {
        val infobox = Infobox(
            id = this.id,
            imgref = updatedInfobox.icon,
            information = updatedInfobox.information,
            subinformation = updatedInfobox.subinformation,
            hidden = updatedInfobox.hidden
        )
        infobox.user = user
        return infobox
    }

    private fun InfoboxDtoRequest.toModel(user: UserCMS): Infobox {
        val infobox = Infobox(
            imgref = this.icon,
            information = this.information,
            subinformation = this.subinformation,
            hidden = this.hidden
        )
        infobox.user = user
        return infobox
    }

}