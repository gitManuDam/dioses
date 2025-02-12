package com.example.app_dioses.ventanas.Dios

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_dioses.api.PruebasAPI
import com.example.app_dioses.api.PruebasNetwork
import com.example.app_dioses.modelo.Prueba
import com.example.app_dioses.modelo.*
import kotlinx.coroutines.launch

class FragCrearPruebaViewModel : ViewModel() {
    private val _errorCode =MutableLiveData<Int?>()
    val errorCode: MutableLiveData<Int?> = _errorCode

    fun restError(){
        _errorCode.value=null
    }

    private fun registrarPruebaConcreta(idPrueba:Int, pruebaConcreta:Any){
        when(pruebaConcreta){
            is PruebaPuntales -> {
                pruebaConcreta.idPrueba=idPrueba
                viewModelScope.launch {
                    val response = PruebasNetwork.retrofit.registrarPruebaPuntales(pruebaConcreta)
                    if (response.isSuccessful){
                        _errorCode.value=response.code()
                    }else{
                        PruebasNetwork.retrofit.eliminarPrueba(idPrueba)
                        _errorCode.value=response.code()
                    }
                }


            }
            is PreguntaEleccion -> {
                pruebaConcreta.idPrueba=idPrueba
                viewModelScope.launch {
                    val response = PruebasNetwork.retrofit.registrarPreguntaEleccion(pruebaConcreta)
                    if (response.isSuccessful){
                        _errorCode.value=response.code()
                    }else{
                        PruebasNetwork.retrofit.eliminarPrueba(idPrueba)
                        _errorCode.value=response.code()
                    }
                }

            }
            is PreguntaRl -> {
                pruebaConcreta.idPrueba=idPrueba
                viewModelScope.launch {
                    val response = PruebasNetwork.retrofit.registrarPreguntaRl(pruebaConcreta)
                    if (response.isSuccessful){
                        _errorCode.value=response.code()
                    }else{
                        PruebasNetwork.retrofit.eliminarPrueba(idPrueba)
                        _errorCode.value=response.code()
                    }
                }

            }
            is PreguntaAfinidad -> {
                pruebaConcreta.idPrueba=idPrueba
                viewModelScope.launch {
                    val response = PruebasNetwork.retrofit.registrarPreguntaAfinidad(pruebaConcreta)
                    if (response.isSuccessful){
                        _errorCode.value=response.code()
                    }else{
                        PruebasNetwork.retrofit.eliminarPrueba(idPrueba)
                        _errorCode.value=response.code()
                    }
                }

            }

        }
    }

     fun registrarPrueba(prueba: Prueba, pruebaConcreta:Any){
        viewModelScope.launch {
            val response  = PruebasNetwork.retrofit.registrarPrueba(prueba)
            if (response.isSuccessful){
                var idPrueba=response.body()
                registrarPruebaConcreta(idPrueba!!,pruebaConcreta)
            }
        }

    }

}