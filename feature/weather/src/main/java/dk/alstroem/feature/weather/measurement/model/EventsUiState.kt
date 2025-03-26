package dk.alstroem.feature.weather.measurement.model

import dk.alstroem.domain.weather.model.WeatherSensorEvent

data class EventsUiState(
    val events: List<WeatherSensorEvent> = emptyList()
)
