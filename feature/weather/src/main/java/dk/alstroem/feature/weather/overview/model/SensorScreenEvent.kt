package dk.alstroem.feature.weather.overview.model

sealed interface SensorScreenEvent {
    data object StartSensor : SensorScreenEvent
    data object StopSensor : SensorScreenEvent
    data class SaveSensorData(
        val temperature: Float?,
        val humidity: Int?
    ) : SensorScreenEvent
}
