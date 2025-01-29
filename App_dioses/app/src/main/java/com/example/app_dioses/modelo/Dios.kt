package com.example.app_dioses.modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Dios(
    @SerializedName("id")
    var id: Int,
    @SerializedName("nombre")
    var nombre: String,
    @SerializedName("clave")
    var clave: String,
    @SerializedName("sabiduria")
    var sabiduria: Int,
    @SerializedName("nobleza")
    var nobleza: Int,
    @SerializedName("virtud")
    var virtud: Int,
    @SerializedName("maldad")
    var maldad: Int,
    @SerializedName("audacia")
    var audacia: Int,
    @SerializedName("foto")
    var foto: String
) : Serializable

