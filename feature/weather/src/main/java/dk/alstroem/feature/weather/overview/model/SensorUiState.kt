package dk.alstroem.feature.weather.overview.model

sealed class SensorUiState<out T> {
    data object NotAvailable : SensorUiState<Nothing>()
    data object Idle : SensorUiState<Nothing>()
    data class Collecting<T>(val value: T) : SensorUiState<T>()

    fun getOrNull(): T? = if (this is Collecting) value else null
}
