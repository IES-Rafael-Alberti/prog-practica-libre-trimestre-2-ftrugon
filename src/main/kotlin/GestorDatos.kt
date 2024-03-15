import java.io.File


/**
 * Interfaz que define métodos para gestionar la información de una partida.
 */
interface InformacionPartida {
    /**
     * Historial de acciones relacionadas con armas durante la partida, representado como un mapa mutable.
     * La clave del mapa es el tipo de arma, y el valor es una lista mutable que representan las acciones.
     */
    val historialAccionesArmas: MutableMap<String, MutableList<String>>

    /**
     * Inicializa los datos de las armas para la partida.
     *
     * @param armas Una lista de objetos de tipo [T] que representan las armas a inicializar.
     * @param T El tipo genérico que debe ser una subclase de [Arma].
     */
    fun <T : Arma> inicializaDatosArmas(armas: List<T>)

    /**
     * Registra una acción relacionada con un arma durante la partida.
     *
     * @param tipoArma El tipo de arma sobre la que se realiza la acción.
     * @param accion La acción a registrar.
     */
    fun registrarAccionArma(tipoArma: String, accion: String)

    /**
     * Historial de acciones relacionadas con jugadores durante la partida, representado como un mapa mutable.
     * La clave del mapa es el nombre del jugador, y el valor es una lista mutable de cadenas que representan las acciones.
     */
    val historialAccionesjugadores: MutableMap<String, MutableList<String>>

    /**
     * Inicializa los datos de los jugadores para la partida.
     *
     * @param jugadores Una lista de objetos de tipo [T] que representan los jugadores a inicializar.
     * @param T El tipo genérico que debe ser una subclase de [Jugador].
     */
    fun <T : Jugador> inicializaDatosJugadores(jugadores: List<T>)

    /**
     * Registra una acción relacionada con un jugador durante la partida.
     *
     * @param jugador El jugador sobre el que se realiza la acción.
     * @param accion La acción a registrar.
     */
    fun registrarAccionJugador(jugador: Jugador, accion: String)


    /**
     * Almacena el resultado de la partida en un archivo.
     *
     * @param nombreArchivo El nombre del archivo donde se almacenará el resultado.
     */
    fun almacenarResultado(nombreArchivo: String)
}

/**
 * Clase que implementa la interfaz [InformacionPartida] para gestionar la información de una partida.
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

    override fun <T : Jugador> inicializaDatosJugadores(jugadores: List<T>) {
        jugadores.forEach {
            historialAccionesjugadores[it.nombre] = mutableListOf()
        }
    }

    override fun registrarAccionJugador(jugador: Jugador, accion: String) {
        historialAccionesjugadores[jugador.nombre]?.add(accion)
    }


    /**
     * Almacena el resultado de la partida en un archivo de texto.
     *
     * @param nombreArchivo El nombre del archivo donde se almacenará el resultado.
     */
    override fun almacenarResultado(nombreArchivo: String) {
        // Construye una cadena para almacenar el resultado de la partida, es como un txt al que puedes escribir cosas pero en cache
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
        File(nombreArchivo).writeText(resultado.toString())
        println("Resultado de la partida almacenado correctamente en '$nombreArchivo'.")

    }


}