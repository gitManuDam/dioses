package modelo

import kotlinx.serialization.Serializable

@Serializable
data class PreguntaAfinidad(
    var idPrueba:Int,
    var pregunta:String,
    var atributo:String
)
