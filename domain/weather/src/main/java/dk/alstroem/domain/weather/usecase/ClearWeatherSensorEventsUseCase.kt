package dk.alstroem.domain.weather.usecase

import dk.alstroem.domain.weather.WeatherRepository

class ClearWeatherSensorEventsUseCase(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke() {
        repository.clearWeatherSensorEvents()
    }
}
