package put.poznan.development

import lombok.AllArgsConstructor
import org.springframework.boot.CommandLineRunner
import org.springframework.core.annotation.Order
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import put.poznan.section.type.Type
import put.poznan.section.type.TypeRepository
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
    private val typeRepository: TypeRepository,
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
                val type1 = Type(type = "BestOffers", hidden = false)
                val type2 = Type(type = "Categories", hidden = false)
                val type3 = Type(type = "Contact", hidden = false)
                val type4 = Type(type = "Infoboxes", hidden = false)
                val type5 = Type(type = "MainSlider", hidden = false)
                val type6 = Type(type = "OurTeam", hidden = false)
                val type7 = Type(type = "PopularProperties", hidden = false)
                val type8 = Type(type = "Testimonials", hidden = false)
                val type9 = Type(type = "WhoWeAre", hidden = false)
                val type10 = Type(type = "WhyUs", hidden = false)

                type1.user = wojtekIn
                type2.user = wojtekIn
                type3.user = wojtekIn
                type4.user = wojtekIn
                type5.user = wojtekIn
                type6.user = wojtekIn
                type7.user = wojtekIn
                type8.user = wojtekIn
                type9.user = wojtekIn
                type10.user = wojtekIn
                typeRepository.saveAll(listOf(type1, type2, type3, type4, type5, type6, type7, type8, type9, type10))
            }

        } catch (exception: Exception){
            throw Exception(exception.message)
        }
    }

}