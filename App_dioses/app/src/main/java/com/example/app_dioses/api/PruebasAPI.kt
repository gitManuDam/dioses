package com.example.app_dioses.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import com.example.app_dioses.modelo.*
interface PruebasAPI {
    @POST("pruebas/registrar")
    suspend fun registrarPrueba(@Body prueba: Prueba): Response<Int>

    @POST("pruebas/registrarPA")
    suspend fun registrarPreguntaAfinidad(@Body preguntaAfinidad: PreguntaAfinidad): Response<Boolean>

    @POST("pruebas/registrarPE")
    suspend fun registrarPreguntaEleccion(@Body preguntaEleccion: PreguntaEleccion): Response<Boolean>

    @POST("pruebas/registrarRL")
    suspend fun registrarPreguntaRl(@Body preguntaRl: PreguntaRl): Response<Boolean>

    @POST("pruebas/registrarPP")
    suspend fun registrarPruebaPuntales(@Body pruebaPuntales: PruebaPuntales): Response<Boolean>

    @DELETE("pruebas/eliminar/{id}")
    suspend fun eliminarPrueba(@Path("id") id: Int): Response<Boolean>

    @GET("pruebas")
    suspend fun obtenerPruebas(): Response<MutableList<Prueba>>

    @GET("pruebas/dios/{idDios}")
    suspend fun obtenerPruebasPorIdDios(@Path("idDios") idDios: Int): Response<MutableList<Prueba>>

    @GET("pruebas/{id}")
    suspend fun obtenerPruebaPorId(@Path("id") id: Int): Response<Prueba?>

    @GET("pruebas/afinidad/{id}")
    suspend fun obtenerPreguntaAfinidad(@Path("id") id: Int): Response<PreguntaAfinidad?>

    @GET("pruebas/eleccion/{id}")
    suspend fun obtenerPreguntaEleccion(@Path("id") id: Int): Response<PreguntaEleccion?>

    @GET("pruebas/puntual/{id}")
    suspend fun obtenerPruebaPuntual(@Path("id") id: Int): Response<PruebaPuntales?>

    @GET("pruebas/rl/{id}")
    suspend fun obtenerPreguntaRl(@Path("id") id: Int): Response<PreguntaRl?>

    @POST("pruebas/pruebaHumano")
    suspend fun asignarPruebaHumano(@Body pruebasHumano: Pruebas_Humano): Response<Boolean>

    @GET("pruebas/pruebaHumanoPendientes/{id}")
    suspend fun obtenerPruebasPendientes(@Path("id") id: Int): Response<MutableList<PruebaHumanoRV>>

    @GET("pruebas/pruebaHumanoHistorico/{id}")
    suspend fun obtenerHistoricoPruebas(@Path("id") id: Int): Response<MutableList<PruebaHumanoRV>>

    @PUT("pruebas/actualizar")
    suspend fun actualizarPrueba(@Body datos: Actualizacion): Response<Boolean>
}