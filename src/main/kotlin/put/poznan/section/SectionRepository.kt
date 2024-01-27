package put.poznan.section

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import put.poznan.section.type.Type

@EnableJpaRepositories
interface SectionRepository: JpaRepository<Section, Long> {
    fun findSectionById(id: Long): Section?
    fun existsByType(type: Type): Boolean
    fun findSectionByTitle(title: String): Section?
}