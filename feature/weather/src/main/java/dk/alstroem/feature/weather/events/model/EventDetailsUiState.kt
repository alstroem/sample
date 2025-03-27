package dk.alstroem.feature.weather.events.model

import dk.alstroem.domain.weather.model.WeatherSensorEvent

sealed interface EventDetailsUiState {
    data object Loading : EventDetailsUiState
    data object Error : EventDetailsUiState
    data class Success(val data: WeatherSensorEvent) : EventDetailsUiState
}
