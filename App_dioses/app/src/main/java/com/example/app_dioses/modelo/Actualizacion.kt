package com.example.app_dioses.modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Actualizacion(
    @SerializedName("estado")
    var estado: Int,
    @SerializedName("destino_fin")
    var destinoFin: Int,
    @SerializedName("idPruebaHumano")
    var idPruebaHumano: Int
) : Serializable


