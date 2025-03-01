package dk.alstroem.sample.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import kotlinx.serialization.Serializable

@Serializable object Sensor

// Nested graph Rout
@Serializable object EventOverview

// Routes in nested graph
@Serializable object Events
@Serializable data class EventDetails(val timestamp: Long)

val topLeverRoutes = listOf(
    TopLevelRoute("Sensor", Sensor, Icons.Default.Home),
    TopLevelRoute("Events", EventOverview, Icons.Default.Star)
)
