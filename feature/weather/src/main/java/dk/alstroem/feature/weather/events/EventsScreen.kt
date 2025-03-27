package dk.alstroem.feature.weather.events

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
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
import dk.alstroem.feature.weather.events.model.EventsUiState
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun EventsScreen(
    openEventDetails: (timestamp: Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EventsViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    EventsContent(
        uiState = uiState,
        openEventDetails = openEventDetails,
        modifier = modifier
    )
}

@Composable
fun EventsContent(
    uiState: EventsUiState,
    openEventDetails: (timestamp: Long) -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiState) {
        EventsUiState.Loading -> EventsLoading(modifier = modifier)
        EventsUiState.Empty -> EventsEmpty(modifier = modifier)
        is EventsUiState.Success -> EventsList(
            events = uiState.events,
            openEventDetails = openEventDetails,
            modifier = modifier
        )
    }
}

@Composable
fun EventsLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun EventsEmpty(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.sensor_events_empty_label),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun EventsList(
    events: List<WeatherSensorEvent>,
    openEventDetails: (timestamp: Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val sdf = remember { SimpleDateFormat("EEEE d. MMMM yyyy hh:mm:ss", Locale.getDefault()) }
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(events) { event ->
            EventItem(
                date = sdf.format(Date(event.timestamp)),
                onClick = { openEventDetails(event.timestamp) }
            )
            HorizontalDivider()
        }
    }
}

@Composable
fun EventItem(
    date: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 54.dp)
            .clickable { onClick() }
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = date)
    }
}

@Preview
@Composable
private fun EventsListPreview() {
    SampleTheme {
        EventsContent(
            uiState = EventsUiState.Success(
                events = listOf(WeatherSensorEvent(0, 22.3f, 75))
            ),
            openEventDetails = {}
        )
    }
}

@Preview
@Composable
private fun EventsLoadingPreview() {
    SampleTheme {
        EventsContent(
            uiState = EventsUiState.Loading,
            openEventDetails = { }
        )
    }
}

@Preview
@Composable
private fun EventsEmptyPreview() {
    SampleTheme {
        EventsContent(
            uiState = EventsUiState.Empty,
            openEventDetails = { }
        )
    }
}
