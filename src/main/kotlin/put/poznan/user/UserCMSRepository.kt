package put.poznan.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.stereotype.Repository
import put.poznan.user.role.UserCMS

@EnableJpaRepositories
@Repository
interface UserCMSRepository: JpaRepository<UserCMS, Long> {
}