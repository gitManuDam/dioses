package com.example.app_dioses.modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PreguntaEleccion(
    @SerializedName("idPrueba")
    var idPrueba: Int,

    @SerializedName("pregunta")
    var pregunta: String,

    @SerializedName("opcion1")
    var opcion1: String,

    @SerializedName("opcion2")
    var opcion2: String,

    @SerializedName("atributo")
    var atributo: String,

    @SerializedName("valor")
    var valor: Int,

    @SerializedName("correcta")
    var correcta: String
) : Serializable

