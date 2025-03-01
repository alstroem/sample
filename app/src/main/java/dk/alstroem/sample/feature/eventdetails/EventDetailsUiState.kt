package dk.alstroem.sample.feature.eventdetails

import dk.alstroem.domain.environment.model.SensorEvent

data class EventDetailsUiState(
    val event: SensorEvent? = null
)
