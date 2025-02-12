package dao

import modelo.Dios
import modelo.Humano
import modelo.UsuarioLogIn

class UsuarioDAOImp:UsuarioDAO {
    override fun insertarHumano(humano: Humano): Boolean {

        val sql = """
            INSERT INTO humanos (nombre, correo, clave, destino, estado, foto, sabiduria, nobleza, virtud,maldad, audacia, idDios)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)
        """.trimIndent()

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
            statement.setInt(10, humano.maldad)
            statement.setInt(11, humano.audacia)
            statement.setInt(12, humano.idDios)

            // Ejecutar la sentencia y verificar si se insertó correctamente
            return statement.executeUpdate() > 0
        }

        // En caso de error, devolver false
        return false
    }


    override fun actualizarHumano(humano: Humano): Boolean {
        val sql = """
        UPDATE humanos SET nombre = ?, correo = ?, clave = ?, destino = ?, estado = ?, foto = ?, 
        sabiduria = ?, nobleza = ?, virtud = ?, maldad = ?, audacia = ?, idDios = ? 
        WHERE id = ?
    """.trimIndent()

        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)

            // Asignar valores a la sentencia SQL
            statement.setString(1, humano.nombre)
            statement.setString(2, humano.correo)
            statement.setString(3, humano.clave)
            statement.setInt(4, humano.destino)
            statement.setInt(5, humano.estado)
            statement.setString(6, humano.foto)
            statement.setInt(7, humano.sabiduria)
            statement.setInt(8, humano.nobleza)
            statement.setInt(9, humano.virtud)
            statement.setInt(10, humano.maldad)
            statement.setInt(11, humano.audacia)
            statement.setInt(12, humano.idDios)
            statement.setInt(13, humano.id) // Condición WHERE id = ?

            // Ejecutar la actualización y devolver si se modificó al menos una fila
            return statement.executeUpdate() > 0
        }

        return false
    }





//    override fun actualizarDestino(destino: Int, id: Int): Boolean {
//        val sql = "UPDATE humanos SET destino = ? WHERE id = ?"
//        val connection = Database.getConnection()
//        connection?.use {
//            val statement = it.prepareStatement(sql)
//            statement.setInt(1, destino)
//            statement.setInt(2, id)
//
//            return statement.executeUpdate() > 0
//        }
//        return false
//    }


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
                    maldad = resultSet.getInt("maldad"),
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
                    maldad = resultSet.getInt("maldad"),
                    idDios = resultSet.getInt("idDios")
                )
            }
        }
        return null
    }

    override fun obtenerTodosLosHumanos(): List<Humano> {
        TODO("Not yet implemented")
    }

    override fun obtenerHumanosPorIdDios(idDios: Int): List<Humano> {
        val sql = "SELECT * FROM humanos WHERE idDios = ?"
        val connection = Database.getConnection()
        val humanos = mutableListOf<Humano>()

        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, idDios)
            val resultSet = statement.executeQuery()

            while (resultSet.next()) {
                val humano = Humano(
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
                    maldad = resultSet.getInt("maldad"),
                    idDios = resultSet.getInt("idDios")
                )
                humanos.add(humano)
            }
        }
        return humanos
    }



    override fun insertarDios(dios: Dios): Boolean {
        TODO("Not yet implemented")
    }





    override fun eliminarDios(id: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun obtenerDiosPorId(id: Int): Dios? {
        val sql = "SELECT * FROM dioses WHERE id = ?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, id)
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
                    foto = resultSet.getString("foto")
                )
            }
        }
        return null

    }

    override fun obtenerTodosLosDioses(): List<Dios> {
        val sql = "SELECT * FROM dioses"
        val diosList = mutableListOf<Dios>()
        val connection = Database.getConnection()

        connection?.use {
            val statement = it.prepareStatement(sql)
            val resultSet = statement.executeQuery()

            while (resultSet.next()) {
                val dios = Dios(
                    id = resultSet.getInt("id"),
                    nombre = resultSet.getString("nombre"),
                    clave = resultSet.getString("clave"),
                    sabiduria = resultSet.getInt("sabiduria"),
                    nobleza = resultSet.getInt("nobleza"),
                    virtud = resultSet.getInt("virtud"),
                    maldad = resultSet.getInt("maldad"),
                    audacia = resultSet.getInt("audacia"),
                    foto = resultSet.getString("foto") // Aquí la foto la tomamos como String, ya que el tipo de la columna puede ser VARCHAR si es un enlace o ruta de archivo
                )
                diosList.add(dios)
            }
        }
        return diosList
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
                    foto = resultSet.getString("foto")
                )
            }
        }
        return null
    }

    override fun actualizarDios(dios: Dios): Boolean {
        val sql = """
        UPDATE dioses SET nombre = ?, clave = ?, sabiduria = ?, nobleza = ?, virtud = ?, 
        maldad = ?, audacia = ?, foto = ? WHERE id = ?
    """.trimIndent()

        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)

            // Asignar valores a la sentencia SQL
            statement.setString(1, dios.nombre)
            statement.setString(2, dios.clave)
            statement.setInt(3, dios.sabiduria)
            statement.setInt(4, dios.nobleza)
            statement.setInt(5, dios.virtud)
            statement.setInt(6, dios.maldad)
            statement.setInt(7, dios.audacia)
            statement.setString(8, dios.foto)
            statement.setInt(9, dios.id) // Condición WHERE id = ?

            // Ejecutar la actualización y devolver si se modificó al menos una fila
            return statement.executeUpdate() > 0
        }

        return false
    }

}

