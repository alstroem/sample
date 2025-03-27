package dk.alstroem.feature.weather.events

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dk.alstroem.domain.weather.usecase.GetWeatherSensorEventUseCase
import dk.alstroem.feature.weather.events.model.EventDetailsUiState
import dk.alstroem.feature.weather.navigation.EventDetails
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class EventDetailsViewModel(
    getSensorEvent: GetWeatherSensorEventUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val event = savedStateHandle.toRoute<EventDetails>()
    val uiState: StateFlow<EventDetailsUiState> = getSensorEvent(event.timestamp)
        .map { event ->
            if (event != null) {
                EventDetailsUiState.Success(data = event)
            } else {
                EventDetailsUiState.Error
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = EventDetailsUiState.Loading
        )
}
