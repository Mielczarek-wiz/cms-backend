package put.poznan.user.role

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import put.poznan.user.UserCMS
import put.poznan.user.UserCMSRepository
import put.poznan.user.role.dto.RoleRequest
import put.poznan.user.role.dto.RoleResponse

@Service
class RoleService(
    private val userCMSRepository: UserCMSRepository,
    private val roleRepository: RoleRepository
) {
    fun findAll(): List<RoleResponse> {
        val roles = roleRepository.findAll()
        val responseRoles = roles.map {it.toResponse()}
        return responseRoles
    }
    fun create(newRole: RoleRequest): ResponseEntity<Map<String, String>> {
        val user = userCMSRepository.findUserCMSByEmail(newRole.user)
        val role = roleRepository.findRoleByName(newRole.name)

        return if(role == null && user != null){
            roleRepository.save(newRole.toModel(user))
            val responseBody = mapOf("message" to "Role created")
            ResponseEntity(responseBody, HttpStatus.OK)
        } else{
            val errorMessage = mapOf("message" to "Cannot create role with name: ${newRole.name}")
            ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
        }
    }
    fun update(id: Long, updatedRole: RoleRequest): ResponseEntity<Map<String, String>> {
        val user = userCMSRepository.findUserCMSByEmail(updatedRole.user)
        val role = roleRepository.findRoleById(id)

        return if(role != null && user != null){
            val roleCopied = role.copy()
            roleRepository.save(roleCopied.toUpdatedModel(user, updatedRole))
            val responseBody = mapOf("message" to "Role updated")
            ResponseEntity(responseBody, HttpStatus.OK)
        } else{
            val errorMessage = mapOf("message" to "Cannot update role")
            ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
        }
    }

    fun delete(id: Long): ResponseEntity<Map<String, String>> {
        val role = roleRepository.findRoleById(id)
        return if(role != null){
            roleRepository.delete(role)
            val responseBody = mapOf("message" to "Role deleted")
            ResponseEntity(responseBody, HttpStatus.OK)
        } else{
            val errorMessage = mapOf("message" to "Cannot delete role with id: $id")
            ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
        }
    }
    private fun Role.toResponse(): RoleResponse {
        val user = this.user.name + " " + this.user.surname
        return RoleResponse(
            id = this.id,
            name = this.name,
            user = user,
            hidden = this.hidden
        )
    }
    private fun RoleRequest.toModel(user: UserCMS): Role {
        val role = Role(
            name = this.name,
            hidden = this.hidden
        )
        role.user = user
        return role
    }
    private fun Role.toUpdatedModel(user: UserCMS, updatedRole: RoleRequest): Role {
        val role = Role(
            id = this.id,
            name = updatedRole.name,
            hidden = updatedRole.hidden
        )
        role.user = user
        return role
    }



}