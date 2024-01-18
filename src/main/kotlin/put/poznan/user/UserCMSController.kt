package put.poznan.user

import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import put.poznan.user.dto.UserDtoRequest
import put.poznan.user.dto.UserDtoResponse

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ["user"])
class UserCMSController(
    private val userCMSService: UserCMSService
) {
    @PostMapping
    fun create(@RequestBody newUser: UserDtoRequest): ResponseEntity<String> =
        userCMSService.createUser(newUser)

    @GetMapping
    fun getAll(): List<UserDtoResponse> = userCMSService.findAll()

    @GetMapping("{id}")
    fun getById(@PathVariable id: Long): UserDtoResponse? = userCMSService.findUserByID(id)
    @PutMapping("{id}")
    fun modifyUser(@PathVariable id: Long, @RequestBody updatedUser: UserDtoRequest): ResponseEntity<Map<String, String>> = userCMSService.modify(id, updatedUser)
    @DeleteMapping("{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<Map<String, String>> = userCMSService.delete(id)
}