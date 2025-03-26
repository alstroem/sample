package dk.alstroem.feature.weather.overview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dk.alstroem.core.sensor.WeatherSensor
import dk.alstroem.domain.weather.model.WeatherSensorEvent
import dk.alstroem.domain.weather.usecase.SetWeatherSensorEventUseCase
import dk.alstroem.feature.weather.overview.model.SensorScreenEvent
import dk.alstroem.feature.weather.overview.model.SensorUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class OverviewViewModel(
    private val weatherSensor: WeatherSensor,
    private val insertSensorEvent: SetWeatherSensorEventUseCase
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
            weatherSensor.temperatureFlow().collectLatest { temperature ->
                uiState = uiState.copy(temperature = temperature)
            }
        }

        humidityJob = viewModelScope.launch {
            weatherSensor.humidityFlow().collectLatest { humidity ->
                uiState = uiState.copy(humidity = humidity)
            }
        }
    }

    private fun unregisterSensors() {
        temperatureJob?.cancel()
        humidityJob?.cancel()
        uiState = uiState.copy(
            collectSensorData = false,
            temperature = 0.0f,
            humidity = 0
        )
    }

    private fun saveSensorData() {
        viewModelScope.launch {
            with(uiState) {
                val event = WeatherSensorEvent(
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
