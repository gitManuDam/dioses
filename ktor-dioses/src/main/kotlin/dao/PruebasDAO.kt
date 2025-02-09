package dao
import modelo.*
interface PruebasDAO {
    fun insertarPrueba(prueba: Prueba):Int?
    fun insertarPreguntaAfinidad(preguntaAfinidad: PreguntaAfinidad):Boolean
    fun insertarPreguntaEleccion(preguntaEleccion: PreguntaEleccion):Boolean
    fun insertarPreguntaRl(preguntaRl: PreguntaRl):Boolean
    fun insertarPruebaPuntal(pruebaPuntales: PruebaPuntales):Boolean

    fun eliminarPruebaPorId(id: Int):Boolean



    fun obtenerPruebas():List<Prueba>
    fun obtenerPruebaPorId(id: Int):Prueba?
    fun obtenerPruebasPorIdDios(id: Int):List<Prueba>
    fun obtenerPreguntaAfinidad(idPrueba: Int):PreguntaAfinidad?
    fun obtenerPreguntaEleccion(idPrueba: Int):PreguntaEleccion?
    fun obtenerPreguntaRl(idPrueba: Int): PreguntaRl?
    fun obtenerPruebaPuntual(idPrueba: Int):PruebaPuntales?


    fun asignarPruebaHumano(pruebasHumano: Pruebas_Humano):Boolean
    fun obtenerPruebasPendientes(idHumano:Int):List<PruebaHumanoRV>
    fun obtenerHistoricoPruebas(idHumano:Int):List<PruebaHumanoRV>
    fun actualizarPrueba(datos:Actualizacion):Boolean
}