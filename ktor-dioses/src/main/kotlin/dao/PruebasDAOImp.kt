package dao

import modelo.*
import java.sql.Statement

class PruebasDAOImp: PruebasDAO {
    override fun insertarPrueba(prueba: Prueba): Int? {
        val sql = "INSERT INTO pruebas (idDios, destino, tipo, titulo) VALUES (?, ?, ?, ?)"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
            statement.setInt(1, prueba.idDios)
            statement.setInt(2, prueba.destino)
            statement.setString(3, prueba.tipo)
            statement.setString(4, prueba.titulo)

            val affectedRows = statement.executeUpdate()
            if (affectedRows > 0) {
                val generatedKeys = statement.generatedKeys
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1)
                }
            }
        }
        return null
    }

    override fun insertarPreguntaAfinidad(preguntaAfinidad: PreguntaAfinidad): Boolean {
        val sql = "INSERT INTO preguntaafinidad(idPrueba,pregunta,atributo) VALUES (?, ?, ?)"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, preguntaAfinidad.idPrueba)
            statement.setString(2, preguntaAfinidad.pregunta)
            statement.setString(3, preguntaAfinidad.atributo)


            return statement.executeUpdate() > 0
        }
        return false
    }

    override fun insertarPreguntaEleccion(preguntaEleccion: PreguntaEleccion): Boolean {
        val sql = "INSERT INTO preguntaeleccion(idPrueba,pregunta,opcion1,opcion2,atributo,valor,correcta) VALUES (?, ?, ?,?, ?, ?,?)"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, preguntaEleccion.idPrueba)
            statement.setString(2, preguntaEleccion.pregunta)
            statement.setString(3, preguntaEleccion.opcion1)
            statement.setString(4, preguntaEleccion.opcion2)
            statement.setString(5,preguntaEleccion.atributo)
            statement.setInt(6,preguntaEleccion.valor)
            statement.setString(7,preguntaEleccion.correcta)


            return statement.executeUpdate() > 0
        }
        return false
    }

    override fun insertarPreguntaRl(preguntaRl: PreguntaRl): Boolean {
        val sql = "INSERT INTO preguntasrl (idPrueba,pregunta,palabrasClave,porcenAciertos)VALUES (?, ?, ?,?)"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, preguntaRl.idPrueba)
            statement.setString(2, preguntaRl.pregunta)
            statement.setString(3, preguntaRl.palabrasClave)
            statement.setInt(4, preguntaRl.porcenAciertos)


            return statement.executeUpdate() > 0
        }
        return false
    }

    override fun insertarPruebaPuntal(pruebaPuntales: PruebaPuntales): Boolean {
        val sql = "INSERT INTO pruebapuntuales(idPrueba,descripcion,atributo,dificultad) VALUES (?, ?, ?,?)"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, pruebaPuntales.idPrueba)
            statement.setString(2, pruebaPuntales.descripcion)
            statement.setString(3, pruebaPuntales.atributo)
            statement.setInt(4, pruebaPuntales.dificultad)



            return statement.executeUpdate() > 0
        }
        return false
    }

    override fun eliminarPruebaPorId(id: Int): Boolean {
        val sql = "DELETE FROM pruebas WHERE id=?;"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, id)

            return statement.executeUpdate() > 0
        }
        return false
    }

    override fun obtenerPruebas(): List<Prueba> {
        val pruebas = mutableListOf<Prueba>()
        val sql = "SELECT * FROM pruebas"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            val resultSet = statement.executeQuery()

            while (resultSet.next()) {
                val prueba = Prueba(
                    id = resultSet.getInt("id"),
                    idDios = resultSet.getInt("idDios"),
                    destino = resultSet.getInt("destino"),
                    tipo = resultSet.getString("tipo"),
                    titulo = resultSet.getString("titulo")
                )
                pruebas.add(prueba)
            }
        }
        return pruebas
    }

    override fun obtenerPruebaPorId(id: Int): Prueba? {

        val sql = "SELECT * FROM pruebas WHERE id=?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, id)
            val resultSet = statement.executeQuery()

            while (resultSet.next()) {
                val prueba = Prueba(
                    id = resultSet.getInt("id"),
                    idDios = resultSet.getInt("idDios"),
                    destino = resultSet.getInt("destino"),
                    tipo = resultSet.getString("tipo"),
                    titulo = resultSet.getString("titulo")
                )
                return prueba
            }

        }
        return null

    }

    override fun obtenerPruebasPorIdDios(idDios: Int): List<Prueba> {
        val pruebas = mutableListOf<Prueba>()
        val sql = "SELECT * FROM pruebas WHERE idDios=?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, idDios)
            val resultSet = statement.executeQuery()

            while (resultSet.next()) {
                val prueba = Prueba(
                    id = resultSet.getInt("id"),
                    idDios = resultSet.getInt("idDios"),
                    destino = resultSet.getInt("destino"),
                    tipo = resultSet.getString("tipo"),
                    titulo = resultSet.getString("titulo")
                )
                pruebas.add(prueba)
            }
        }
        return pruebas
    }

    override fun obtenerPreguntaAfinidad(idPrueba: Int): PreguntaAfinidad? {
        val sql = "SELECT * FROM preguntaafinidad WHERE idPrueba=?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, idPrueba)
            val resultSet = statement.executeQuery()

            while (resultSet.next()) {
                val prueba = PreguntaAfinidad(
                    idPrueba = resultSet.getInt("idPrueba"),
                    pregunta = resultSet.getString("pregunta"),
                    atributo = resultSet.getString("atributo")
                )
                return prueba
            }
        }
        return null
    }

    override fun obtenerPreguntaEleccion(idPrueba: Int): PreguntaEleccion? {
        val sql = "SELECT * FROM preguntaeleccion WHERE idPrueba=?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, idPrueba)
            val resultSet = statement.executeQuery()

            while (resultSet.next()) {
                val prueba = PreguntaEleccion(
                    idPrueba = resultSet.getInt("idPrueba"),
                    pregunta = resultSet.getString("pregunta"),
                    opcion1 = resultSet.getString("opcion1"),
                    opcion2 = resultSet.getString("opcion2"),
                    atributo = resultSet.getString("atributo"),
                    valor = resultSet.getInt("valor"),
                    correcta = resultSet.getString("correcta")
                )
                return prueba
            }
        }
        return null
    }

    override fun obtenerPreguntaRl(idPrueba: Int): PreguntaRl? {
        val sql = "SELECT * FROM preguntasrl WHERE idPrueba=?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, idPrueba)
            val resultSet = statement.executeQuery()

            while (resultSet.next()) {
                val prueba = PreguntaRl(
                    idPrueba = resultSet.getInt("idPrueba"),
                    pregunta = resultSet.getString("pregunta"),
                    palabrasClave = resultSet.getString("palabrasClave"),
                    porcenAciertos = resultSet.getInt("porceAciertos"),

                )
                return prueba
            }
        }
        return null
    }

    override fun obtenerPruebaPuntual(idPrueba: Int): PruebaPuntales? {
        val sql = "SELECT * FROM pruebapuntuales WHERE idPrueba=?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, idPrueba)
            val resultSet = statement.executeQuery()

            while (resultSet.next()) {
                val prueba = PruebaPuntales(
                    idPrueba = resultSet.getInt("idPrueba"),
                    descripcion = resultSet.getString("descripcion"),
                    atributo = resultSet.getString("atributo"),
                    dificultad = resultSet.getInt("dificultad"),

                    )
                return prueba
            }
        }
        return null
    }

    override fun asignarPruebaHumano(pruebasHumano: Pruebas_Humano): Boolean {
        val sql = "INSERT INTO pruebas_humanos (idPrueba, id_Humano, estado, destino_fin) VALUES (?, ?, 0,0)"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, pruebasHumano.idPrueba)
            statement.setInt(2, pruebasHumano.id_Humano)

            return statement.executeUpdate() > 0
        }
        return false
    }

    override fun obtenerPruebasPendientes(idHumano: Int): List<PruebaHumanoRV> {
        val pruebasPendientes = mutableListOf<PruebaHumanoRV>()
        val sql = """
            SELECT ph.id, ph.idPrueba, ph.id_Humano, ph.estado, ph.destino_fin, p.titulo, p.destino, p.tipo 
            FROM pruebas_humanos ph
             JOIN pruebas p ON ph.idPrueba = p.id WHERE ph.id_Humano = ? AND ph.estado = 0;
        """.trimIndent()
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, idHumano)
            val resultSet = statement.executeQuery()

            while (resultSet.next()) {
                val prueba = PruebaHumanoRV(
                    id = resultSet.getInt("id"),
                    idPrueba = resultSet.getInt("idPrueba"),
                    id_Humano = resultSet.getInt("id_Humano"),
                    estado = resultSet.getInt("estado"),
                    destino_fin = resultSet.getInt("destino_fin"),
                    titulo = resultSet.getString("titulo"),
                    destino =  resultSet.getInt("destino"),
                    tipo = resultSet.getString("tipo")
                )
                pruebasPendientes.add(prueba)
            }
        }
        return pruebasPendientes
    }

    override fun obtenerHistoricoPruebas(idHumano: Int): List<PruebaHumanoRV> {
        val historicoPruebas = mutableListOf<PruebaHumanoRV>()
        val sql = """
            SELECT ph.id, ph.idPrueba, ph.id_Humano, ph.estado, ph.destino_fin, p.titulo, p.destino, p.tipo 
            FROM pruebas_humanos ph
             JOIN pruebas p ON ph.idPrueba = p.id WHERE ph.id_Humano = ?;
        """.trimIndent()
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, idHumano)
            val resultSet = statement.executeQuery()

            while (resultSet.next()) {
                val prueba = PruebaHumanoRV(
                    id = resultSet.getInt("id"),
                    idPrueba = resultSet.getInt("idPrueba"),
                    id_Humano = resultSet.getInt("id_Humano"),
                    estado = resultSet.getInt("estado"),
                    destino_fin = resultSet.getInt("destino_fin"),
                    titulo = resultSet.getString("titulo"),
                    destino =  resultSet.getInt("destino"),
                    tipo = resultSet.getString("tipo")
                )
                historicoPruebas.add(prueba)
            }
        }
        return historicoPruebas
    }

    override fun actualizarPrueba(datos: Actualizacion): Boolean {
        val sql = "UPDATE pruebas_humanos SET estado=?, destino_fin=? WHERE id=?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, datos.estado)
            statement.setInt(2, datos.destino_fin)
            statement.setInt(3,datos.idPruebaHumano)

            return statement.executeUpdate() > 0
        }
        return false
    }
}