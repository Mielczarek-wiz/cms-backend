package put.poznan.general

import jakarta.persistence.*
import lombok.*
import org.jetbrains.annotations.NotNull
import put.poznan.user.UserCMS

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
data class General(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @NotNull
    val key: String = "",
    val value: String = "",
    val description: String = "",
    val hidden: Boolean = true
) {
    @ManyToOne
    @JoinColumn(name="UsercmsID")
    lateinit var user:UserCMS
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as General

        if (id != other.id) return false
        if (key != other.key) return false
        if (value != other.value) return false
        if (description != other.description) return false
        if (hidden != other.hidden) return false
        if (user != other.user) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + key.hashCode()
        result = 31 * result + value.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + hidden.hashCode()
        result = 31 * result + user.hashCode()
        return result
    }

}