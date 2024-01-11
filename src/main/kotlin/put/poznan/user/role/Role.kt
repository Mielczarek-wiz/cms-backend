package put.poznan.user.role

import jakarta.persistence.*
import lombok.*
import org.jetbrains.annotations.NotNull

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
data class Role (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @NotNull
    val name: String = "",

    val hidden: Boolean = true
) {
    @ManyToOne
    @JoinColumn(name="UsercmsID")
    lateinit var user:UserCMS
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Role

        if (id != other.id) return false
        if (name != other.name) return false
        if (hidden != other.hidden) return false
        if (user != other.user) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + hidden.hashCode()
        result = 31 * result + user.hashCode()
        return result
    }

}