package put.poznan.slider

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaRepositories
interface SliderRepository: JpaRepository<Slider, Long> {
}