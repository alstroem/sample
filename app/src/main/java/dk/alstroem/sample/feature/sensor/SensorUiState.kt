package dk.alstroem.sample.feature.sensor

data class SensorUiState(
    val collectSensorData: Boolean = false,
    val temperature: Float = 0.0f,
    val humidity: Int = 0
)
