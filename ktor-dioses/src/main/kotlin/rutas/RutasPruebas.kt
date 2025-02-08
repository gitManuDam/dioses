package rutas

import dao.PruebasDAOImp
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import modelo.*

val pruebaDAO: PruebasDAOImp = PruebasDAOImp()

fun Route.rutasPruebas() {
    route("/pruebas") {
        post("/registrar") {
            println("registrar prueba llamado")
            val prueba = try {
                call.receive<Prueba>()
            } catch (e: Exception) {
                println("Error al recibir la prueba: ${e.message}")
                return@post call.respond(HttpStatusCode.BadRequest, "Error al recibir los datos")
            }
            println("Prueba recibida: $prueba")
            val idPruebaInsertada = pruebaDAO.insertarPrueba(prueba)
            println("ID de la prueba insertada: $idPruebaInsertada")
            if (idPruebaInsertada == null) {
                println("Error al insertar prueba")
                return@post call.respond(HttpStatusCode.BadRequest, null)
            }
            call.respond(HttpStatusCode.OK, idPruebaInsertada)
        }
        post("/registrarPA") {
            println("registrar PA llamado")
            val preguntaAfinidad = try {
                call.receive<PreguntaAfinidad>()
            } catch (e: Exception) {
                println("Error al recibir la pregunta de afinidad: ${e.message}")
                return@post call.respond(HttpStatusCode.BadRequest, "Error al recibir los datos")
            }
            println("Pregunta Afinidad recibida: $preguntaAfinidad")
            val pregunta = pruebaDAO.obtenerPreguntaAfinidad(preguntaAfinidad.idPrueba)
            if (pregunta != null) {
                println("Pregunta ya existe para esta prueba")
                return@post call.respond(HttpStatusCode.Conflict, false)
            }

            if (!pruebaDAO.insertarPreguntaAfinidad(preguntaAfinidad)) {
                println("Error al insertar la pregunta de afinidad")
                return@post call.respond(HttpStatusCode.BadRequest, false)
            }
            call.respond(HttpStatusCode.Created, true)
        }
        post("/registrarPE") {
            println("registrar PE llamado")
            val preguntaEleccion = try {
                call.receive<PreguntaEleccion>()
            } catch (e: Exception) {
                println("Error al recibir la pregunta de elección: ${e.message}")
                return@post call.respond(HttpStatusCode.BadRequest, "Error al recibir los datos")
            }
            println("Pregunta Elección recibida: $preguntaEleccion")
            val pregunta = pruebaDAO.obtenerPreguntaEleccion(preguntaEleccion.idPrueba)
            if (pregunta != null) {
                println("Pregunta ya existe para esta prueba")
                return@post call.respond(HttpStatusCode.Conflict, false)
            }

            if (!pruebaDAO.insertarPreguntaEleccion(preguntaEleccion)) {
                println("Error al insertar la pregunta de elección")
                return@post call.respond(HttpStatusCode.BadRequest, false)
            }
            call.respond(HttpStatusCode.Created, true)
        }
        post("/registrarRL") {
            println("registrar RL llamado")
            val preguntaRl = try {
                call.receive<PreguntaRl>()
            } catch (e: Exception) {
                println("Error al recibir la pregunta RL: ${e.message}")
                return@post call.respond(HttpStatusCode.BadRequest, "Error al recibir los datos")
            }
            println("Pregunta RL recibida: $preguntaRl")
            val pregunta = pruebaDAO.obtenerPreguntaRl(preguntaRl.idPrueba)
            if (pregunta != null) {
                println("Pregunta ya existe para esta prueba")
                return@post call.respond(HttpStatusCode.Conflict, false)
            }

            if (!pruebaDAO.insertarPreguntaRl(preguntaRl)) {
                println("Error al insertar la pregunta RL")
                return@post call.respond(HttpStatusCode.BadRequest, false)
            }
            call.respond(HttpStatusCode.Created, true)
        }
        post("/registrarPP") {
            println("registrar PP llamado")
            val pruebaPuntales = try {
                call.receive<PruebaPuntales>()
            } catch (e: Exception) {
                println("Error al recibir la prueba de puntales: ${e.message}")
                return@post call.respond(HttpStatusCode.BadRequest, "Error al recibir los datos")
            }
            println("Prueba Puntales recibida: $pruebaPuntales")
            val pregunta = pruebaDAO.obtenerPruebaPuntual(pruebaPuntales.idPrueba)
            if (pregunta != null) {
                println("Pregunta ya existe para esta prueba")
                return@post call.respond(HttpStatusCode.Conflict, false)
            }

            if (!pruebaDAO.insertarPruebaPuntal(pruebaPuntales)) {
                println("Error al insertar la prueba de puntales")
                return@post call.respond(HttpStatusCode.BadRequest, false)
            }
            call.respond(HttpStatusCode.Created, true)
        }
        delete("/eliminar/{id?}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest, false)
            println("Eliminar prueba con ID: $id")
            val prueba = pruebaDAO.obtenerPruebaPorId(id.toInt()) ?: return@delete call.respond(HttpStatusCode.NotFound, false)

            if (!pruebaDAO.eliminarPruebaPorId(id.toInt())) {
                println("Error al eliminar la prueba con ID: $id")
                return@delete call.respond(HttpStatusCode.Conflict, false)
            }
            call.respond(HttpStatusCode.OK, true)
        }
        get {
            println("Obteniendo todas las pruebas")
            return@get call.respond(HttpStatusCode.OK, pruebaDAO.obtenerPruebas())
        }
        get("/dios/{idDios}") {
            val idDios = call.parameters["idDios"] ?: return@get call.respond(HttpStatusCode.BadRequest, false)
            println("Obteniendo pruebas para el Dios con ID: $idDios")
            return@get call.respond(HttpStatusCode.OK, pruebaDAO.obtenerPruebasPorIdDios(idDios.toInt()))
        }
        get("/{id?}") {
            val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, null)
            println("Obteniendo prueba con ID: $id")
            val prueba = pruebaDAO.obtenerPruebaPorId(id.toInt()) ?: return@get call.respond(HttpStatusCode.NotFound, null)

            call.respond(HttpStatusCode.OK, prueba)
        }
        get("/afinidad/{id}") {
            val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, null)
            println("Obteniendo pregunta de afinidad con ID: $id")
            val prueba = pruebaDAO.obtenerPreguntaAfinidad(id.toInt()) ?: return@get call.respond(HttpStatusCode.NotFound, null)

            call.respond(HttpStatusCode.OK, prueba)
        }
        get("/eleccion/{id}") {
            val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, null)
            println("Obteniendo pregunta de elección con ID: $id")
            val prueba = pruebaDAO.obtenerPreguntaEleccion(id.toInt()) ?: return@get call.respond(HttpStatusCode.NotFound, null)

            call.respond(HttpStatusCode.OK, prueba)
        }
        get("/puntual/{id}") {
            val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, null)
            println("Obteniendo pregunta puntual con ID: $id")
            val prueba = pruebaDAO.obtenerPruebaPuntual(id.toInt()) ?: return@get call.respond(HttpStatusCode.NotFound, null)

            call.respond(HttpStatusCode.OK, prueba)
        }
        get("/rl/{id}") {
            val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, null)
            println("Obteniendo pregunta RL con ID: $id")
            val prueba = pruebaDAO.obtenerPreguntaRl(id.toInt()) ?: return@get call.respond(HttpStatusCode.NotFound, null)

            call.respond(HttpStatusCode.OK, prueba)
        }
        post("/pruebaHumano") {
            println("Asignando prueba a humano")
            val pruebasHumano = try {
                call.receive<Pruebas_Humano>()
            } catch (e: Exception) {
                println("Error al recibir la prueba de humano: ${e.message}")
                return@post call.respond(HttpStatusCode.BadRequest, "Error al recibir los datos")
            }

            if (!pruebaDAO.asignarPruebaHumano(pruebasHumano)) {
                println("Error al asignar la prueba al humano")
                return@post call.respond(HttpStatusCode.Conflict, false)
            }
            call.respond(HttpStatusCode.Created, true)
        }
        get("/pruebaHumanoPendientes/{id?}") {
            val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, null)
            println("Obteniendo pruebas pendientes para el humano con ID: $id")
            return@get call.respond(HttpStatusCode.OK, pruebaDAO.obtenerPruebasPendientes(id.toInt()))
        }
        get("/pruebaHumanoHistorico/{id?}") {
            val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, null)
            println("Obteniendo histórico de pruebas para el humano con ID: $id")
            return@get call.respond(HttpStatusCode.OK, pruebaDAO.obtenerHistoricoPruebas(id.toInt()))
        }
        put("/actualizar") {
            println("Actualizando prueba")
            val datos = try {
                call.receive<Actualizacion>()
            } catch (e: Exception) {
                println("Error al recibir los datos de actualización: ${e.message}")
                return@put call.respond(HttpStatusCode.BadRequest, "Error al recibir los datos")
            }
            if (!pruebaDAO.actualizarPrueba(datos)) {
                println("Error al actualizar la prueba")
                return@put call.respond(HttpStatusCode.BadRequest, false)
            }
            call.respond(HttpStatusCode.OK, true)
        }
    }
}
