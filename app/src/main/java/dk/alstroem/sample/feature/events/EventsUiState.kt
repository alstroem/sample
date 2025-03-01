package dk.alstroem.sample.feature.events

import dk.alstroem.domain.environment.model.SensorEvent

data class EventsUiState(
    val events: List<SensorEvent> = emptyList()
)
