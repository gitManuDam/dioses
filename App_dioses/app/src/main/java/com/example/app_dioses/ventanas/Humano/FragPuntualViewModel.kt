package com.example.app_dioses.ventanas.Humano

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_dioses.api.PruebasNetwork
import com.example.app_dioses.modelo.Humano
import com.example.app_dioses.modelo.PruebaHumanoRV
import com.example.app_dioses.modelo.PruebaPuntales
import kotlinx.coroutines.launch

class FragPuntualViewModel : ViewModel() {
    private val _pruebaPuntual = MutableLiveData<PruebaPuntales>()
    val pruebaPuntual: MutableLiveData<PruebaPuntales> = _pruebaPuntual

    fun obtenerPruebaPuntual(idPrueba: Int) {
        viewModelScope.launch {

            val response = PruebasNetwork.retrofit.obtenerPruebaPuntual(idPrueba)
            if (response.isSuccessful) {
                Log.d("Manuel", "FragPuntualViewModel. obtenerPruebaPuntual: ${response.body()}")
                _pruebaPuntual.value = response.body()
            }

        }
    }



}