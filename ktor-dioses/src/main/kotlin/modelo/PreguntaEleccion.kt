package modelo

import kotlinx.serialization.Serializable


@Serializable
data class PreguntaEleccion(
    var idPrueba:Int,
    var pregunta:String,
    var opcion1:String,
    var opcion2:String,
    var atributo:String,
    var valor:Int,
    var correcta:String,
)
