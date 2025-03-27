package dk.alstroem.feature.weather.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dk.alstroem.domain.weather.usecase.GetAllWeatherSensorEventsUseCase
import dk.alstroem.feature.weather.events.model.EventsUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class EventsViewModel (
    getSensorEvents: GetAllWeatherSensorEventsUseCase
) : ViewModel() {
    val uiState: StateFlow<EventsUiState> = getSensorEvents()
        .map {
            if (it.isEmpty()) EventsUiState.Empty else EventsUiState.Success(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = EventsUiState.Loading
        )
}
