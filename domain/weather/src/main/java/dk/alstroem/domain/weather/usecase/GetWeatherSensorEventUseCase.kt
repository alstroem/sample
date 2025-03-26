package dk.alstroem.domain.weather.usecase

import dk.alstroem.domain.weather.WeatherRepository
import dk.alstroem.domain.weather.model.WeatherSensorEvent

class GetWeatherSensorEventUseCase(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(timestamp: Long): WeatherSensorEvent? {
        return repository.getWeatherSensorEvent(timestamp)
    }
}
