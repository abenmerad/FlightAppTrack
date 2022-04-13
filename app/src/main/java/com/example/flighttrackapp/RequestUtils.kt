package com.example.flighttrackapp

import android.os.SystemClock
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.reflect.Type


private val client = OkHttpClient()
private val gson = Gson()

const val FLIGHT_API_URL = "https://aerodatabox.p.rapidapi.com/flights/%s/%s?withAircraftImage=true&withLocation=false"

class RequestUtils {

    companion object {

        fun loadFlightDatas(flightNumber: String, flightDate: String): FlightBean {
            SystemClock.sleep(3000)
            val json = getFlightDatas(FLIGHT_API_URL.format(flightNumber, flightDate))
            val collectionType: Type = object : TypeToken<List<FlightBean?>?>() {}.type
            val lcs: List<FlightBean> = Gson()
                .fromJson(json, collectionType) as List<FlightBean>
            return lcs[0]
        }

        private fun getFlightDatas (url : String): String {
            println("url : $url")
            val request = Request.Builder()
                .url(url)
                .get()
                .addHeader("X-RapidAPI-Host", "aerodatabox.p.rapidapi.com")
                .addHeader("X-RapidAPI-Key", "04f2a0dbebmshfd59830c97514d0p1470ebjsn14b46f506278")
                .build()

            return client.newCall(request).execute().use {
                if (!it.isSuccessful){
                    throw Exception("${it.code}")
                }
                it.body?.string() ?: ""
            }
        }
    }
}