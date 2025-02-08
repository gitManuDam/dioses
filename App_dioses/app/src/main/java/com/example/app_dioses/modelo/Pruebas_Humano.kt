package com.example.app_dioses.modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Pruebas_Humano(
    @SerializedName("idPrueba")
    var idPrueba: Int,

    @SerializedName("id_Humano")
    var idHumano: Int,

    @SerializedName("estado")
    var estado: Int,

    @SerializedName("destino_fin")
    var destinoFin: Int
) : Serializable
