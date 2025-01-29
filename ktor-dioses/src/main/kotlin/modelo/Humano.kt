package modelo

import kotlinx.serialization.Serializable

@Serializable
data class Humano(
    val id: Int,
    val nombre: String,
    val correo: String,
    val clave: String,
    val destino: Int,
    val estado: Int,
    val foto: String,
    val sabiduria: Int,
    val nobleza: Int,
    val virtud: Int,
    val maldad: Int,
    val audacia: Int,
    val idDios: Int
)