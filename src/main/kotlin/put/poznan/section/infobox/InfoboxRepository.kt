package put.poznan.section.infobox

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaRepositories
interface InfoboxRepository: JpaRepository<Infobox, Long> {
    fun findInfoboxById(id: Long): Infobox?
    fun findInfoboxByInformationAndHiddenIsFalse(information: String): Infobox?
    fun findInfoboxesByInformationStartingWithAndHiddenIsFalse(partInformation: String): List<Infobox>
}