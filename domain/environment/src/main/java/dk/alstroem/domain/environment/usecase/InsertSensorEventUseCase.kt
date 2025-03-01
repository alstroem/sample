package dk.alstroem.domain.environment.usecase

import dk.alstroem.domain.environment.EnvironmentRepository
import dk.alstroem.domain.environment.model.SensorEvent
import javax.inject.Inject

class InsertSensorEventUseCase @Inject constructor(
    private val repository: EnvironmentRepository
) {
    suspend operator fun invoke(event: SensorEvent) {
        repository.insertSensorEvent(event)
    }
}
