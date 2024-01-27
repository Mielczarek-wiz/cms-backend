package put.poznan.section.infobox

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaRepositories
interface InfoboxRepository: JpaRepository<Infobox, Long> {
    fun findInfoboxById(id: Long): Infobox?
    fun findInfoboxByInformation(information: String): Infobox?
    fun findInfoboxesByInformationStartingWith(partInformation: String): List<Infobox>
}