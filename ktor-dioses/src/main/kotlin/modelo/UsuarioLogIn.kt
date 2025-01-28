package modelo
import kotlinx.serialization.Serializable

@Serializable
data class UsuarioLogIn(var nombre_correo :String,var clave:String)


