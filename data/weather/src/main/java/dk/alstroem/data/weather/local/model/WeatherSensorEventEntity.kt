package dk.alstroem.data.weather.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherSensorEventEntity(
    @PrimaryKey val timestamp: Long,
    val temperature: Float?,
    val humidity: Int?
)
