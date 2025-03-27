package dk.alstroem.feature.weather.events.model

import dk.alstroem.domain.weather.model.WeatherSensorEvent

sealed interface EventsUiState {
    data object Loading : EventsUiState
    data object Empty : EventsUiState
    data class Success(val events: List<WeatherSensorEvent> = emptyList()) : EventsUiState
}
