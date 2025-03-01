package dk.alstroem.sample.feature.sensor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.alstroem.domain.environment.model.SensorEvent
import dk.alstroem.domain.environment.usecase.InsertSensorEventUseCase
import dk.alstroem.sample.EnvironmentSensor
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SensorViewModel @Inject constructor(
    private val environmentSensor: EnvironmentSensor,
    private val insertSensorEvent: InsertSensorEventUseCase
) : ViewModel() {

    private var temperatureJob: Job? = null
    private var humidityJob: Job? = null

    var uiState by mutableStateOf(SensorUiState())
        private set

    fun onClickEvent(event: SensorScreenEvent) {
        when (event) {
            SensorScreenEvent.StartSensor -> registerSensors()
            SensorScreenEvent.StopSensor -> unregisterSensors()
            SensorScreenEvent.SaveSensorData -> saveSensorData()
        }
    }

    private fun registerSensors() {
        uiState = uiState.copy(collectSensorData = true)
        temperatureJob = viewModelScope.launch {
            environmentSensor.temperatureFlow().collectLatest { temperature ->
                uiState = uiState.copy(temperature = temperature)
            }
        }

        humidityJob = viewModelScope.launch {
            environmentSensor.humidityFlow().collectLatest { humidity ->
                uiState = uiState.copy(humidity = humidity)
            }
        }
    }

    private fun unregisterSensors() {
        temperatureJob?.cancel()
        uiState = uiState.copy(
            collectSensorData = false,
            temperature = 0.0f,
            humidity = 0
        )
    }

    private fun saveSensorData() {
        viewModelScope.launch {
            with(uiState) {
                val event = SensorEvent(
                    timestamp = System.currentTimeMillis(),
                    temperature = temperature,
                    humidity = humidity
                )

                insertSensorEvent(event)
                unregisterSensors()
            }
        }
    }
}
