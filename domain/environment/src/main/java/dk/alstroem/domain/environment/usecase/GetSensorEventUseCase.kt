package dk.alstroem.domain.environment.usecase

import dk.alstroem.domain.environment.EnvironmentRepository
import dk.alstroem.domain.environment.model.SensorEvent
import javax.inject.Inject

class GetSensorEventUseCase @Inject constructor(
    private val repository: EnvironmentRepository
) {
    suspend operator fun invoke(timestamp: Long): SensorEvent? {
        return repository.getSensorEvent(timestamp)
    }
}
