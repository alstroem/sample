package dk.alstroem.data.environmemt.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SensorEventEntity(
    @PrimaryKey val timestamp: Long,
    val temperature: Float,
    val humidity: Int
)
