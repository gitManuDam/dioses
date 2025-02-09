package com.example.app_dioses.ventanas.Humano

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_dioses.api.PruebasNetwork
import com.example.app_dioses.api.UsuarioNetwork
import com.example.app_dioses.modelo.Humano
import com.example.app_dioses.modelo.PruebaHumanoRV
import kotlinx.coroutines.launch
import retrofit2.Response

class FragPruebasPendientesViewModel : ViewModel() {

    private val _pruebasPendientes = MutableLiveData<List<PruebaHumanoRV>>()
    val pruebasPendientes: MutableLiveData<List<PruebaHumanoRV>> = _pruebasPendientes

    fun obtenerPruebasPendientes(idHumano: Int) {
        Log.e("Manuel", "obtenerHumanosProtegidos: FragProtegidosViewModel")
        viewModelScope.launch {
            val response = PruebasNetwork.retrofit.obtenerPruebasPendientes(idHumano)
            _pruebasPendientes.value = response.body()
        }
    }
}