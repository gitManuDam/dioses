package modelo

import kotlinx.serialization.Serializable

@Serializable
data class PreguntaRl(
    var idPrueba:Int,
    var pregunta:String,
    var palabrasClave:String,
    var porcenAciertos:Int
)
