package com.example.app_dioses.ventanas.LogIn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_dioses.api.UsuarioNetwork
import com.example.app_dioses.modelo.Dios
import com.example.app_dioses.modelo.UsuarioLogIn
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.Objects

class MainViewModel: ViewModel() {
    private val _errorCode = MutableLiveData<Int?>()
    val errorCode: LiveData<Int?> get() = _errorCode

    fun restablecerError(){
        _errorCode.value = null
    }

    private val _usuarioLogeado = MutableLiveData<Any?>()
    val usuarioLogeado: LiveData<Any?> get() = _usuarioLogeado

//    fun iniciarSesionVM(id: Int){
//        viewModelScope.launch {
//            val response: Response<Any?> = UsuarioNetwork.retrofit.obtenerUsuarioPorId(id)
//            _usuarioLogeado.value = response.body()
//        }
//    }
    fun cerrarSesionVM(){
        _usuarioLogeado.value = null
    }






    fun loginVM(datosLogIn: UsuarioLogIn) {
        viewModelScope.launch {
            //comprobar si el nombre coincide con un correo si es un nombre llamar a las funciones de dios
            //si es un correo llamar a las funciones de humano
            val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$")

            val response: Response<Any?> = if (emailRegex.matches(datosLogIn.nombre_correo)) {
                // Si es un correo, llamar a las funciones relacionadas con humanos
                UsuarioNetwork.retrofit.loginHumano(datosLogIn)
            } else {
                // Si no es un correo, llamar a las funciones relacionadas con dioses
                UsuarioNetwork.retrofit.loginDios(datosLogIn)
            }

            // Actualizar los valores del estado según la respuesta

            _usuarioLogeado.value = response.body()
            _errorCode.value = response.code()
        }
    }
}