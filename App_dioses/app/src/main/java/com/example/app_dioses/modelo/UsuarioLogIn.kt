package com.example.app_dioses.modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UsuarioLogIn(
    @SerializedName("nombre_correo")
    val nombre_correo:String,
    @SerializedName("clave")
    val clave:String
): Serializable
