package com.example.app_dioses.ventanas

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cloudinary.Cloudinary
import com.example.app_dioses.api.UsuarioNetwork
import com.example.app_dioses.modelo.Dios
import com.example.app_dioses.modelo.Humano
import com.example.app_dioses.parametros.Parametros
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class FragPerfilViewModel : ViewModel() {
    private val _urlFoto = MutableLiveData<String?>()
    val urlFoto: MutableLiveData<String?> get() = _urlFoto

    fun resetUrlFoto() {
        _urlFoto.value = null
    }

    fun subirImagen(file: File) {
        viewModelScope.launch {
            val config = mapOf(
                "cloud_name" to Parametros.cloud_name,
                "api_key" to Parametros.api_key,
                "api_secret" to Parametros.api_secret
            )
            val cloudinary = Cloudinary(config)
            val options = mapOf("folder" to "Dioses")
            val uploadResult = withContext(Dispatchers.IO) {
                cloudinary.uploader().upload(file, options)
            }
            _urlFoto.value = uploadResult["url"].toString()
        }
    }

    fun actualizarHumano(humano: Humano) {
        viewModelScope.launch {
            val response = UsuarioNetwork.retrofit.actualizarHumano(humano)
            if (response.isSuccessful) {
                Log.d("Manuel", "FragPerfilViewModel. actualizarHumano: ${response.body()}")
            }else{
                Log.d("Manuel", "FragPerfilViewModel. actualizarHumano: ${response.body()}")
            }
        }
    }

    fun actualizarDios(dios: Dios) {
        viewModelScope.launch {
            val response = UsuarioNetwork.retrofit.actualizarDios(dios)
            if (response.isSuccessful) {
                Log.d("Manuel", "FragPerfilViewModel. actualizarDios: ${response.body()}")
            }else{
                Log.d("Manuel", "FragPerfilViewModel. actualizarDios: ${response.body()}")
            }
        }
    }

}