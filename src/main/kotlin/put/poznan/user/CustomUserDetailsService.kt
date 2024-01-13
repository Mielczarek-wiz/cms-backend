package put.poznan.user

import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

typealias ApplicationUser = put.poznan.user.UserCMS
@Service
class CustomUserDetailsService(
    private val userCMSRepository: UserCMSRepository
): UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails = userCMSRepository.findUserCMSByEmail(username)?.mapToUserDetails()
        ?: throw UsernameNotFoundException("Not found!")

    private fun ApplicationUser.mapToUserDetails(): UserDetails =
        User.builder()
            .username(this.email)
            .password(this.password)
            .roles(this.role.name)
            .build()
}