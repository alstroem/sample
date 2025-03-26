package dk.alstroem.feature.weather.measurement.model

import dk.alstroem.domain.environment.model.SensorEvent

data class EventDetailsUiState(
    val event: SensorEvent? = null
)
