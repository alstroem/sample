package dk.alstroem.data.environmemt

import dk.alstroem.data.environmemt.local.model.SensorEventEntity
import dk.alstroem.domain.environment.model.SensorEvent

internal fun SensorEventEntity.mapToDomain(): SensorEvent {
    return SensorEvent(
        timestamp = timestamp,
        temperature = temperature,
        humidity = humidity
    )
}

internal fun SensorEvent.mapToEntity(): SensorEventEntity {
    return SensorEventEntity(
        timestamp = timestamp,
        temperature = temperature,
        humidity = humidity
    )
}
