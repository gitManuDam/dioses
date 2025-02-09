package modelo

import kotlinx.serialization.Serializable

@Serializable
data class Actualizacion (
    var estado:Int,
    var destino_fin:Int,
    var idPruebaHumano:Int
)


