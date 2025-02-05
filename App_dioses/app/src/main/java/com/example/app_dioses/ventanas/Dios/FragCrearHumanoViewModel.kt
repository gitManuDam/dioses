package com.example.app_dioses.ventanas.Dios

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_dioses.api.UsuarioNetwork
import com.example.app_dioses.modelo.Humano
import kotlinx.coroutines.launch
import retrofit2.Response
import kotlin.random.Random

class FragCrearHumanoViewModel : ViewModel() {

    private val _resRegistro = MutableLiveData<Boolean?>()
    val resRegistro: LiveData<Boolean?> get() = _resRegistro

    fun resetResRegistro() {
        _resRegistro.value = null
    }
    private val _errorCode = MutableLiveData<Int?>()
    val errorCode: LiveData<Int?> get() = _errorCode

    fun resetErrorCode() {
        _errorCode.value = null
    }

    fun registrarUsuarioVM(humano: Humano) {
        viewModelScope.launch {
            val response: Response<Boolean> = UsuarioNetwork.retrofit.registrarHumano(humano)
            _resRegistro.value = response.body()
            _errorCode.value = response.code()
        }
    }




    fun generarHumano(idDios: Int): Humano {
        val nombres = listOf("Juan", "Pedro", "Ana", "Maria", "Carlos", "Lucia", "Elena", "David", "Raul", "Sofia")
        val apellidos = listOf("Lopez", "Garcia", "Perez", "Rodriguez", "Martinez", "Gonzalez", "Hernandez")
        val dominiosCorreo = listOf("gmail.com", "yahoo.com", "hotmail.com", "outlook.com")

        val random = Random(System.currentTimeMillis())


        val nombre = "${nombres.random()} ${apellidos.random()}"
        val correo = "${nombre.replace(" ", "").lowercase()}@${dominiosCorreo.random()}"
        val clave = "${nombre.replace(" ", "").lowercase()}123" // Contraseña: nombre + 123
        val foto = ""
        val sabiduria = random.nextInt(1, 6)
        val nobleza = random.nextInt(1, 6)
        val virtud = random.nextInt(1, 6)
        val maldad = random.nextInt(1, 6)
        val audacia = random.nextInt(1, 6)

        return Humano(
            id = 0,
            nombre = nombre,
            correo = correo,
            clave = clave,
            destino = 0,
            estado = 0,
            foto = foto,
            sabiduria = sabiduria,
            nobleza = nobleza,
            virtud = virtud,
            maldad = maldad,
            audacia = audacia,
            idDios = idDios
        )
    }
}