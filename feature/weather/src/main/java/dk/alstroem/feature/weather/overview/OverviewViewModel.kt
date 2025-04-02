package dk.alstroem.feature.weather.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dk.alstroem.core.sensor.WeatherSensor
import dk.alstroem.domain.weather.model.WeatherSensorEvent
import dk.alstroem.domain.weather.usecase.SetWeatherSensorEventUseCase
import dk.alstroem.feature.weather.overview.model.SensorScreenEvent
import dk.alstroem.feature.weather.overview.model.SensorUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OverviewViewModel(
    private val weatherSensor: WeatherSensor,
    private val insertSensorEvent: SetWeatherSensorEventUseCase
) : ViewModel() {

    private var temperatureJob: Job? = null
    private var humidityJob: Job? = null

    private val _temperatureUiState = MutableStateFlow<SensorUiState<Float>>(SensorUiState.Idle)
    val temperatureUiState: StateFlow<SensorUiState<Float>> = _temperatureUiState.asStateFlow()

    private val _humidityUiState = MutableStateFlow<SensorUiState<Int>>(SensorUiState.Idle)
    val humidityUiState: StateFlow<SensorUiState<Int>> = _humidityUiState.asStateFlow()

    fun onClickEvent(event: SensorScreenEvent) {
        when (event) {
            SensorScreenEvent.StartSensor -> registerSensors()
            SensorScreenEvent.StopSensor -> unregisterSensors()
            is SensorScreenEvent.SaveSensorData -> saveSensorData(event)
        }
    }

    private fun registerSensors() {
        temperatureJob = viewModelScope.launch {
            weatherSensor.temperatureFlow().collectLatest { temperature ->
                val state = temperature.fold(
                    onSuccess = { SensorUiState.Collecting(it) },
                    onFailure = { SensorUiState.NotAvailable }
                )

                _temperatureUiState.update { state }
            }
        }

        humidityJob = viewModelScope.launch {
            weatherSensor.humidityFlow().collectLatest { humidity ->
                val state = humidity.fold(
                    onSuccess = { SensorUiState.Collecting(it) },
                    onFailure = { SensorUiState.NotAvailable }
                )

                _humidityUiState.update { state }
            }
        }
    }

    private fun unregisterSensors() {
        temperatureJob?.cancel()
        humidityJob?.cancel()
        _temperatureUiState.update { SensorUiState.Idle }
        _humidityUiState.update { SensorUiState.Idle }
    }

    private fun saveSensorData(event: SensorScreenEvent.SaveSensorData) {
        viewModelScope.launch {
            val sensorEvent = WeatherSensorEvent(
                timestamp = System.currentTimeMillis(),
                temperature = event.temperature,
                humidity = event.humidity
            )

            insertSensorEvent(sensorEvent)
            unregisterSensors()
        }
    }
}
