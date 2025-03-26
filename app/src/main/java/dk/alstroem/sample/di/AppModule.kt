package dk.alstroem.sample.di

import dk.alstroem.feature.weather.di.weatherFeatureModule
import org.koin.dsl.module

val appModule = module {
    includes(weatherFeatureModule)
}
