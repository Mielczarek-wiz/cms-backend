package put.poznan.section

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaRepositories
interface SectionRepository: JpaRepository<Section, Long> {
}