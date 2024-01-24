package put.poznan.section

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaRepositories
interface SectionRepository: JpaRepository<Section, Long> {
    fun findSectionById(id: Long): Section?
    fun findSectionByTitle(title: String): Section?
}