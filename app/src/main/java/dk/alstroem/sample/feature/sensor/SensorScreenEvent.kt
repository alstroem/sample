package dk.alstroem.sample.feature.sensor

sealed class SensorScreenEvent {
    data object StartSensor : SensorScreenEvent()
    data object StopSensor : SensorScreenEvent()
    data object SaveSensorData : SensorScreenEvent()
}
