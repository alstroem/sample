package dk.alstroem.data.weather.di

import androidx.room.Room
import dk.alstroem.data.weather.WeatherRepositoryImpl
import dk.alstroem.data.weather.local.WeatherDatabase
import dk.alstroem.domain.weather.WeatherRepository
import org.koin.dsl.module

val weatherDataModule = module {
    single {
        Room.databaseBuilder(
            context = get(),
            klass = WeatherDatabase::class.java,
            name = "weather_database"
        ).build()
    }

    single {
        val database = get<WeatherDatabase>()
        database.weatherSensorDao()
    }

    single<WeatherRepository> { WeatherRepositoryImpl(get()) }
}
