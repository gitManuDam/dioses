package com.example.app_dioses.modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Humano(
    @SerializedName("id")
    val id: Int,
    @SerializedName("nombre")
    val nombre: String,
    @SerializedName("correo")
    val correo: String,
    @SerializedName("clave")
    val clave: String,
    @SerializedName("destino")
    val destino: Int,
    @SerializedName("estado")
    val estado: Int,
    @SerializedName("foto")
    val foto: String,
    @SerializedName("sabiduria")
    val sabiduria: Int,
    @SerializedName("nobleza")
    val nobleza: Int,
    @SerializedName("virtud")
    val virtud: Int,
    @SerializedName("audacia")
    val audacia: Int,
    @SerializedName("idDios")
    val idDios: Int
) : Serializable
