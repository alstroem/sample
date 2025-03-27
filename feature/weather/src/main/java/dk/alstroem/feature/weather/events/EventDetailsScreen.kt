package dk.alstroem.feature.weather.events

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dk.alstroem.core.designsystem.theme.SampleTheme
import dk.alstroem.domain.weather.model.WeatherSensorEvent
import dk.alstroem.feature.weather.R
import dk.alstroem.feature.weather.events.model.EventDetailsUiState
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun EventDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: EventDetailsViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    EventDetailsContent(
        uiState = uiState,
        modifier = modifier
    )
}

@Composable
fun EventDetailsContent(
    uiState: EventDetailsUiState,
    modifier: Modifier = Modifier
) {
    when(uiState) {
        EventDetailsUiState.Error -> { }
        EventDetailsUiState.Loading -> { }
        is EventDetailsUiState.Success -> {
            with(uiState.data) {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    val sdf = remember { SimpleDateFormat("yyyy-MM-dd \nhh:mm:ss", Locale.getDefault()) }

                    Text(
                        text = stringResource(R.string.sensor_event_details_timestamp),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Text(
                        text = sdf.format(Date(timestamp)),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.displayMedium,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    Text(
                        text = stringResource(R.string.sensor_temperature_label),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    val formattedTemperature = temperature?.let {
                        String.format(Locale.getDefault(), "%.1f", it)
                    } ?: "-"

                    Text(
                        text = stringResource(R.string.sensor_temperature_value, formattedTemperature),
                        style = MaterialTheme.typography.displayMedium,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    Text(
                        text = stringResource(R.string.sensor_humidity_label),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Text(
                        text = stringResource(R.string.sensor_humidity_value, humidity ?: "-"),
                        style = MaterialTheme.typography.displayMedium,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun EventDetailsContentPreview() {
    SampleTheme {
        EventDetailsContent(
            uiState = EventDetailsUiState.Success(
                data = WeatherSensorEvent(
                    timestamp = System.currentTimeMillis(),
                    temperature = 22.3f,
                    humidity = 75,
                )
            )
        )
    }
}
