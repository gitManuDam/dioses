
package com.example.app_dioses.modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PruebaPuntales(
    @SerializedName("idPrueba")
    var idPrueba: Int,

    @SerializedName("descripcion")
    var descripcion: String,

    @SerializedName("atributo")
    var atributo: String,

    @SerializedName("dificultad")
    var dificultad: Int
) : Serializable
