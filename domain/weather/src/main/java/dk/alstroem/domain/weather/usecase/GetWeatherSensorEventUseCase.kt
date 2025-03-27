package dk.alstroem.domain.weather.usecase

import dk.alstroem.domain.weather.WeatherRepository
import dk.alstroem.domain.weather.model.WeatherSensorEvent
import kotlinx.coroutines.flow.Flow

class GetWeatherSensorEventUseCase(
    private val repository: WeatherRepository
) {
    operator fun invoke(timestamp: Long): Flow<WeatherSensorEvent?> {
        return repository.getWeatherSensorEvent(timestamp)
    }
}
