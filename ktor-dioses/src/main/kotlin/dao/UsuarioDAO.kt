package dao

import modelo.Dios
import modelo.Humano
import modelo.UsuarioLogIn

interface UsuarioDAO {
    fun insertarHumano(humano: Humano): Boolean
    fun eliminarHumano(id: Int): Boolean
    fun obtenerHumanoPorId(id: Int): Humano?
    fun obtenerHumanoPorCorreo(correo:String):Humano?
    fun obtenerTodosLosHumanos(): List<Humano>
    fun obtenerHumanosPorIdDios(idDios:Int): List<Humano>
    fun actualizarHumano(humano: Humano): Boolean




    fun insertarDios(dios: Dios): Boolean
    fun actualizarDios(dios: Dios): Boolean
    fun eliminarDios(id: Int): Boolean
    fun obtenerDiosPorId(id: Int): Dios?
    fun obtenerDiosPorNombre(nombre: String): Dios?
    fun obtenerTodosLosDioses():List<Dios>

}