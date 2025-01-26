package dao

import modelo.Dios
import modelo.Humano

class UsuarioDAOImp:UsuarioDAO {
    override fun insertarHumano(humano: Humano): Boolean {
        // SQL con las columnas correspondientes de la tabla 'humanos'
        val sql = """
        INSERT INTO humanos (nombre, correo, clave, destino, estado, foto, sabiduria, nobleza, virtud, audacia, idDios)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
    """

        // Obtener la conexión a la base de datos
        val connection = Database.getConnection()

        connection?.use {
            // Preparar la sentencia
            val statement = it.prepareStatement(sql)

            // Asignar los valores a la sentencia
            statement.setString(1, humano.nombre)
            statement.setString(2, humano.correo)
            statement.setString(3, humano.clave)
            statement.setInt(4, humano.destino)
            statement.setInt(5, humano.estado)
            statement.setString(6, humano.foto)
            statement.setInt(7, humano.sabiduria)
            statement.setInt(8, humano.nobleza)
            statement.setInt(9, humano.virtud)
            statement.setInt(10, humano.audacia)
            statement.setInt(11, humano.idDios)

            // Ejecutar la sentencia y verificar si se insertó correctamente
            return statement.executeUpdate() > 0
        }

        // En caso de error, devolver false
        return false
    }

    override fun actualizarDestino(destino: Int, id: Int): Boolean {
        val sql = "UPDATE humanos SET destino = ? WHERE id = ?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, destino)
            statement.setInt(2, id)

            return statement.executeUpdate() > 0
        }
        return false
    }


    override fun eliminarHumano(id: Int): Boolean {
        val sql = "DELETE FROM humanos WHERE id = ?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, id)

            return statement.executeUpdate() > 0
        }
        return false
    }


    override fun obtenerHumanoPorCorreo(correo: String): Humano? {
        val sql = "SELECT * FROM humanos WHERE correo = ?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setString(1, correo)
            val resultSet = statement.executeQuery()

            if (resultSet.next()) {
                return Humano(
                    id = resultSet.getInt("id"),
                    nombre = resultSet.getString("nombre"),
                    correo = resultSet.getString("correo"),
                    clave = resultSet.getString("clave"),
                    destino = resultSet.getInt("destino"),
                    estado = resultSet.getInt("estado"),
                    foto = resultSet.getString("foto"),
                    sabiduria = resultSet.getInt("sabiduria"),
                    nobleza = resultSet.getInt("nobleza"),
                    virtud = resultSet.getInt("virtud"),
                    audacia = resultSet.getInt("audacia"),
                    idDios = resultSet.getInt("idDios")
                )
            }
        }
        return null
    }


    override fun obtenerHumanoPorId(id: Int): Humano? {
        val sql = "SELECT * FROM humanos WHERE id = ?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, id)
            val resultSet = statement.executeQuery()

            if (resultSet.next()) {
                return Humano(
                    id = resultSet.getInt("id"),
                    nombre = resultSet.getString("nombre"),
                    correo = resultSet.getString("correo"),
                    clave = resultSet.getString("clave"),
                    destino = resultSet.getInt("destino"),
                    estado = resultSet.getInt("estado"),
                    foto = resultSet.getString("foto"),
                    sabiduria = resultSet.getInt("sabiduria"),
                    nobleza = resultSet.getInt("nobleza"),
                    virtud = resultSet.getInt("virtud"),
                    audacia = resultSet.getInt("audacia"),
                    idDios = resultSet.getInt("idDios")
                )
            }
        }
        return null
    }


    override fun obtenerDiosPorNombre(nombre: String): Dios? {
        val sql = "SELECT * FROM dioses WHERE nombre = ?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setString(1, nombre)
            val resultSet = statement.executeQuery()

            if (resultSet.next()) {
                return Dios(
                    id = resultSet.getInt("id"),
                    nombre = resultSet.getString("nombre"),
                    clave = resultSet.getString("clave"),
                    sabiduria = resultSet.getInt("sabiduria"),
                    nobleza = resultSet.getInt("nobleza"),
                    virtud = resultSet.getInt("virtud"),
                    maldad = resultSet.getInt("maldad"),
                    audacia = resultSet.getInt("audacia"),
                    foto = resultSet.getInt("foto")
                )
            }
        }
        return null
    }
}

