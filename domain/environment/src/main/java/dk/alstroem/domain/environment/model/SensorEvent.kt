package dk.alstroem.domain.environment.model

data class SensorEvent(
    val timestamp: Long,
    val temperature: Float,
    val humidity: Int
)
