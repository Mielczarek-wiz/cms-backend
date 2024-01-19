package put.poznan.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import put.poznan.user.role.Role

@EnableJpaRepositories
interface UserCMSRepository: JpaRepository<UserCMS, Long> {
    fun findUserCMSByEmail(email: String): UserCMS?
    fun findUserCMSById(id: Long): UserCMS?

    fun existsByRole(role: Role): Boolean
}