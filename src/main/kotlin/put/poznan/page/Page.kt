package put.poznan.page

import jakarta.persistence.*
import lombok.*
import org.jetbrains.annotations.NotNull
import put.poznan.section.Section
import put.poznan.user.role.UserCMS

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
class Page (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,
    @NotNull
    var name: String = "",
    @NotNull
    var link: String = "",
    var hidden: Boolean = true
) {
    @ManyToOne
    @JoinColumn(name="UsercmsID")
    lateinit var user: UserCMS

    @ManyToOne
    @JoinColumn(name="ParentID")
    lateinit var page: Page

    @ManyToMany
    var sections: List<Section> = listOf()
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Page

        if (id != other.id) return false
        if (name != other.name) return false
        if (link != other.link) return false
        if (hidden != other.hidden) return false
        if (user != other.user) return false
        if (page != other.page) return false
        if (sections != other.sections) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + link.hashCode()
        result = 31 * result + hidden.hashCode()
        result = 31 * result + user.hashCode()
        result = 31 * result + page.hashCode()
        result = 31 * result + sections.hashCode()
        return result
    }
}