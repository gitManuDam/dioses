package com.example.app_dioses.ventanas.Humano

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_dioses.api.PruebasNetwork
import com.example.app_dioses.api.UsuarioNetwork
import com.example.app_dioses.modelo.Actualizacion
import com.example.app_dioses.modelo.Humano
import com.example.app_dioses.modelo.PruebaHumanoRV
import com.example.app_dioses.modelo.PruebaPuntales
import kotlinx.coroutines.launch

class FragPuntualViewModel : ViewModel() {
    private val _pruebaPuntual = MutableLiveData<PruebaPuntales>()
    val pruebaPuntual: MutableLiveData<PruebaPuntales> = _pruebaPuntual

    private val _isPruebaAct = MutableLiveData<Boolean>()
    val isPruebaAct: MutableLiveData<Boolean> = _isPruebaAct

    fun obtenerPruebaPuntual(idPrueba: Int) {
        viewModelScope.launch {

            val response = PruebasNetwork.retrofit.obtenerPruebaPuntual(idPrueba)
            if (response.isSuccessful) {
                Log.d("Manuel", "FragPuntualViewModel. obtenerPruebaPuntual: ${response.body()}")
                _pruebaPuntual.value = response.body()
            }

        }
    }

    fun actualizarPruebaPuntual(act: Actualizacion){
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