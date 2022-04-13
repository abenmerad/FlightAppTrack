package com.example.flighttrackapp

import java.time.LocalDateTime

data class FlightBean (
    var greatCircleDistance: DistanceBean,
    var departure: PointBean,
    var arrival: PointBean,
    var number: String,
    var callSign: String,
    var status: String,
    var aircraft: AircraftBean,
    var airline: AirlineBean
)

data class DistanceBean (
    var km: Float,
    var mile: Float,
    var nm: Float
)

data class PointBean (
    var airport: AirportBean,
    var scheduledTimeLocal: String?,
    var terminal: String?,
    var runway: String?,
    var checkInDesk: String?
)

data class AirportBean (
    var icao: String,
    var iata: String,
    var shortName: String,
    var municipalityName: String,
    var countryCode: String
)

data class AircraftBean (
    var reg: String,
    var model: String,
    var image: AircraftImageBean
)

data class AirlineBean (
    var name: String
)

data class AircraftImageBean (
    var url: String,
    var title: String,
    var description: String
)