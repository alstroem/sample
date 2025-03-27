package dk.alstroem.domain.weather

import dk.alstroem.domain.weather.model.WeatherSensorEvent
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    val weatherSensorEvents: Flow<List<WeatherSensorEvent>>
    suspend fun setWeatherSensorEvent(event: WeatherSensorEvent)
    fun getWeatherSensorEvent(timestamp: Long): Flow<WeatherSensorEvent?>
    suspend fun clearWeatherSensorEvents()
}
