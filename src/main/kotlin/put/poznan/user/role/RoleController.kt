package put.poznan.user.role

import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import put.poznan.user.role.dto.RoleRequest
import put.poznan.user.role.dto.RoleResponse

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ["user/role"])
class RoleController(
    private val roleService: RoleService
) {
    @GetMapping
    fun getAll(): List<RoleResponse> =
        roleService.findAll()


    @PostMapping
    fun createRole(@RequestBody newRole: RoleRequest): ResponseEntity<Map<String, String>> =
        roleService.create(newRole)
    @PutMapping("{id}")
    fun updateRole(@PathVariable id:Long, @RequestBody updatedRole: RoleRequest): ResponseEntity<Map<String, String>> =
        roleService.update(id, updatedRole)
    @DeleteMapping("{id}")
    fun deleteRole(@PathVariable id: Long): ResponseEntity<Map<String, String>> =
        roleService.delete(id)
}