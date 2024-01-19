package put.poznan.user

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import put.poznan.user.dto.DeletingUserCheckDtoRequest
import put.poznan.user.dto.UserDtoRequest
import put.poznan.user.dto.UserDtoResponse
import put.poznan.user.role.Role
import put.poznan.user.role.RoleRepository

@Service
class UserCMSService(
    private val userCMSRepository: UserCMSRepository,
    private val roleRepository: RoleRepository,
    private val encoder: PasswordEncoder
) {
    fun createUser(newUser: UserDtoRequest): ResponseEntity<Map<String, String>> {
        val user = userCMSRepository.findUserCMSByEmail(newUser.email)
        return if(user == null){
            userCMSRepository.save(newUser.toModel())
            val responseBody = mapOf("message" to "User created")
            ResponseEntity(responseBody, HttpStatus.OK)
        } else {
            val errorMessage = mapOf("message" to "Cannot create user with email: ${newUser.email}")
            ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
        }
    }

    fun findUserByID(id: Long): UserDtoResponse =
        userCMSRepository.findUserCMSById(id)
            ?.toResponse()
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND,"User not found for ID: $id")

    fun findAll(): List<UserDtoResponse>{
        val userCMS = userCMSRepository.findAll()
        val responseUsers = userCMS.map { it.toResponse() }
        return responseUsers
    }
    fun modify(id: Long, updatedUser: UserDtoRequest): ResponseEntity<Map<String, String>> {
        val user = userCMSRepository.findUserCMSById(id)
        val role = roleRepository.findRoleByName(updatedUser.role)

        return if(user != null && role != null){
            val userCopied = user.copy()
            userCMSRepository.save(userCopied.toUpdatedUser(role, updatedUser))
            val responseBody = mapOf("message" to "User updated")
            ResponseEntity(responseBody, HttpStatus.OK)
        } else {
            val errorMessage = mapOf("message" to "Cannot update user")
            ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
        }
    }
    fun delete(id: Long, userCheck: DeletingUserCheckDtoRequest): ResponseEntity<Map<String, String>> {
        val user = userCMSRepository.findUserCMSById(id)
        return if(user != null && user.email == userCheck.user){
            val errorMessage = mapOf("message" to "You cannot delete yourself")
            ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
        } else {
            if(user != null){
                println("User: $user, id: $id, email: $userCheck.user")
                if(user.role.name == "Admin"){
                    val errorMessage = mapOf("message" to "You cannot delete Administrators")
                    ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
                }
                else {
                    preDelete(user)
                    userCMSRepository.delete(user)
                    val responseBody = mapOf("message" to "User deleted")
                    ResponseEntity(responseBody, HttpStatus.OK)
                }

            } else {
                val errorMessage = mapOf("message" to "Cannot delete user with id: $id")
                ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
            }
        }
    }
    private fun preDelete(user: UserCMS): Void? {
        val authoredRoles = roleRepository.findRolesByUserId(user.id)
        authoredRoles.map {

            it.user = null
        }
        roleRepository.saveAll(authoredRoles)
        return null
    }
    private fun UserDtoRequest.toModel(): UserCMS{
        val userCMS = UserCMS(
            name = this.name,
            surname = this.surname,
            email = this.email,
            password = encoder.encode(this.password)
        )

        userCMS.role = roleRepository.findRoleByName(this.role)!!

        return userCMS
    }

    private fun UserCMS.toResponse(): UserDtoResponse {
        return UserDtoResponse(
            id = this.id,
            name = this.name,
            surname = this.surname,
            email = this.email,
            role = this.role.name
        )
    }
    private fun UserCMS.toUpdatedUser(role: Role, updatedUser: UserDtoRequest): UserCMS {
        val userCMS = UserCMS(
            id = this.id,
            name = updatedUser.name,
            surname = updatedUser.surname,
            email = updatedUser.email,
            password = encoder.encode(updatedUser.password)
        )

        userCMS.role = role

        return userCMS
    }



}