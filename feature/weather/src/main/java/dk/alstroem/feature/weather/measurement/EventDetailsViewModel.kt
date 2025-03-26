package dk.alstroem.feature.weather.measurement

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.alstroem.domain.environment.usecase.GetSensorEventUseCase
import dk.alstroem.feature.weather.measurement.model.EventDetailsUiState
import dk.alstroem.sample.navigation.EventDetails
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventDetailsViewModel @Inject constructor(
    private val getSensorEvent: GetSensorEventUseCase,
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