package put.poznan.slider

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaRepositories
interface SliderRepository: JpaRepository<Slider, Long> {
    fun findSliderById(id: Long): Slider?
    fun findSliderByTitle(title: String): Slider?
    fun findSlidersByHiddenIsFalse(): List<Slider>
}