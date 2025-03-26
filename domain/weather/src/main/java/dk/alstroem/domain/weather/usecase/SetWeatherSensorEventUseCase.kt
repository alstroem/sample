package dk.alstroem.domain.weather.usecase

import dk.alstroem.domain.weather.WeatherRepository
import dk.alstroem.domain.weather.model.WeatherSensorEvent

class SetWeatherSensorEventUseCase(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(event: WeatherSensorEvent) {
        repository.setWeatherSensorEvent(event)
    }
}
