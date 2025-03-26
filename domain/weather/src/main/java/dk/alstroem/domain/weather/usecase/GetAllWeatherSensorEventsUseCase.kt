package dk.alstroem.domain.weather.usecase

import dk.alstroem.domain.weather.WeatherRepository
import dk.alstroem.domain.weather.model.WeatherSensorEvent
import kotlinx.coroutines.flow.Flow

class GetAllWeatherSensorEventsUseCase(
    private val repository: WeatherRepository
) {
    operator fun invoke(): Flow<List<WeatherSensorEvent>> {
        return repository.weatherSensorEvents
    }
}
