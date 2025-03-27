package dk.alstroem.feature.weather.overview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dk.alstroem.core.designsystem.theme.SampleTheme
import dk.alstroem.feature.weather.R
import dk.alstroem.feature.weather.overview.model.SensorScreenEvent
import dk.alstroem.feature.weather.overview.model.SensorUiState
import org.koin.androidx.compose.koinViewModel
import java.util.Locale

@Composable
fun OverviewScreen(
    modifier: Modifier = Modifier,
    viewModel: OverviewViewModel = koinViewModel()
) {
    val temperatureUiState by viewModel.temperatureUiState.collectAsStateWithLifecycle()
    val humidityUiState by viewModel.humidityUiState.collectAsStateWithLifecycle()

    OverviewContent(
        temperatureUiState = temperatureUiState,
        humidityUiState = humidityUiState,
        onClick = viewModel::onClickEvent,
        modifier = modifier
    )
}

@Composable
fun OverviewContent(
    temperatureUiState: SensorUiState<Float>,
    humidityUiState: SensorUiState<Int>,
    onClick: (SensorScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    LifecycleEventEffect(Lifecycle.Event.ON_PAUSE) {
        onClick(SensorScreenEvent.StopSensor)
    }

    ConstraintLayout(modifier = modifier
        .fillMaxSize()
        .padding(16.dp)) {
        val (values, buttons) = createRefs()

        SensorValues(
            temperatureUiState = temperatureUiState,
            humidityUiState = humidityUiState,
            modifier = Modifier.constrainAs(values) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                end.linkTo(parent.end)
                bottom.linkTo(buttons.top)
            }
        )

        ButtonRow(
            temperatureUiState = temperatureUiState,
            humidityUiState = humidityUiState,
            onClick = onClick,
            modifier = Modifier.constrainAs(buttons) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }
        )
    }
}

@Composable
fun SensorValues(
    temperatureUiState: SensorUiState<Float>,
    humidityUiState: SensorUiState<Int>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = stringResource(R.string.sensor_temperature_label),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = when (temperatureUiState) {
                SensorUiState.Idle -> "-"
                SensorUiState.NotAvailable -> stringResource(R.string.sensor_not_available_label)
                is SensorUiState.Collecting -> {
                    val value = String.format(Locale.getDefault(), "%.1f", temperatureUiState.value)
                    stringResource(R.string.sensor_temperature_value, value)
                }
            },
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Text(
            text = stringResource(R.string.sensor_humidity_label),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = when (humidityUiState) {
                SensorUiState.Idle -> "-"
                SensorUiState.NotAvailable -> stringResource(R.string.sensor_not_available_label)
                is SensorUiState.Collecting -> {
                    stringResource(R.string.sensor_humidity_value, humidityUiState.value)
                }
            },
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )
    }
}

@Composable
fun ButtonRow(
    temperatureUiState: SensorUiState<Float>,
    humidityUiState: SensorUiState<Int>,
    onClick: (SensorScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        val isCollectingData = temperatureUiState is SensorUiState.Collecting || humidityUiState is SensorUiState.Collecting
        Button(
            modifier = Modifier.weight(1f),
            onClick = {
                val event = if (isCollectingData) SensorScreenEvent.StopSensor else SensorScreenEvent.StartSensor
                onClick(event)
            }
        ) {
            val labelRes = if (isCollectingData) R.string.sensor_button_cancel_label else R.string.sensor_button_start_label
            Text(text = stringResource(labelRes))
        }

        Spacer(modifier = Modifier.width(16.dp))

        FilledTonalButton(
            modifier = Modifier.weight(1f),
            enabled = isCollectingData,
            onClick = { onClick(SensorScreenEvent.SaveSensorData(
                temperature = temperatureUiState.getOrNull(),
                humidity = humidityUiState.getOrNull()
            )) }
        ) {
            Text(text = stringResource(R.string.sensor_button_save_label))
        }
    }
}

@Preview
@Composable
private fun SensorScreenPreview() {
    SampleTheme {
        OverviewContent(
            temperatureUiState = SensorUiState.Collecting(21.2f),
            humidityUiState = SensorUiState.Collecting(46),
            onClick = { }
        )
    }
}
