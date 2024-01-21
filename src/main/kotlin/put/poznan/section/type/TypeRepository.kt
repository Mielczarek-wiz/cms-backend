package put.poznan.section.type

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaRepositories
interface TypeRepository: JpaRepository<Type, Long> {
    fun findTypeById(id: Long): Type?
}