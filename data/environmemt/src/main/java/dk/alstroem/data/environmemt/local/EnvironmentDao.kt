package dk.alstroem.data.environmemt.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import dk.alstroem.data.environmemt.local.model.SensorEventEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EnvironmentDao {

    @Query("SELECT * FROM SensorEventEntity")
    fun getSensorEvents(): Flow<List<SensorEventEntity>>

    @Query("SELECT * FROM SensorEventEntity WHERE timestamp = :timestamp")
    suspend fun getSensorEvent(timestamp: Long): SensorEventEntity?

    @Insert
    suspend fun insertSensorEvent(event: SensorEventEntity)

    @Query("DELETE FROM SensorEventEntity")
    suspend fun deleteEvents()
}
