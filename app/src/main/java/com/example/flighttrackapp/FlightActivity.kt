package com.example.flighttrackapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.flighttrackapp.databinding.ActivityFlightInformationBinding
import com.squareup.picasso.Picasso


class FlightActivity : AppCompatActivity() {
    private val binding by lazy { ActivityFlightInformationBinding.inflate(layoutInflater)}
    private val model by lazy { ViewModelProvider(this).get(FlightViewModel::class.java)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val flightNumber = intent.getStringExtra("flightNumber")
        val flightDate = intent.getStringExtra("flightDate")
        model.loadData(flightNumber!!, flightDate!!)

        model.data.observe(this) {
            binding.fieldAirline.text = it?.airline?.name ?: "-"
            binding.fieldDistanceKm.text = it?.greatCircleDistance?.km?.toString() ?: "-"
            binding.fieldCallSign.text = it?.callSign ?: "-"
            binding.fieldFlightNumber.text = it?.number ?: "-"
            binding.fieldFlightStatus.text = it?.status ?: "-"

            binding.fieldAircraftModel.text = it?.aircraft?.model ?: "-"
            binding.fieldRegNumberAircraft.text = it?.aircraft?.reg ?: "-"
            Picasso.get().load(it?.aircraft?.image?.url).into(binding.aircraftImageView)

            val municipalityNameDeparture : String? = it?.departure?.airport?.municipalityName
            val countryCodeDeparture : String? = it?.departure?.airport?.countryCode
            val terminalDeparture : String? = it?.arrival?.terminal ?: ""
            val checkInDeskDeparture : String? = it?.arrival?.checkInDesk ?: ""
            binding.fieldAirportDeparture.text = it?.departure?.airport?.shortName ?: "-"
            binding.fieldAirportICAODeparture.text = it?.departure?.airport?.icao ?: "-"
            binding.fieldAirportLocalisationDeparture.text =
                if (municipalityNameDeparture === null || countryCodeDeparture === null) "-" else "$municipalityNameDeparture, $countryCodeDeparture"
            binding.fieldScheduledTimeDeparture.text = it?.departure?.scheduledTimeLocal ?: "-"
            binding.fieldTerminalDeparture.text =
                if (terminalDeparture === "" && checkInDeskDeparture === "") "-" else terminalDeparture + checkInDeskDeparture

            val municipalityNameArrival : String? = it?.arrival?.airport?.municipalityName
            val countryCodeArrival : String? = it?.arrival?.airport?.countryCode
            val terminalArrival : String? = it?.arrival?.terminal ?: ""
            val checkInDeskArrival : String? = it?.arrival?.checkInDesk ?: ""
            binding.fieldAirportArrival.text = it?.arrival?.airport?.shortName ?: "-"
            binding.fieldAirportICAOArrival.text = it?.arrival?.airport?.icao ?: "-"
            binding.fieldAirportLocalisationArrival.text =
                if (municipalityNameArrival === null || countryCodeArrival === null) "-" else "$municipalityNameArrival, $countryCodeArrival"
            binding.fieldScheduledTimeArrival.text = it?.arrival?.scheduledTimeLocal ?: "-"
            binding.fieldTerminalArrival.text =
                if (terminalArrival === "" && checkInDeskArrival === "") "-" else terminalArrival + checkInDeskArrival
        }

        model.errorMessage.observe(this) {
            if (it != null) {
                val intent = Intent(this, MainActivity::class.java)

                if (it == "404") {
                    intent.putExtra("errorMessage", "Aucun resultat pour ce numéro de vol et cette date. Veuillez ressayer.")
                } else if (it == "400") {
                    intent.putExtra("errorMessage", "Les paramètres sont mal formées. Veuillez recommencer.")
                }
                this.startActivity(intent);
            }
        }

        model.threadRunning.observe(this) {
            binding.progressBarLoadingDataFlight.isGone = !it
            binding.aircraftImageView.isVisible = !it
        }
    }
}