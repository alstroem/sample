package dk.alstroem.core.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import timber.log.Timber

class WeatherSensor(context: Context) {

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private val temperatureSensor: Sensor? =
        sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
    private val humiditySensor: Sensor? =
        sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY)

    fun temperatureFlow() = callbackFlow {
        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                if (event == null) return
                trySend(event.values[0])
            }

            override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
                Timber.d("Temperature accuracy changed: $p1")
            }
        }

        val temperatureSensorRegistered = sensorManager.registerListener(
            listener,
            temperatureSensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )

        Timber.d("Temperature sensor registered: $temperatureSensorRegistered")

        awaitClose { sensorManager.unregisterListener(listener) }
    }

    fun humidityFlow() = callbackFlow {
        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                if (event == null) return
                trySend(event.values[0].toInt())
            }

            override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
                Timber.d("Temperature accuracy changed: $p1")
            }
        }

        val humiditySensorRegistered = sensorManager.registerListener(
            listener,
            humiditySensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )

        Timber.d("Temperature sensor registered: $humiditySensorRegistered")

        awaitClose { sensorManager.unregisterListener(listener) }
    }
}