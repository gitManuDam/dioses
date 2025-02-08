package com.example.app_dioses.modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PreguntaAfinidad(
    @SerializedName("idPrueba")
    var idPrueba: Int,
    @SerializedName("pregunta")
    var pregunta: String,
    @SerializedName("atributo")
    var atributo: String
) : Serializable

