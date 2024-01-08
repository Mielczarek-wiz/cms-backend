package put.poznan.user.role

import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ["user/role"])
class RoleController {
    @GetMapping("all")
    fun getAll(): String {
        return "Hello World"
    }
}