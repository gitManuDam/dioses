package modelo

import kotlinx.serialization.Serializable

@Serializable
data class Pruebas_Humano(
    var idPrueba: Int,
    var id_Humano: Int,
    var estado:Int,
    var destino_fin:Int
)
