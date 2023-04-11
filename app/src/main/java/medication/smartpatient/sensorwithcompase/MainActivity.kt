package medication.smartpatient.sensorwithcompase

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build.VERSION_CODES.O
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import kotlin.text.Typography.degree

class MainActivity : AppCompatActivity(), SensorEventListener {

    lateinit var sensorManager: SensorManager
    lateinit var compassImage: ImageView
    var degreeStart = 0f
    lateinit var degreeTV: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        compassImage = findViewById(R.id.compass_image)
        degreeTV = findViewById(R.id.DegreeTV)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager


    }

    override fun onStop() {
        super.onStop()
        sensorManager.unregisterListener(this)
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(
            this,
            sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
            SensorManager.SENSOR_DELAY_GAME
        )
    }

    override fun onSensorChanged(event: SensorEvent) {

        var degree = Math.round(event.values[0])
        degreeTV.setText("Heading" + degree.toString() + "degree")

        var  rotateAnimation = RotateAnimation(degreeStart, degree.toFloat(),Animation.RELATIVE_TO_SELF,
        0.5f,Animation.RELATIVE_TO_SELF,0.5f)
        rotateAnimation.fillAfter = true
        rotateAnimation.duration = 210

        compassImage.startAnimation(rotateAnimation)
        degreeStart = -degree.toFloat()
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}