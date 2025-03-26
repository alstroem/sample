package dk.alstroem.sample.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import dk.alstroem.feature.weather.navigation.EventOverview
import dk.alstroem.feature.weather.navigation.Sensor

val topLeverRoutes = listOf(
    TopLevelRoute("Sensor", Sensor, Icons.Default.Home),
    TopLevelRoute("Events", EventOverview, Icons.Default.Star)
)
