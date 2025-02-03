package com.example.app_dioses.ventanas.Dios

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_dioses.api.UsuarioNetwork
import com.example.app_dioses.modelo.Humano

import kotlinx.coroutines.launch
import retrofit2.Response

class FragProtegidosViewModel : ViewModel() {
    private val _humanos = MutableLiveData<List<Humano>>()
    val humanos: LiveData<List<Humano>> get() = _humanos


    fun obtenerHumanosProtegidos(idDios: Int) {
        Log.e("Manuel", "obtenerHumanosProtegidos: FragProtegidosViewModel")
        viewModelScope.launch {
            val response: Response<MutableList<Humano>> = UsuarioNetwork.retrofit.obtenerHumanosPorIdDios(idDios)
            _humanos.value = response.body()
        }
    }

}