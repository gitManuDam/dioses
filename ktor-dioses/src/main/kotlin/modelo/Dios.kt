package modelo

data class Dios(
    val id: Int,            // Identificador único del dios
    val nombre: String,     // Nombre del dios
    val clave: String,      // Clave única del dios
    val sabiduria: Int,     // Nivel de sabiduría del dios
    val nobleza: Int,       // Nivel de nobleza del dios
    val virtud: Int,        // Nivel de virtud del dios
    val maldad: Int,        // Nivel de maldad del dios
    val audacia: Int,       // Nivel de audacia del dios
    val foto: String           // Identificador o referencia a la foto del dios
)