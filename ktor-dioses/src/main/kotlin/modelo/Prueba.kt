package modelo

import kotlinx.serialization.Serializable

@Serializable
data class Prueba(
    var id:Int,
    var idDios:Int,
    var destino:Int,
    var tipo:String,
    var titulo:String
)
