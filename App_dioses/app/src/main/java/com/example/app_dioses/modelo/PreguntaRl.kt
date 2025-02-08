package com.example.app_dioses.modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PreguntaRl(
    @SerializedName("idPrueba")
    var idPrueba: Int,

    @SerializedName("pregunta")
    var pregunta: String,

    @SerializedName("palabrasClave")
    var palabrasClave: String,

    @SerializedName("porcenAciertos")
    var porcenAciertos: Int
) : Serializable
