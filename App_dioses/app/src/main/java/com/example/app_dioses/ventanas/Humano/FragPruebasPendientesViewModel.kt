package com.example.app_dioses.ventanas.Humano

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_dioses.api.PruebasNetwork
import com.example.app_dioses.api.UsuarioNetwork
import com.example.app_dioses.modelo.Humano
import com.example.app_dioses.modelo.PruebaHumanoRV
import com.example.app_dioses.parametros.Parametros
import kotlinx.coroutines.launch
import retrofit2.Response

class FragPruebasPendientesViewModel : ViewModel() {

    private val _pruebasPendientes = MutableLiveData<List<PruebaHumanoRV>>()
    val pruebasPendientes: MutableLiveData<List<PruebaHumanoRV>> = _pruebasPendientes

    private val _historico = MutableLiveData<List<PruebaHumanoRV>>()
    val historico: MutableLiveData<List<PruebaHumanoRV>> = _historico

    private val _pruebaDetalle= MutableLiveData<Any?>()
    val pruebaDetalle: MutableLiveData<Any?> = _pruebaDetalle

    fun restPruebaDetalle(){
        _pruebaDetalle.value = null
    }

    fun obtenerPruebasPendientes(idHumano: Int) {
        Log.e("Manuel", "obtenerHumanosProtegidos: FragProtegidosViewModel")
        viewModelScope.launch {
            val response = PruebasNetwork.retrofit.obtenerPruebasPendientes(idHumano)
            _pruebasPendientes.value = response.body()
        }
    }

    fun obtenerHistorico(idHumano: Int) {
        Log.e("Manuel", "obtenerHumanosProtegidos: FragProtegidosViewModel")
        viewModelScope.launch {
            val response = PruebasNetwork.retrofit.obtenerHistoricoPruebas(idHumano)
            _historico.value = response.body()
        }
    }

    fun obtenerPruebaDetalle(pruebaRV: PruebaHumanoRV) {
        Log.e("Manuel", "obtenerPruebaDetalle: FragProtegidosViewModel ~${pruebaRV.idPrueba}")
       when(pruebaRV.tipo){
           Parametros.pp->{
               viewModelScope.launch {
                   val response = PruebasNetwork.retrofit.obtenerPruebaPuntual(pruebaRV.idPrueba)
                   Log.d("Manuel", "obtenerPruebaDetalle: FragProtegidosViewModel ~${response.body()}")
                   _pruebaDetalle.value = response.body()
               }
           }
           Parametros.resL->{
               viewModelScope.launch {
                   val response = PruebasNetwork.retrofit.obtenerPreguntaRl(pruebaRV.idPrueba)
                   Log.d("Manuel", "obtenerPruebaDetalle: FragProtegidosViewModel ~${response.body()}")
                   _pruebaDetalle.value = response.body()
               }


           }

           Parametros.eleccion->{
               viewModelScope.launch {
                   val response = PruebasNetwork.retrofit.obtenerPreguntaEleccion(pruebaRV.idPrueba)
                   Log.d("Manuel", "obtenerPruebaDetalle: FragProtegidosViewModel ~${response.body()}")
                   _pruebaDetalle.value = response.body()
               }

           }

           Parametros.afinidad->{
               viewModelScope.launch {
                   val response = PruebasNetwork.retrofit.obtenerPreguntaAfinidad(pruebaRV.idPrueba)
                   Log.d("Manuel", "obtenerPruebaDetalle: FragProtegidosViewModel ~${response.body()}")
                   _pruebaDetalle.value = response.body()
               }

           }

       }
    }

}