package dk.alstroem.domain.weather.model

data class WeatherSensorEvent(
    val timestamp: Long,
    val temperature: Float?,
    val humidity: Int?
)
