package dk.alstroem.data.weather.local

import androidx.room.Database
import androidx.room.RoomDatabase
import dk.alstroem.data.weather.local.model.WeatherSensorEventEntity

@Database(
    entities = [WeatherSensorEventEntity::class],
    version = 1,
    exportSchema = false
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherSensorDao(): WeatherSensorDao
}
