package dk.alstroem.feature.weather.di

import dk.alstroem.core.sensor.WeatherSensor
import dk.alstroem.data.weather.di.weatherDataModule
import dk.alstroem.domain.weather.di.weatherDomainModule
import dk.alstroem.feature.weather.measurement.EventDetailsViewModel
import dk.alstroem.feature.weather.measurement.EventsViewModel
import dk.alstroem.feature.weather.overview.OverviewViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val weatherFeatureModule = module {
    includes(weatherDomainModule, weatherDataModule)

    singleOf(::WeatherSensor)

    viewModelOf(::OverviewViewModel)

    viewModelOf(::EventDetailsViewModel)
    viewModelOf(::EventsViewModel)
}
