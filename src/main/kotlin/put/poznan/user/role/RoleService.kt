package put.poznan.user.role

import org.springframework.stereotype.Service

@Service
class RoleService(
    private val roleRepository: RoleRepository
) {
}