package put.poznan.section.infobox

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.stereotype.Repository

@EnableJpaRepositories
@Repository
interface InfoboxRepository: JpaRepository<Infobox, Long> {
}