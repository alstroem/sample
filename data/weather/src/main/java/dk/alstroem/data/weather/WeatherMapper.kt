package dk.alstroem.data.weather

import dk.alstroem.data.weather.local.model.WeatherSensorEventEntity
import dk.alstroem.domain.weather.model.WeatherSensorEvent

internal fun WeatherSensorEventEntity.mapToDomain(): WeatherSensorEvent {
    return WeatherSensorEvent(
        timestamp = timestamp,
        temperature = temperature,
        humidity = humidity
    )
}

internal fun WeatherSensorEvent.mapToEntity(): WeatherSensorEventEntity {
    return WeatherSensorEventEntity(
        timestamp = timestamp,
        temperature = temperature,
        humidity = humidity
    )
}
