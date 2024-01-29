package put.poznan.page

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.stereotype.Repository
import put.poznan.section.Section

@EnableJpaRepositories
@Repository
interface PageRepository: JpaRepository<Page, Long> {
    fun findPagesByHiddenIsFalse(): List<Page>
    fun findPageById(id: Long): Page?
    fun findPageByName(name: String): Page?
    fun findPageByLinkAndHiddenIsFalse(link: String): Page?
    fun findPagesBySectionsContaining(section: Section): List<Page>
    fun findPagesByPageIsNullAndHiddenIsFalse(): List<Page>
    fun findPagesByPageIdAndHiddenIsFalse(id: Long): List<Page>
}