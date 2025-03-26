package dk.alstroem.feature.weather.measurement

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dk.alstroem.feature.weather.measurement.model.EventsUiState
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun EventsScreen(
    uiState: EventsUiState,
    openEventDetails: (timestamp: Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val sdf = remember { SimpleDateFormat("EEEE d. MMMM yyyy hh:mm:ss", Locale.getDefault()) }

    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(uiState.events) { event ->
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
private fun OverviewScreenPreview() {
    dk.alstroem.core.designsystem.theme.SampleTheme {
        EventsScreen(
            uiState = EventsUiState(),
            openEventDetails = {}
        )
    }
}
