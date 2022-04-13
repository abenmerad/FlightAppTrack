package com.example.flighttrackapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import com.example.flighttrackapp.databinding.ActivityFlightTrackMainBinding
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityFlightTrackMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val errorMessage : String? = intent.getStringExtra("errorMessage")
        println("errormessage: $errorMessage")

        if (!errorMessage.isNullOrEmpty()) binding.fieldErrorMessage.text = errorMessage
        val btnFlight = findViewById<Button>(R.id.btSubmit)
        btnFlight.setOnClickListener {
            binding.fieldErrorMessage.text = ""
            val intent = Intent(this, FlightActivity::class.java)
            intent.putExtra("flightNumber", binding.fieldFlightNumber.text.toString())
            intent.putExtra("flightDate", formatDatePickerIntoString(binding.fieldFlightDate))
            this.startActivity(intent);
        }
    }

    private fun formatDatePickerIntoString(datePicker: DatePicker): String {
        val year = datePicker.year
        val month = datePicker.month
        val day = datePicker.dayOfMonth

        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)

        return  SimpleDateFormat("yyyy-MM-dd").format(calendar.time)
    }
}