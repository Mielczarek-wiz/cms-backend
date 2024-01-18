package put.poznan.general

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import put.poznan.general.dto.GeneralDtoRequest
import put.poznan.general.dto.GeneralDtoResponse
import put.poznan.user.UserCMS
import put.poznan.user.UserCMSRepository

@Service
class GeneralService(
    val generalRepository: GeneralRepository,
    val userCMSRepository: UserCMSRepository
) {
    fun findAll(): List<GeneralDtoResponse> {
        val allGenerals = generalRepository.findAll()
        val responseGenerals = allGenerals.map { it.toResponse() }
        return responseGenerals
    }
    fun create(newGeneral: GeneralDtoRequest): ResponseEntity<Map<String, String>> {
        val general = generalRepository.findGeneralByKey(newGeneral.key)
        val user = userCMSRepository.findUserCMSByEmail(newGeneral.user)
        return if(general == null && user != null){
            generalRepository.save(newGeneral.toModel(user))
            val responseBody = mapOf("message" to "General created")
            ResponseEntity(responseBody, HttpStatus.OK)
        } else {
            val errorMessage = mapOf("message" to "Cannot create general")
            ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
        }
    }
    fun modify(id: Long, updatedGeneral: GeneralDtoRequest): ResponseEntity<Map<String, String>> {
        val general = generalRepository.findGeneralById(id)
        val user = userCMSRepository.findUserCMSByEmail(updatedGeneral.user)
        return if(general != null && user != null){
            val generalCopied = general.copy()
            generalRepository.save(generalCopied.toUpdatedModel(user, updatedGeneral))
            val responseBody = mapOf("message" to "General updated")
            ResponseEntity(responseBody, HttpStatus.OK)
        } else {
            val errorMessage = mapOf("message" to "Cannot update general")
            ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
        }
    }
    fun delete(id: Long): ResponseEntity<Map<String, String>> {
        val general = generalRepository.findGeneralById(id)
        return if (general != null){
            generalRepository.delete(general)
            val responseBody = mapOf("message" to "General deleted")
            ResponseEntity(responseBody, HttpStatus.OK)
        }else {
            val errorMessage = mapOf("message" to "Cannot delete general with id: $id")
            ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
        }
    }
    private fun General.toResponse(): GeneralDtoResponse {
        val user = this.user?.name + " " + this.user?.surname
        return GeneralDtoResponse(
            id= this.id,
            key=this.key,
            value=this.value,
            description=this.description,
            hidden = this.hidden,
            user = user
        )
    }
    private fun General.toUpdatedModel(user: UserCMS, updatedGeneral: GeneralDtoRequest): General {
        val general = General(
            id = this.id,
            key=updatedGeneral.key,
            value=updatedGeneral.value,
            description=updatedGeneral.description,
            hidden = updatedGeneral.hidden
        )
        general.user = user
        return general
    }
    private fun GeneralDtoRequest.toModel(user: UserCMS): General {
        val general = General(
            key = this.key,
            value = this.value,
            description = this.description,
            hidden = this.hidden
        )
        general.user = user
        return general
    }

}