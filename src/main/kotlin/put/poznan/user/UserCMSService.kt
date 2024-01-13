package put.poznan.user

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import put.poznan.user.projection.UserDto
import put.poznan.user.projection.UserDtoFormRegister
import put.poznan.user.role.RoleRepository

@Service
class UserCMSService(
    private val userCMSRepository: UserCMSRepository,
    private val roleRepository: RoleRepository,
    private val encoder: PasswordEncoder
) {
    fun createUser(newUser: UserDtoFormRegister): ResponseEntity<String> {
        val user = userCMSRepository.findUserCMSByEmail(newUser.email)
        return if(user == null){
            userCMSRepository.save(newUser.toModel())
            ResponseEntity("User created", HttpStatus.OK)
        } else ResponseEntity("Cannot create user", HttpStatus.BAD_REQUEST)
    }

    fun findUserByID(id: Long): UserDto =
        userCMSRepository.findUserCMSById(id)
            ?.toResponse()
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND,"User not found for ID: $id")

    fun findAll(): List<UserDto>{
        val userCMS = userCMSRepository.findAll()
        val responseUsers = userCMS.map { it.toResponse() }
        return responseUsers
    }
    fun deleteById(id: Long): ResponseEntity<Boolean> {
        val user = userCMSRepository.findUserCMSById(id)
        return if(user != null){
            userCMSRepository.delete(user)
            ResponseEntity.noContent().build()
        } else throw ResponseStatusException(HttpStatus.NOT_FOUND,"User not found for ID: $id")
    }

    private fun UserDtoFormRegister.toModel(): UserCMS{
        val userCMS = UserCMS(
            name = this.name,
            surname = this.surname,
            email = this.email,
            password = encoder.encode(this.password)
        )

        userCMS.role = roleRepository.findRoleByName(this.role)

        return userCMS
    }

    private fun UserCMS.toResponse(): UserDto {
        return UserDto(
            id = this.id,
            name = this.name,
            surname = this.surname,
            email = this.email
        )
    }

}