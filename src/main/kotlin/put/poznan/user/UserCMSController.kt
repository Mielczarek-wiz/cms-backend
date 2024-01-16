package put.poznan.user

import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import put.poznan.user.dto.UserDto
import put.poznan.user.dto.UserDtoFormRegister

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ["user"])
class UserCMSController(
    private val userCMSService: UserCMSService
) {
    @PostMapping
    fun create(@RequestBody newUser: UserDtoFormRegister): ResponseEntity<String> =
        userCMSService.createUser(newUser)

    @GetMapping
    fun getAll(): List<UserDto> = userCMSService.findAll()

    @GetMapping("{id}")
    fun getById(@PathVariable id: Long): UserDto? = userCMSService.findUserByID(id)

    @DeleteMapping("{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<Boolean> = userCMSService.deleteById(id)
}