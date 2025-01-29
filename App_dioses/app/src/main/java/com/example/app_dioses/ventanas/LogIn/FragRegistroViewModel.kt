package com.example.app_dioses.ventanas.LogIn

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_dioses.api.UsuarioNetwork
import com.example.app_dioses.modelo.Dios
import com.example.app_dioses.modelo.Humano
import kotlinx.coroutines.launch
import retrofit2.Response
import kotlin.math.abs
import kotlin.random.Random

class FragRegistroViewModel : ViewModel() {
    private val _errorCode = MutableLiveData<Int?>()
    val errorCode: LiveData<Int?> get() = _errorCode

    fun restablecerError(){
        _errorCode.value = null
    }

    private val _boolRegistro = MutableLiveData<Boolean?>()
    val boolRegistro: LiveData<Boolean?> get() = _boolRegistro

    fun restablecerboolRegistro(){
        _boolRegistro.value = null
    }


    private fun registrarHumano(humano: Humano) {
        Log.e("Manuel", "LLamando a registrarHumano: $humano")
        viewModelScope.launch {
            val response: Response<Boolean> = UsuarioNetwork.retrofit.registrarHumano(humano)
            if (response.isSuccessful) {
                Log.e("Manuel", "Registro exitoso: ${response.body()}")
                _boolRegistro.value = response.body()
            } else {
                Log.e("Manuel", "Error en el registro: ${response.code()}")
                _errorCode.value = response.code()
            }
        }
    }


    private suspend fun obtenerDioses(): MutableList<Dios> {
        val response: Response<MutableList<Dios>> = UsuarioNetwork.retrofit.obtenerTodosLosDioses()
        return if (response.isSuccessful) {
            response.body() ?: mutableListOf()
        } else {
            _errorCode.value = response.code()
            mutableListOf()
        }
    }


    fun crearHumano(nombre: String, correo: String, clave: String) {
        viewModelScope.launch {
            // Crear humano
            val humano = Humano(0, nombre, correo, clave, 0, 0, "",
                0, 0, 0, 0, 0, 0)


            humano.sabiduria = Random.nextInt(1, 6)
            humano.nobleza = Random.nextInt(1, 6)
            humano.virtud = Random.nextInt(1, 6)
            humano.audacia = Random.nextInt(1, 6)
            humano.maldad = Random.nextInt(1, 6)


            val dioses = obtenerDioses()

            // Calcular la afinidad con los dioses
            humano.idDios = afinidad(humano, dioses)


            registrarHumano(humano)
        }
    }


    private fun afinidad(humano: Humano, dioses: MutableList<Dios>): Int {
        var idDiosAfin = 0
        var afinidadMin = Int.MAX_VALUE // Inicializar en un valor alto

        for (dios in dioses) {
            val afinidad = abs(humano.sabiduria - dios.sabiduria) +
                    abs(humano.nobleza - dios.nobleza) +
                    abs(humano.virtud - dios.virtud) +
                    abs(humano.maldad - dios.maldad) +
                    abs(humano.audacia - dios.audacia)

            if (afinidad < afinidadMin) { // Buscar la menor diferencia
                afinidadMin = afinidad
                idDiosAfin = dios.id
            }
        }
        return idDiosAfin
    }
}