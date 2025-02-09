package com.example.app_dioses.ventanas.Dios

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_dioses.api.PruebasNetwork
import com.example.app_dioses.modelo.Dios
import com.example.app_dioses.modelo.Humano
import com.example.app_dioses.modelo.Pruebas_Humano
import kotlinx.coroutines.launch
import kotlin.math.abs

class FragAsignarPruebaViewModel : ViewModel() {
    private val _errorCode = MutableLiveData<Int?>()
    val errorCode: MutableLiveData<Int?> = _errorCode

    fun restError(){
        _errorCode.value=null
    }

    private val _humanosSeleccionado = MutableLiveData<ArrayList<Humano>>()
    val humanosSeleccionado: MutableLiveData<ArrayList<Humano>> = _humanosSeleccionado

    fun seleccionarHumano(humano: Humano) {
        val currentList = _humanosSeleccionado.value ?: ArrayList()
        val newList = ArrayList(currentList)

        if (newList.contains(humano)) {
            newList.remove(humano)  // Elimina el humano si ya está en la lista
        } else {
            newList.add(humano)  // Agrega el humano si no está en la lista
        }

        // Asigna la nueva lista al LiveData
        _humanosSeleccionado.value = newList
    }

    fun asignarSeleccionados(idPrueba: Int) {
        if ( _humanosSeleccionado.value != null) {
            for (humano in humanosSeleccionado.value!!) {
                Log.d("Manuel", "Asignando humano: ${humano.nombre}, ID: ${humano.id}")
                viewModelScope.launch {
                    val pruebaHumano = Pruebas_Humano(idPrueba, humano.id, 0, 0)
                    val response = PruebasNetwork.retrofit.asignarPruebaHumano(pruebaHumano)
                    if (response.isSuccessful) {
                        Log.d("Manuel", "Prueba asignada correctamente para humano: ${humano.nombre}, ID: ${humano.id} idPrueba: $idPrueba")
                        _errorCode.value = response.code()
                    } else {
                        Log.d("Manuel", "Error al asignar prueba para humano: ${humano.nombre}, ID: ${humano.id} idPrueba: $idPrueba, Código de error: ${response.code()}")
                        _errorCode.value = response.code()
                    }
                    _humanosSeleccionado.value = ArrayList()
                }
            }
        }else{
            Log.d("Manuel", "No hay humanos seleccionados")
            _errorCode.value = 400
        }
    }

    fun asignarTodos(listaHumanos: ArrayList<Humano>,idPrueba: Int)  {
        for (humano in listaHumanos) {
            Log.d("Manuel", "Asignando a todos humano: ${humano.nombre}, ID: ${humano.id}")
            viewModelScope.launch {
                val pruebaHumano = Pruebas_Humano(idPrueba, humano.id, 0, 0)
                val response = PruebasNetwork.retrofit.asignarPruebaHumano(pruebaHumano)
                if (response.isSuccessful) {
                    Log.d(
                        "Manuel",
                        "Todos: Prueba asignada correctamente para humano: ${humano.nombre}, ID: ${humano.id} IDPrueba: $idPrueba"
                    )
                    _errorCode.value = response.code()
                } else {
                    Log.d(
                        "Manuel",
                        "Todos: Error al asignar prueba para humano: ${humano.nombre}, ID: ${humano.id},IdPrueba: $idPrueba Código de error: ${response.code()}"
                    )
                    _errorCode.value = response.code()
                }
            }

        }
    }

    fun asignarAfin(dios: Dios, listaHumanos: ArrayList<Humano>,idPrueba: Int) {
        var humanoAfin=humanoMasAfin(dios,listaHumanos)
        viewModelScope.launch {
            val pruebaHumano = Pruebas_Humano(idPrueba, humanoAfin!!.id, 0, 0)
            val response = PruebasNetwork.retrofit.asignarPruebaHumano(pruebaHumano)
            if (response.isSuccessful) {
                Log.d(
                    "Manuel",
                    "Afin: Prueba asignada correctamente para humano: ${humanoAfin.nombre}, ID: ${humanoAfin.id} idPrueba: ${dios.id}"
                )
                _errorCode.value = response.code()
            }
            else {
                Log.d(
                    "Manuel",
                    "Afin: Error al asignar prueba para humano: ${humanoAfin.nombre}, ID: ${humanoAfin.id} idPrueba: ${dios.id}, Código de error: ${response.code()}"
                )
                errorCode.value = response.code()
            }
        }

    }

    private fun humanoMasAfin(dios: Dios, humanos: MutableList<Humano>): Humano? {
        var humanoAfin: Humano? = null
        var afinidadMin = Int.MAX_VALUE // Inicializar en un valor alto

        for (humano in humanos) {
            val afinidad = abs(dios.sabiduria - humano.sabiduria) +
                    abs(dios.nobleza - humano.nobleza) +
                    abs(dios.virtud - humano.virtud) +
                    abs(dios.maldad - humano.maldad) +
                    abs(dios.audacia - humano.audacia)

            if (afinidad < afinidadMin) { // Buscar la menor diferencia
                afinidadMin = afinidad
                humanoAfin = humano
            }
        }

        return humanoAfin
    }



}