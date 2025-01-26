package dao

import modelo.Dios
import modelo.Humano

interface UsuarioDAO {
    fun insertarHumano(humano: Humano): Boolean
    fun actualizarDestino(destino: Int, id: Int): Boolean
    fun eliminarHumano(id: Int): Boolean
    fun obtenerHumanoPorCorreo(correo:String): Humano?
    fun obtenerHumanoPorId(id: Int): Humano?

    fun obtenerDiosPorNombre(nombre:String): Dios?
}