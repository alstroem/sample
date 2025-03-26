package dk.alstroem.feature.weather.navigation

import kotlinx.serialization.Serializable

@Serializable object Sensor

// Nested graph Route
@Serializable object EventOverview

// Routes in nested graph
@Serializable object Events
@Serializable data class EventDetails(val timestamp: Long)
