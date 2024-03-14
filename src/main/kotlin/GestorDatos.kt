import java.io.File

/**
 * Interfaz que deben implementar las clases que quieran gestionar la información de la Carrera
 */
interface InformacionPartida {
    val historialAccionesArmas : MutableMap<String, MutableList<String>>
    fun <T : Arma> inicializaDatosArmas(armas: List<T>)
    fun registrarAccionArma(tipoArma: String, accion:String)


    val historialAccionesjugadores : MutableMap<String, MutableList<String>>
    fun <T : Jugador> inicializaDatosJugadores(Jugadores: List<T>)
    fun registrarAccionJugador(jugador: Jugador, accion:String)
}

/**
 * Clase que gestiona la información de una carrera.
 */
class GestionInfoPartida : InformacionPartida {

    override val historialAccionesArmas = mutableMapOf<String, MutableList<String>>()

    override fun <T : Arma> inicializaDatosArmas(armas: List<T>) {
        armas.forEach {
            historialAccionesArmas[it.tipo] = mutableListOf()
        }
    }

    override fun registrarAccionArma(tipoArma: String, accion:String) {
        historialAccionesArmas[tipoArma]?.add(accion)
    }



    override val historialAccionesjugadores = mutableMapOf<String, MutableList<String>>()

    override fun <T : Jugador> inicializaDatosJugadores(Jugadores: List<T>) {
        Jugadores.forEach {
            historialAccionesjugadores[it.nombre] = mutableListOf()
        }
    }

    override fun registrarAccionJugador(jugador: Jugador, accion: String) {
        historialAccionesArmas[jugador.nombre]?.add(accion)
    }




    fun almacenarResultado(nombreArchivo: String) {
        val resultado = StringBuilder()

        // Agregar información de armas al resultado
        resultado.append("Historial de acciones de armas:\n")
        historialAccionesArmas.forEach { (tipoArma, acciones) ->
            resultado.append("$tipoArma: ${acciones.joinToString(", ")}\n")
        }

        // Agregar información de jugadores al resultado
        resultado.append("\nHistorial de acciones de jugadores:\n")
        historialAccionesjugadores.forEach { (nombreJugador, acciones) ->
            resultado.append("$nombreJugador: ${acciones.joinToString(", ")}\n")
        }

        // Escribir el resultado en un archivo
        try {
            File(nombreArchivo).writeText(resultado.toString())
            println("Resultado de la partida almacenado correctamente en '$nombreArchivo'.")
        } catch (ex: Exception) {
            println("Ocurrió un error al almacenar el resultado de la partida: ${ex.message}")
        }
    }


}