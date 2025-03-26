package dk.alstroem.feature.weather.overview.model

sealed class SensorScreenEvent {
    data object StartSensor : SensorScreenEvent()
    data object StopSensor : SensorScreenEvent()
    data object SaveSensorData : SensorScreenEvent()
}
