package dk.alstroem.data.weather

import dk.alstroem.data.weather.local.WeatherSensorDao
import dk.alstroem.domain.weather.WeatherRepository
import dk.alstroem.domain.weather.model.WeatherSensorEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WeatherRepositoryImpl(
    private val weatherSensorDao: WeatherSensorDao
) : WeatherRepository {
    override val weatherSensorEvents: Flow<List<WeatherSensorEvent>>
        get() = weatherSensorDao.getSensorEvents().map { events ->
            events.map { it.mapToDomain() }
        }

    override suspend fun setWeatherSensorEvent(event: WeatherSensorEvent) {
        weatherSensorDao.insertSensorEvent(event.mapToEntity())
    }

    override suspend fun getWeatherSensorEvent(timestamp: Long): WeatherSensorEvent? {
        return weatherSensorDao.getSensorEvent(timestamp)?.mapToDomain()
    }

    override suspend fun clearWeatherSensorEvents() {
        weatherSensorDao.deleteEvents()
    }
}
