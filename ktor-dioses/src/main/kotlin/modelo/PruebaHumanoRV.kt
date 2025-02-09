package modelo

import kotlinx.serialization.Serializable

@Serializable
data class PruebaHumanoRV(
    var id:Int,
    var idPrueba: Int,
    var id_Humano: Int,
    var estado:Int,
    var destino_fin:Int,
    var titulo:String,
    var destino:Int,
    var tipo:String,
)


