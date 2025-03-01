package dk.alstroem.sample.feature.events

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.alstroem.domain.environment.usecase.GetSensorEventsUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(
    private val getSensorEvents: GetSensorEventsUseCase
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
