package put.poznan.section.type

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import put.poznan.section.SectionRepository
import put.poznan.section.type.dto.TypeDtoRequest
import put.poznan.section.type.dto.TypeDtoResponse
import put.poznan.user.UserCMS
import put.poznan.user.UserCMSRepository

@Service
class TypeService (
    private val typeRepository: TypeRepository,
    private val sectionRepository: SectionRepository,
    private val userCMSRepository: UserCMSRepository
) {
    fun findAll(): List<TypeDtoResponse> {
        val allTypes = typeRepository.findAll()
        val responseTypes = allTypes.map { it.toResponse() }
        return responseTypes
    }

    fun create(newType: TypeDtoRequest): ResponseEntity<Map<String, String>> {
        val user = userCMSRepository.findUserCMSByEmail(newType.user)
        return if (user !== null) {
            typeRepository.save(newType.toModel(user))
            val responseBody = mapOf("message" to "Type created")
            ResponseEntity(responseBody, HttpStatus.OK)
        } else {
            val errorMassage = mapOf("massage" to "Cannot create type")
            ResponseEntity(errorMassage, HttpStatus.BAD_REQUEST)
        }
    }

    fun modify(id: Long, updatedType: TypeDtoRequest): ResponseEntity<Map<String, String>> {
        val type = typeRepository.findTypeById(id)
        val user = type?.user
        return if(type != null && user != null){
            val typeCopied = type.copy()
            typeRepository.save(typeCopied.toUpdatedModel(user, updatedType))
            val responseBody = mapOf("message" to "Type updated")
            ResponseEntity(responseBody, HttpStatus.OK)
        } else {
            val errorMessage = mapOf("message" to "Cannot update type")
            ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
        }
    }

    fun delete(id: Long): ResponseEntity<Map<String, String>> {
        val type = typeRepository.findTypeById(id)
        return if (type != null){
            if(sectionRepository.existsByType(type)){
                val errorMessage = mapOf("message" to "Cannot delete type with id: $id, because it is used in section")
                return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
            }
            else{
                typeRepository.delete(type)
                val responseBody = mapOf("message" to "Type deleted")
                ResponseEntity(responseBody, HttpStatus.OK)
            }

        } else {
            val errorMessage = mapOf("message" to "Cannot delete type with id: $id")
            ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
        }
    }

    private fun Type.toResponse(): TypeDtoResponse {
        val user = this.user?.name + " " + this.user?.surname
        return TypeDtoResponse(
            id = this.id,
            type = this.type,
            hidden = this.hidden,
            user = user
        )
    }

    private fun Type.toUpdatedModel(user: UserCMS, updatedType: TypeDtoRequest): Type {
        val type = Type(
            id = this.id,
            type = updatedType.type,
            hidden = updatedType.hidden
        )
        type.user = user
        return type
    }

    private fun TypeDtoRequest.toModel(user: UserCMS): Type {
        val type = Type(
            type = this.type,
            hidden = this.hidden
        )
        type.user = user
        return type
    }
}