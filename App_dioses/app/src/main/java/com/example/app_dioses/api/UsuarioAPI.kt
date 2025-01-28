package com.example.app_dioses.api


import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import com.example.app_dioses.modelo.*
import java.util.Objects

interface UsuarioAPI {

    // Rutas para Humanos

    @POST("registrarHumano")
    suspend fun registrarHumano(@Body humano: Humano): Response<Boolean>

    @PUT("modificarPerfilHumano")
    suspend fun modificarPerfilHumano(@Body humano: Humano): Response<Boolean>

    @POST("loginHumano")
    suspend fun loginHumano(@Body datosLogIn: UsuarioLogIn): Response<Any?>

    @PUT("modificarDestinoHumano/{id}")
    suspend fun modificarDestinoHumano(@Path("id") id: Int, @Body destino: Int): Response<Boolean>

    @DELETE("eliminarHumano/{id}")
    suspend fun eliminarHumano(@Path("id") id: Int): Response<Boolean>

    @GET("humanos/{id}")
    suspend fun obtenerHumanoPorId(@Path("id") id: Int): Response<Humano?>

    @GET("humanos")
    suspend fun obtenerTodosLosHumanos(): Response<MutableList<Humano>>

    @GET("filtrarHumanosPorNombre/{nombre}")
    suspend fun filtrarHumanosPorNombre(@Path("nombre") nombre: String): Response<MutableList<Humano>>


    // Rutas para Dioses

    @POST("registrarDios")
    suspend fun registrarDios(@Body dios: Dios): Response<Boolean>

    @POST("loginDios")
    suspend fun loginDios(@Body datosLogIn: UsuarioLogIn): Response<Any?>

    @PUT("modificarPerfilDios")
    suspend fun modificarPerfilDios(@Body dios: Dios): Response<Boolean>

    @PUT("activarCuentaDios/{id}")
    suspend fun activarCuentaDios(@Path("id") id: Int): Response<Boolean>

    @DELETE("eliminarDios/{id}")
    suspend fun eliminarDios(@Path("id") id: Int): Response<Boolean>

    @GET("dioses/{id}")
    suspend fun obtenerDiosPorId(@Path("id") id: Int): Response<Dios?>

    @GET("dioses")
    suspend fun obtenerTodosLosDioses(): Response<MutableList<Dios>>

    @GET("filtrarDiosesPorNombre/{nombre}")
    suspend fun filtrarDiosesPorNombre(@Path("nombre") nombre: String): Response<MutableList<Dios>>
}