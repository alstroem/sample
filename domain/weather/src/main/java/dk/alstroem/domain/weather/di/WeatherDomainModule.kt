package dk.alstroem.domain.weather.di

import dk.alstroem.domain.weather.usecase.ClearWeatherSensorEventsUseCase
import dk.alstroem.domain.weather.usecase.GetAllWeatherSensorEventsUseCase
import dk.alstroem.domain.weather.usecase.GetWeatherSensorEventUseCase
import dk.alstroem.domain.weather.usecase.SetWeatherSensorEventUseCase
import org.koin.dsl.module

val weatherDomainModule = module {
    factory { ClearWeatherSensorEventsUseCase(get()) }
    factory { GetAllWeatherSensorEventsUseCase(get()) }
    factory { GetWeatherSensorEventUseCase(get()) }
    factory { SetWeatherSensorEventUseCase(get()) }
}
