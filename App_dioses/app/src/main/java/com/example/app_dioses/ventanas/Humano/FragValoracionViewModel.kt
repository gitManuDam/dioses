package com.example.app_dioses.ventanas.Humano

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_dioses.api.PruebasNetwork
import com.example.app_dioses.api.UsuarioNetwork
import com.example.app_dioses.modelo.Actualizacion
import com.example.app_dioses.modelo.Humano
import com.example.app_dioses.modelo.PreguntaAfinidad
import com.example.app_dioses.modelo.PreguntaEleccion
import kotlinx.coroutines.launch

class FragValoracionViewModel : ViewModel() {
    private val _preguntaAfinidad = MutableLiveData<PreguntaAfinidad>()
    val preguntaAfinidad: MutableLiveData<PreguntaAfinidad> = _preguntaAfinidad

    private val _isPruebaAct = MutableLiveData<Boolean>()
    val isPruebaAct: MutableLiveData<Boolean> = _isPruebaAct

    fun obtenerPrueba(idPrueba: Int) {
        viewModelScope.launch {

            val response = PruebasNetwork.retrofit.obtenerPreguntaAfinidad(idPrueba)
            if (response.isSuccessful) {
                Log.d("Manuel", "FragPuntualViewModel. obtenerPruebaPuntual: ${response.body()}")
                _preguntaAfinidad.value = response.body()
            }

        }
    }

    fun actualizarPrueba(act: Actualizacion){
        viewModelScope.launch {
            val response = PruebasNetwork.retrofit.actualizarPrueba(act)
            if (response.isSuccessful){
                Log.d("Manuel", "FragPuntualViewModel. actualizarPruebaPuntual: ${response.body()}")
                _isPruebaAct.value = response.body()
            }

        }
    }

    fun actualizarDestinoHumano(humano: Humano){
        viewModelScope.launch {
            val response = UsuarioNetwork.retrofit.actualizarHumano(humano)
            if (response.isSuccessful){
                Log.d("Manuel", "FragPuntualViewModel. actualizarDestinoHumano: ${response.body()}")
            }else{
                Log.d("Manuel", "FragPuntualViewModel. actualizarDestinoHumano: ${response.body()}")
            }

        }

    }
}