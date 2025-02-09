package com.example.app_dioses.ventanas.Dios

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import android.util.Log
import com.example.app_dioses.api.PruebasNetwork
import com.example.app_dioses.modelo.Prueba
import kotlinx.coroutines.launch

class FragPruebasViewModel : ViewModel() {
    private val _errorCode = MutableLiveData<Int?>()
    val errorCode: LiveData<Int?> get() = _errorCode

    fun restablecerError() {
        Log.d("Manuel", "restablecerError: Restableciendo código de error")
        _errorCode.value = null
    }

    private val _pruebas = MutableLiveData<List<Prueba>>()
    val pruebas: LiveData<List<Prueba>> get() = _pruebas

    fun obtenerPruebas(idDios: Int) {
        Log.d("Manuel", "obtenerPruebas: Solicitando pruebas para el dios con ID $idDios")
        viewModelScope.launch {
            try {
                val response = PruebasNetwork.retrofit.obtenerPruebasPorIdDios(idDios)
                if (response.isSuccessful) {
                    _pruebas.value = response.body()
                    Log.d("Manuel", "obtenerPruebas: Pruebas obtenidas correctamente")
                } else {
                    Log.e("Manuel", "obtenerPruebas: Error en la respuesta ${response.code()}")
                    _errorCode.value = response.code()
                }
            } catch (e: Exception) {
                Log.e("Manuel", "obtenerPruebas: Excepción al obtener pruebas", e)
                _errorCode.value = -1
            }
        }
    }

    fun eliminarPrueba(idPrueba: Int, idDios: Int) {
        Log.d("Manuel", "eliminarPrueba: Eliminando prueba con ID $idPrueba")
        viewModelScope.launch {
            try {
                val response = PruebasNetwork.retrofit.eliminarPrueba(idPrueba)
                _errorCode.value = response.code()
                if (response.isSuccessful) {
                    Log.d("Manuel", "eliminarPrueba: Prueba eliminada correctamente")
                    obtenerPruebas(idDios)
                } else {
                    Log.e("Manuel", "eliminarPrueba: Error en la eliminación ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("Manuel", "eliminarPrueba: Excepción al eliminar prueba", e)
                _errorCode.value = -1
            }
        }
    }
}
