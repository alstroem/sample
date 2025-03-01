package dk.alstroem.domain.environment.usecase

import dk.alstroem.domain.environment.EnvironmentRepository
import dk.alstroem.domain.environment.model.SensorEvent
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSensorEventsUseCase @Inject constructor(
    private val repository: EnvironmentRepository
) {
    operator fun invoke(): Flow<List<SensorEvent>> {
        return repository.sensorEvents
    }
}
