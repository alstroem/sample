package dk.alstroem.data.environmemt

import dk.alstroem.data.environmemt.local.EnvironmentDao
import dk.alstroem.domain.environment.EnvironmentRepository
import dk.alstroem.domain.environment.model.SensorEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EnvironmentRepositoryImpl(
    private val environmentDao: EnvironmentDao
) : EnvironmentRepository {
    override val sensorEvents: Flow<List<SensorEvent>>
        get() = environmentDao.getSensorEvents().map { events ->
            events.map { it.mapToDomain() }
        }

    override suspend fun insertSensorEvent(event: SensorEvent) {
        environmentDao.insertSensorEvent(event.mapToEntity())
    }

    override suspend fun getSensorEvent(timestamp: Long): SensorEvent? {
        return environmentDao.getSensorEvent(timestamp)?.mapToDomain()
    }

    override suspend fun clearSensorEvents() {
        environmentDao.deleteEvents()
    }
}
