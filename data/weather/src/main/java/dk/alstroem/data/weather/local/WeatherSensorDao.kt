package dk.alstroem.data.weather.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import dk.alstroem.data.weather.local.model.WeatherSensorEventEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherSensorDao {

    @Query("SELECT * FROM WeatherSensorEventEntity")
    fun getSensorEvents(): Flow<List<WeatherSensorEventEntity>>

    @Query("SELECT * FROM WeatherSensorEventEntity WHERE timestamp = :timestamp")
    suspend fun getSensorEvent(timestamp: Long): WeatherSensorEventEntity?

    @Insert
    suspend fun insertSensorEvent(event: WeatherSensorEventEntity)

    @Query("DELETE FROM WeatherSensorEventEntity")
    suspend fun deleteEvents()
}
