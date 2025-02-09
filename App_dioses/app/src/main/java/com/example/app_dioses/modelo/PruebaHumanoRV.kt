package com.example.app_dioses.modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PruebaHumanoRV(
    @SerializedName("id")
    var id: Int,

    @SerializedName("idPrueba")
    var idPrueba: Int,

    @SerializedName("id_Humano")
    var idHumano: Int,

    @SerializedName("estado")
    var estado: Int,

    @SerializedName("destino_fin")
    var destinoFin: Int,

    @SerializedName("titulo")
    var titulo: String,

    @SerializedName("destino")
    var destino: Int,

    @SerializedName("tipo")
    var tipo: String
) : Serializable
