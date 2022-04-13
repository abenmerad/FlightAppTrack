package com.example.flighttrackapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.concurrent.thread


class FlightViewModel : ViewModel() {
    val data : MutableLiveData<FlightBean?> = MutableLiveData()
    val errorMessage : MutableLiveData<String?> = MutableLiveData()
    val threadRunning : MutableLiveData<Boolean> = MutableLiveData(false)

    fun loadData(flightNumber: String, flightDate: String){
        errorMessage.postValue(null)
        data.postValue(null)
        threadRunning.postValue(true)

        thread {
            try {
                data.postValue(RequestUtils.loadFlightDatas(flightNumber, flightDate))
            } catch (e:Exception) {
                e.printStackTrace()
                errorMessage.postValue(e.message)
            }
            threadRunning.postValue(false)
        }
    }
}