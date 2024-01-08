package put.poznan.section.type

import jakarta.persistence.*
import lombok.*
import org.jetbrains.annotations.NotNull
import put.poznan.user.role.UserCMS

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
class Type (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,
    @NotNull
    var type: String = "",
    var hidden: Boolean = true
) {
    @ManyToOne
    @JoinColumn(name="UsercmsID")
    lateinit var user: UserCMS
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Type

        if (id != other.id) return false
        if (type != other.type) return false
        if (hidden != other.hidden) return false
        if (user != other.user) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + hidden.hashCode()
        result = 31 * result + user.hashCode()
        return result
    }

}