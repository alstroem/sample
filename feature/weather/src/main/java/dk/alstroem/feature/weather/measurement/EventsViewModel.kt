package dk.alstroem.feature.weather.measurement

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dk.alstroem.domain.weather.usecase.GetAllWeatherSensorEventsUseCase
import dk.alstroem.feature.weather.measurement.model.EventsUiState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class EventsViewModel (
    private val getSensorEvents: GetAllWeatherSensorEventsUseCase
) : ViewModel() {

    var uiSate by mutableStateOf(EventsUiState())
        private set

    init {
        collectSensorEvents()
    }

    private fun collectSensorEvents() {
        viewModelScope.launch {
            getSensorEvents().collectLatest { events ->
                uiSate = uiSate.copy(events = events)
            }
        }
    }
}
