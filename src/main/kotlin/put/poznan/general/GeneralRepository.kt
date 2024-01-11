package put.poznan.general

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaRepositories
interface GeneralRepository: JpaRepository<General, Long> {
}