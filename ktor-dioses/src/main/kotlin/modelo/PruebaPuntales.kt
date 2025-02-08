package modelo

import kotlinx.serialization.Serializable

@Serializable
data class PruebaPuntales(
    var idPrueba: Int,
    var descripcion:String,
    var atributo:String,
    var dificultad:Int
)
