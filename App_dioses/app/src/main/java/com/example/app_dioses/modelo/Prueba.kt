package com.example.app_dioses.modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Prueba(
    @SerializedName("id")
    var id: Int,

    @SerializedName("idDios")
    var idDios: Int,

    @SerializedName("destino")
    var destino: Int,

    @SerializedName("tipo")
    var tipo: String,

    @SerializedName("titulo")
    var titulo: String
) : Serializable
