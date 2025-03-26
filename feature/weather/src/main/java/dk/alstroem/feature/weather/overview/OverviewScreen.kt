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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import dk.alstroem.core.designsystem.theme.SampleTheme
import dk.alstroem.feature.weather.R
import dk.alstroem.feature.weather.overview.model.SensorScreenEvent
import dk.alstroem.feature.weather.overview.model.SensorUiState
import java.util.Locale

@Composable
fun OverviewScreen(
    uiState: SensorUiState,
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
            collectSensorData = uiState.collectSensorData,
            temperature = uiState.temperature,
            humidity = uiState.humidity,
            modifier = Modifier.constrainAs(values) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                end.linkTo(parent.end)
                bottom.linkTo(buttons.top)
            }
        )

        ButtonRow(
            collectSensorData = uiState.collectSensorData,
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
    collectSensorData: Boolean,
    temperature: Float,
    humidity: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = stringResource(R.string.sensor_temperature_label),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = if (collectSensorData) {
                val value = String.format(Locale.getDefault(), "%.1f", temperature)
                stringResource(R.string.sensor_temperature_value, value)
            } else "-",
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Text(
            text = stringResource(R.string.sensor_humidity_label),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = if (collectSensorData) {
                stringResource(R.string.sensor_humidity_value, humidity)
            } else "-",
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )
    }
}

@Composable
fun ButtonRow(
    collectSensorData: Boolean,
    onClick: (SensorScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Button(
            modifier = Modifier.weight(1f),
            onClick = {
                val event = if (collectSensorData) SensorScreenEvent.StopSensor else SensorScreenEvent.StartSensor
                onClick(event)
            }
        ) {
            val labelRes = if (collectSensorData) R.string.sensor_button_cancel_label else R.string.sensor_button_start_label
            Text(text = stringResource(labelRes))
        }

        Spacer(modifier = Modifier.width(16.dp))

        FilledTonalButton(
            modifier = Modifier.weight(1f),
            enabled = collectSensorData,
            onClick = { onClick(SensorScreenEvent.SaveSensorData) }
        ) {
            Text(text = stringResource(R.string.sensor_button_save_label))
        }
    }
}

@Preview
@Composable
private fun SensorScreenPreview() {
    SampleTheme {
        OverviewScreen(
            uiState = SensorUiState(
                temperature = 21.2f,
                humidity = 46
            ),
            onClick = { }
        )
    }
}
