package com.example.app_dioses.ventanas.LogIn

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_dioses.api.UsuarioNetwork
import com.example.app_dioses.modelo.UsuarioLogIn
import kotlinx.coroutines.launch
import retrofit2.Response
import com.example.app_dioses.modelo.*

class MainViewModel: ViewModel() {
    private val _errorCode = MutableLiveData<Int?>()
    val errorCode: LiveData<Int?> get() = _errorCode

    fun restablecerError(){
        _errorCode.value = null
    }

    private val _humanoLogeado = MutableLiveData<Humano?>()
    val humanoLogeado: LiveData<Humano?> get() = _humanoLogeado

    private val _diosLogeado = MutableLiveData<Dios?>()
    val diosLogeado: LiveData<Dios?> get() = _diosLogeado

    private fun loginDiosID(id: Int) {
        viewModelScope.launch {
            val response: Response<Dios?> = UsuarioNetwork.retrofit.obtenerDiosPorId(id)
            if (response.isSuccessful) {
                _diosLogeado.value = response.body()
            } else {
                _errorCode.value = response.code()
            }
        }
    }
    private fun loginHumanoID(id: Int) {
        viewModelScope.launch {
            val response: Response<Humano?> = UsuarioNetwork.retrofit.obtenerHumanoPorId(id)
            if (response.isSuccessful) {
                _humanoLogeado.value = response.body()
            } else {
                _errorCode.value = response.code()

            }
        }
    }

    fun reiniciarSesionVM(usuario:Any){

        when(usuario){
            is Dios -> loginDiosID(usuario.id)
            is Humano -> loginHumanoID(usuario.id)
        }

    }

    fun cerrarSesionVM(){
        _humanoLogeado.value = null
        _diosLogeado.value = null
    }




    fun loginVM(datosLogIn: UsuarioLogIn) {
        Log.e("Manuel", "loginVM:")
            // Regex para verificar si es un correo electrónico
        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$")


        if (emailRegex.matches(datosLogIn.nombre_correo)) {
            // Es un humano
            loginHumanoVM(datosLogIn)
        } else {
            // Es un dios
            loginDiosVM(datosLogIn)
        }

    }

    private fun loginHumanoVM(datosLogIn: UsuarioLogIn) {
        viewModelScope.launch {
            Log.e("Manuel", "loginHumanoVM:")
            val response: Response<Humano?> = UsuarioNetwork.retrofit.loginHumano(datosLogIn)
            if (response.isSuccessful) {
                Log.e("Manuel", "loginHumanoVM: ${response.body()} ha ido bien")
                _humanoLogeado.value = response.body()
            } else {
                Log.e("Manuel", "loginHumanoVM: ${response.code()} no ha ido bien")
                _errorCode.value = response.code()
            }

        }
    }

    private fun loginDiosVM(datosLogIn: UsuarioLogIn) {
        Log.e("Manuel", "loginDiosVM: $datosLogIn")
        viewModelScope.launch {

            val response = UsuarioNetwork.retrofit.loginDios(datosLogIn)
            if (response.isSuccessful) {
                Log.e("Manuel", "loginDiosVM: ${response.body()}")
                _diosLogeado.value = response.body()
            } else {
                Log.e("Manuel", "loginDiosVM: ${response.code()}")
                _errorCode.value = response.code()
            }

        }
    }
}