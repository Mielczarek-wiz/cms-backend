package put.poznan.user.role

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaRepositories
interface RoleRepository: JpaRepository<Role, Long> {
    fun findRoleByName(name: String): Role?
    fun findRoleById(id: Long): Role?

    fun findRolesByUserId(id: Long): List<Role>
}