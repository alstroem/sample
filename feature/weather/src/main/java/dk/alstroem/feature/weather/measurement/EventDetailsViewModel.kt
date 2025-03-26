package dk.alstroem.feature.weather.measurement

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dk.alstroem.domain.weather.usecase.GetWeatherSensorEventUseCase
import dk.alstroem.feature.weather.measurement.model.EventDetailsUiState
import dk.alstroem.feature.weather.navigation.EventDetails
import kotlinx.coroutines.launch

class EventDetailsViewModel(
    private val getSensorEvent: GetWeatherSensorEventUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val event = savedStateHandle.toRoute<EventDetails>()

    var uiState by mutableStateOf(EventDetailsUiState())
        private set


    init {
        fetchSensorEvent(event.timestamp)
    }

    private fun fetchSensorEvent(timestamp: Long) {
        viewModelScope.launch {
            val event = getSensorEvent(timestamp)
            uiState = uiState.copy(event = event)
        }
    }
}