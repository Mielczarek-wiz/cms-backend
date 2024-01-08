package put.poznan.slider

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
class Slider(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,
    @NotNull
    var title: String = "",
    @NotNull
    var text: String = "",
    @NotNull
    var imgref: String = "",
    var hidden: Boolean = true
) {
        @ManyToOne
        @JoinColumn(name="UsercmsID")
        lateinit var user: UserCMS
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Slider

        if (id != other.id) return false
        if (title != other.title) return false
        if (text != other.text) return false
        if (imgref != other.imgref) return false
        if (hidden != other.hidden) return false
        if (user != other.user) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + text.hashCode()
        result = 31 * result + imgref.hashCode()
        result = 31 * result + hidden.hashCode()
        result = 31 * result + user.hashCode()
        return result
    }
}