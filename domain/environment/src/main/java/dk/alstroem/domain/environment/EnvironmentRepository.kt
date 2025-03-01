package dk.alstroem.domain.environment

import dk.alstroem.domain.environment.model.SensorEvent
import kotlinx.coroutines.flow.Flow

interface EnvironmentRepository {
    val sensorEvents: Flow<List<SensorEvent>>
    suspend fun insertSensorEvent(event: SensorEvent)
    suspend fun getSensorEvent(timestamp: Long): SensorEvent?
    suspend fun clearSensorEvents()
}