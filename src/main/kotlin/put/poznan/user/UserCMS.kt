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
class UserCMS(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,
    @NotNull
    var name: String = "",
    @NotNull
    var surname: String = "",
    @Column(unique = true)
    var email: String = "",
    @NotNull
    var password: String = ""
) {
    @ManyToOne
    @JoinColumn(name = "RoleID")
    lateinit var role: Role
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserCMS

        if (id != other.id) return false
        if (name != other.name) return false
        if (surname != other.surname) return false
        if (email != other.email) return false
        if (password != other.password) return false
        if (role != other.role) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + surname.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + role.hashCode()
        return result
    }
}