package rutas

import dao.UsuarioDAO
import dao.UsuarioDAOImp
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import modelo.Humano
import modelo.Dios

val usuarioDAO: UsuarioDAO = UsuarioDAOImp()

fun Route.rutasUsuario(){
    route("/registrarHumano") {
        post {
            val humano = call.receive<Humano>()
            if (usuarioDAO.insertarHumano(humano)) {
                call.respond(HttpStatusCode.Created, "Humano registrado correctamente.")
            } else {
                call.respond(HttpStatusCode.Conflict, "Error al registrar el humano.")
            }
        }
    }

    // Ruta para obtener un humano por correo
    route("/obtenerHumanoPorCorreo/{correo}") {
        get("{correo}") {
            val correo = call.parameters["correo"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Correo no proporcionado.")
            val humano = usuarioDAO.obtenerHumanoPorCorreo(correo)
            if (humano != null) {
                call.respond(HttpStatusCode.OK, humano)
            } else {
                call.respond(HttpStatusCode.NotFound, "Humano no encontrado.")
            }
        }
    }

    // Ruta para obtener un humano por ID
    route("/obtenerHumanoPorId/{id}") {
        get("{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest, "ID inválido.")
            val humano = usuarioDAO.obtenerHumanoPorId(id)
            if (humano != null) {
                call.respond(HttpStatusCode.OK, humano)
            } else {
                call.respond(HttpStatusCode.NotFound, "Humano no encontrado.")
            }
        }
    }

    // Ruta para actualizar el destino de un humano
    route("/actualizarDestino/{id}") {
        put("{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@put call.respond(HttpStatusCode.BadRequest, "ID inválido.")
            val destino = call.receive<Int>()
            if (usuarioDAO.actualizarDestino(destino, id)) {
                call.respond(HttpStatusCode.Accepted, "Destino actualizado correctamente.")
            } else {
                call.respond(HttpStatusCode.Conflict, "Error al actualizar el destino.")
            }
        }
    }

    // Ruta para eliminar un humano
    route("/eliminarHumano/{id}") {
        delete("{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@delete call.respond(HttpStatusCode.BadRequest, "ID inválido.")
            if (usuarioDAO.eliminarHumano(id)) {
                call.respond(HttpStatusCode.OK, "Humano eliminado correctamente.")
            } else {
                call.respond(HttpStatusCode.Conflict, "Error al eliminar el humano.")
            }
        }
    }

    // Ruta para obtener un dios por nombre
    route("/obtenerDiosPorNombre/{nombre}") {
        get("{nombre}") {
            val nombre = call.parameters["nombre"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Nombre no proporcionado.")
            val dios = usuarioDAO.obtenerDiosPorNombre(nombre)
            if (dios != null) {
                call.respond(HttpStatusCode.OK, dios)
            } else {
                call.respond(HttpStatusCode.NotFound, "Dios no encontrado.")
            }
        }
    }
}
