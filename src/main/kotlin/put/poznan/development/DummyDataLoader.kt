package put.poznan.development

import lombok.AllArgsConstructor
import org.springframework.boot.CommandLineRunner
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import put.poznan.user.UserCMSRepository
import put.poznan.user.role.Role
import put.poznan.user.role.RoleRepository
import put.poznan.user.role.UserCMS

@Component
@AllArgsConstructor
@Order(1)
class DummyDataLoader(
    private val roleRepository: RoleRepository,
    private val userCMSRepository: UserCMSRepository
): CommandLineRunner {
    override fun run(vararg args: String?) {
        try {
            val mod = Role(name = "Moderator")
            val adm = Role(name = "Admin")
            roleRepository.save(mod)
            roleRepository.save(adm)
            val basia = UserCMS(name = "Pani", surname = "Basia", email = "basia@o2.pl", password = "pass1")
            basia.role = mod
            userCMSRepository.save(basia)

            val wojtek = UserCMS(name = "Pan", surname = "Wojtek", email = "wojtek@o2.pl", password = "pass2")
            wojtek.role = adm
            userCMSRepository.save(wojtek)

            mod.user = wojtek
            adm.user = wojtek

            roleRepository.saveAll(listOf(mod, adm))

        } catch (exception: Exception){
            throw Exception(exception.message)
        }
    }

}