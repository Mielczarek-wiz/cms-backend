package put.poznan.page

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.stereotype.Repository

@EnableJpaRepositories
@Repository
interface PageRepository: JpaRepository<Page, Long> {
    fun findPageById(id: Long): Page?
    fun findPageByName(name: String): Page?
    fun findPageByLink(link: String): Page?

    fun findPagesByPageIsNull(): List<Page>
    fun findPagesByPageId(id: Long): List<Page>
}