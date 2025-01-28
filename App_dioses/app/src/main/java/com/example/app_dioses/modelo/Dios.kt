package com.example.app_dioses.modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Dios(
    @SerializedName("id")
    val id: Int,
    @SerializedName("nombre")
    val nombre: String,
    @SerializedName("clave")
    val clave: String,
    @SerializedName("sabiduria")
    val sabiduria: Int,
    @SerializedName("nobleza")
    val nobleza: Int,
    @SerializedName("virtud")
    val virtud: Int,
    @SerializedName("maldad")
    val maldad: Int,
    @SerializedName("audacia")
    val audacia: Int,
    @SerializedName("foto")
    val foto: String
) : Serializable

