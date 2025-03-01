package dk.alstroem.data.environmemt.local

import androidx.room.Database
import androidx.room.RoomDatabase
import dk.alstroem.data.environmemt.local.model.SensorEventEntity

@Database(
    entities = [SensorEventEntity::class],
    version = 1,
    exportSchema = false
)
abstract class EnvironmentDatabase : RoomDatabase() {
    abstract fun environmentDao(): EnvironmentDao
}
