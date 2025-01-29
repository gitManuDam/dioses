package rutas

import dao.UsuarioDAO
import dao.UsuarioDAOImp
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import modelo.Humano
import modelo.Dios
import modelo.UsuarioLogIn

val usuarioDAO: UsuarioDAO = UsuarioDAOImp()

fun Route.rutasUsuario() {

    // Rutas para Humanos
    route("/humanos") {
        post("/registrar") {
            println("registrar llamado")
            val humano = call.receive<Humano>()
            val existente = usuarioDAO.obtenerHumanoPorCorreo(humano.correo)
            if (existente != null) {
                println("humano ya existe")
                return@post call.respond(HttpStatusCode.BadRequest, false)
            }

            if (!usuarioDAO.insertarHumano(humano)) {
                println("Error al insertar humano")
                return@post call.respond(HttpStatusCode.Conflict, false)
            }
            println("Humano insertado")
            call.respond(HttpStatusCode.Created, true)
        }

//        put("/modificarPerfil") {
//            val humano = call.receive<Humano>()
//            val existente = usuarioDAO.obtenerHumanoPorId(humano.id) ?: return@put call.respond(HttpStatusCode.NotFound, false)
//
//            if (!usuarioDAO.modificarPerfilHumano(humano)) {
//                return@put call.respond(HttpStatusCode.BadRequest, false)
//            }
//            call.respond(HttpStatusCode.Accepted, true)
//        }

        post("/login") {
            val datosLogIn = call.receive<UsuarioLogIn>()
            val humano = usuarioDAO.obtenerHumanoPorCorreo(datosLogIn.nombre_correo) ?: return@post call.respond(HttpStatusCode.NotFound, null)
            if (humano.clave != datosLogIn.clave){
                return@post call.respond(HttpStatusCode.BadRequest, null)
            }
            call.respond(HttpStatusCode.OK, humano)
        }

//        put("/modificarDestino/{id}") {
//            val id = call.parameters["id"]?.toIntOrNull() ?: return@put call.respond(HttpStatusCode.BadRequest, false)
//            val destino = call.receive<Int>()
//            if (!usuarioDAO.modificarDestinoHumano(id, destino)) {
//                return@put call.respond(HttpStatusCode.Conflict, false)
//            }
//            call.respond(HttpStatusCode.Accepted, true)
//        }

        delete("/eliminar/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@delete call.respond(HttpStatusCode.BadRequest, false)
            if (!usuarioDAO.eliminarHumano(id)) {
                return@delete call.respond(HttpStatusCode.Conflict, false)
            }
            call.respond(HttpStatusCode.Accepted, true)
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest, null)
            val humano = usuarioDAO.obtenerHumanoPorId(id) ?: return@get call.respond(HttpStatusCode.NotFound, null)
            call.respond(HttpStatusCode.OK, humano)
        }

        get {
            call.respond(HttpStatusCode.OK, usuarioDAO.obtenerTodosLosHumanos())
        }


    }

    // Rutas para Dioses
    route("/dioses") {
        get {
            call.respond(HttpStatusCode.OK, usuarioDAO.obtenerTodosLosDioses())
        }
        post("/registrar") {
            val dios = call.receive<Dios>()
            val existente = usuarioDAO.obtenerDiosPorNombre(dios.nombre)
            if (existente != null) {
                return@post call.respond(HttpStatusCode.BadRequest, false)
            }

            if (!usuarioDAO.insertarDios(dios)) {
                return@post call.respond(HttpStatusCode.Conflict, false)
            }
            call.respond(HttpStatusCode.Created, true)
        }

        post("/login") {
            println("Login dios llamado")
            val datosLogIn = call.receive<UsuarioLogIn>()
            println("${datosLogIn.nombre_correo} != ${datosLogIn.clave}")
            val dios = usuarioDAO.obtenerDiosPorNombre(datosLogIn.nombre_correo) ?: return@post call.respond(HttpStatusCode.NotFound, null)
            if (dios.clave != datosLogIn.clave){
                println("${dios.clave} != ${datosLogIn.clave}")
                return@post call.respond(HttpStatusCode.BadRequest, null)
            }
            call.respond(HttpStatusCode.OK, dios)
        }

//        put("/modificarPerfil") {
//            val dios = call.receive<Dios>()
//            val existente = usuarioDAO.obtenerDiosPorId(dios.id) ?: return@put call.respond(HttpStatusCode.NotFound, false)
//
//            if (!usuarioDAO.modificarPerfilDios(dios)) {
//                return@put call.respond(HttpStatusCode.BadRequest, false)
//            }
//            call.respond(HttpStatusCode.Accepted, true)
//        }

        delete("/eliminar/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@delete call.respond(HttpStatusCode.BadRequest, false)
            if (!usuarioDAO.eliminarDios(id)) {
                return@delete call.respond(HttpStatusCode.Conflict, false)
            }
            call.respond(HttpStatusCode.Accepted, true)
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest, null)
            val dios = usuarioDAO.obtenerDiosPorId(id) ?: return@get call.respond(HttpStatusCode.NotFound, null)
            call.respond(HttpStatusCode.OK, dios)
        }




    }
}
