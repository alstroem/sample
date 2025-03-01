package dk.alstroem.domain.environment.usecase

import dk.alstroem.domain.environment.EnvironmentRepository
import javax.inject.Inject

class ClearSensorEventsUseCase @Inject constructor(
    private val repository: EnvironmentRepository
) {
    suspend operator fun invoke() {
        repository.clearSensorEvents()
    }
}
