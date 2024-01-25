package put.poznan.development

import lombok.AllArgsConstructor
import org.springframework.boot.CommandLineRunner
import org.springframework.core.annotation.Order
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import put.poznan.user.UserCMS
import put.poznan.user.UserCMSRepository
import put.poznan.user.role.Role
import put.poznan.user.role.RoleRepository

@Component
@AllArgsConstructor
@Order(1)
class DummyDataLoader(
    private val roleRepository: RoleRepository,
    private val userCMSRepository: UserCMSRepository,
    private val encoder: PasswordEncoder
): CommandLineRunner {
    override fun run(vararg args: String?) {
        try {
            val mod = Role(name = "Moderator")
            val adm = Role(name = "Admin")
            roleRepository.save(mod)
            roleRepository.save(adm)
            val basia = UserCMS(name = "Pani", surname = "Basia", email = "basia@o2.pl", password = encoder.encode("pass1"))
            basia.role = mod
            val wojtek = UserCMS(name = "Pan", surname = "Wojtek", email = "wojtek@o2.pl", password = encoder.encode("pass2"))
            wojtek.role = adm

            val wojtekIn = userCMSRepository.findUserCMSByEmail(wojtek.email)
            val basiaIn = userCMSRepository.findUserCMSByEmail(basia.email)
            if (wojtekIn == null && basiaIn == null) {
                userCMSRepository.save(wojtek)
                userCMSRepository.save(basia)
                mod.user = wojtek
                adm.user = wojtek

                roleRepository.saveAll(listOf(mod, adm))
            }



        } catch (exception: Exception){
            throw Exception(exception.message)
        }
    }

}