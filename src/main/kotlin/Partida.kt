import java.lang.IllegalArgumentException
import kotlin.random.Random



/**
 * Clase que representa una partida del juego.
 * @property jugadores La lista de jugadores que participan en la partida.
 * @property listaArmas La lista de armas disponibles en la partida.
 * @property listaobjetos La lista de objetos disponibles en la partida.
 * @property gestionConsola Instancia de la clase Consola utilizada para interactuar con la consola.
 * @property infoPartida Instancia de la clase GestionInfoPartida utilizada para gestionar la información de la partida.
 */
class Partida(
    private val jugadores: MutableList<Jugador>,
    private val listaArmas: List<Arma>,
    private val listaobjetos: List<Objeto>,
    private val gestionConsola: Consola,
    private val infoPartida: GestionInfoPartida
) {

    // Propiedades de la partida
    private var estadoPartida = false
    var arma = cambiarArma()
    var danio = 1
    var ronda = 1
    var saltarTurno = false

    init {
        // Si solo hay un jugador, se agrega un jugador IA
        if (jugadores.size == 1){
            jugadores.add(Ia(this,"dealer",jugadores[0].vida,gestionConsola))
        }
    }


    /**
     * Inicia la partida.
     * Este método inicializa los datos de armas y jugadores, establece el estado de la partida como activo,
     * añade objetos al inicio de la partida y ejecuta el bucle principal de la partida.
     */
    fun iniciarPartida() {

        // Inicializar los datos de armas y jugadores
        infoPartida.inicializaDatosArmas(listaArmas)
        infoPartida.inicializaDatosJugadores(jugadores)

        // Establecer el estado de la partida como activo
        estadoPartida = true

        //Añade objetos al empezar la partida
        anadirobjetos()


        // Bucle principal de la partida
        do {

            // Elegir el jugador activo para esta ronda (por si se usa una sierra)
            val jugador = elegirJugador(ronda)

            // Reiniciar el daño para esta ronda
            danio = arma.danio

            // Mostrar información relevante sobre la partida
            gestionConsola.mostrarCosas(arma,jugadores,jugador,ronda)


            // Permitir al jugador usar objetos
            jugador.usarObjeto(this)


            // Mostrar las opciones de disparo
            gestionConsola.opcionesDisparo()

            // Elegir la opción del jugador
            val opcion = jugador.elegirOpcionDisparo()

            // Realizar la acción correspondiente según la opción elegida
            gestiorarDisparo(opcion,jugador)

            // Verificar si alguien ha muerto durante esta ronda
            if (alguienMuere()) {
                estadoPartida = false
                break
            }

            // Si el cargador está vacío o solo tiene balas de fogueo, se elige otra arma
            if ( arma.cargador.count { it.cargado } == 0){
                reasignarCargadores()
                try {
                    gestionConsola.anadirObjetos()
                    anadirobjetos()
                }catch (e:Exception){
                    println(e.message)
                }

                arma = cambiarArma()
            }

        } while (estadoPartida)
    }


    /**
     * Añade objetos aleatorios a los inventarios de los jugadores al inicio de la partida.
     * La cantidad de objetos añadidos a cada jugador es un número aleatorio entre 2 y 5.
     * Si el inventario de un jugador ya contiene 8 objetos, se lanza una excepción.
     */
    private fun anadirobjetos() {
        // Generar un número aleatorio entre 2 y 5 para la cantidad de objetos a añadir
        val cantidadRandom = Random.nextInt(2, 5)

        // Iterar sobre cada jugador y añadir objetos aleatorios
        jugadores.forEach { jugador ->
            // Repetir el proceso de añadir objetos aleatorios según la cantidad aleatoria generada
            repeat(cantidadRandom) {
                // Verificar si el inventario del jugador ya contiene 8 objetos
                if (jugador.objetos.size >= 8) {
                    // Si ya tiene 8 objetos, lanzar una excepción indicando que debe usar un objeto
                    throw IllegalArgumentException(gestionConsola.tienesQueUsarobjeto(jugador))
                }
                // Añadir un objeto aleatorio al inventario del jugador
                jugador.anadirobjetoAleatorio(listaobjetos)
            }
        }
    }

    /**
     * Reasigna los cargadores de todas las armas, registrando la acción en la información de la partida.
     */
    private fun reasignarCargadores() {
        // Registrar la acción de reasignar cargadores en la información de la partida
        infoPartida.registrarAccionArma(arma.tipo, "Reasignar cargador")

        // Iterar sobre cada arma y recargar su cargador
        listaArmas.forEach { arma ->
            arma.cargador = arma.recargar()
        }
    }

    /**
     * Elige aleatoriamente un arma de la lista de armas disponibles.
     * @return El arma elegida aleatoriamente.
     */
    private fun cambiarArma(): Arma {
        return listaArmas.random()
    }


    /**
     * Gestiona el disparo realizado por el jugador según la opción elegida.
     * Registra la acción de disparo en la información de la partida.
     * @param opcion La opción elegida por el jugador: 1 para dispararse a sí mismo, 2 para disparar al oponente.
     * @param jugador El jugador que realiza el disparo.
     */
    private fun gestiorarDisparo(opcion: Int, jugador: Jugador) {
        // Registrar la acción de disparo en la información de la partida
        infoPartida.registrarAccionArma(arma.tipo, "Disparo")

        // Realizar la acción correspondiente según la opción elegida por el jugador
        if (opcion == 1) {
            // Disparar al propio jugador
            dispararme(jugador, danio)
        } else {
            // Disparar al oponente
            dispararle(jugador, danio)

            // Verificar si se activó la opción de saltar turno
            if (saltarTurno) {
                // Avanzar al siguiente jugador en la siguiente ronda
                ronda++
                val jugador2 = elegirJugador(ronda)

                // Verificar si alguien murió durante la ronda anterior
                if (!alguienMuere()) {
                    // Mostrar mensaje indicando que se ha saltado el turno del siguiente jugador
                    gestionConsola.saltarTurno(jugador2)
                }

                // Restablecer la variable saltarTurno para evitar saltar el turno del oponente nuevamente
                saltarTurno = false
            }
            // Avanzar a la siguiente ronda
            ronda++
        }
    }



    /**
     * Realiza un disparo del jugador contra sí mismo.
     * Registra la acción de dispararse en la información de la partida.
     * @param jug El jugador que realiza el disparo.
     * @param danio El daño causado por el disparo.
     */
    private fun dispararme(jug: Jugador, danio: Int) {
        // Registrar la acción de dispararse en la información de la partida
        infoPartida.registrarAccionJugador(jug, "Dispararse")

        // Verificar si el disparo es exitoso
        val estabaCargado = arma.disparo()

        // Mostrar mensaje indicando si el disparo fue exitoso o no
        gestionConsola.clicOBoom(estabaCargado)

        // Si el arma estaba cargada, reducir la vida del jugador según el daño especificado
        if (estabaCargado) {
            jug.recibedanio(danio)
        }
    }

    /**
     * Realiza un disparo del jugador contra el oponente.
     * Registra la acción de disparar al oponente en la información de la partida.
     * @param jug El jugador que realiza el disparo.
     * @param danio El daño causado por el disparo.
     */
    private fun dispararle(jug: Jugador, danio: Int) {
        // Registrar la acción de disparar al oponente en la información de la partida
        infoPartida.registrarAccionJugador(jug, "Disparar al oponente")

        // Verificar si el disparo es exitoso
        val estabaCargado = arma.disparo()

        // Mostrar mensaje indicando si el disparo fue exitoso o no
        gestionConsola.clicOBoom(estabaCargado)

        // Si el arma estaba cargada, identificar al oponente y reducir su vida según el daño especificado
        if (estabaCargado) {
            // Identificar al oponente del jugador actual
            val oponente = elegirJugador(ronda + 1)
            // Reducir la vida del oponente según el daño especificado
            oponente.recibedanio(danio)
        }
    }

    /**
     * Método para elegir al jugador activo según el número de ronda.
     * @param numRonda El número de la ronda actual.
     * @return El jugador activo para esta ronda.
     */
    private fun elegirJugador(numRonda: Int): Jugador {
        // Si el número de ronda es impar, devuelve el primer jugador de la lista
        return if (numRonda % 2 != 0) {
            jugadores[0]
        } else { // Si el número de ronda es par, devuelve el segundo jugador de la lista
            jugadores[1]
        }
    }



    /**
     * Método para verificar si algún jugador ha muerto durante la partida.
     * @return true si algún jugador ha muerto, false en caso contrario.
     */
    private fun alguienMuere(): Boolean {
        // Verificar algun jugador tiene 0 de vida
        for (jugador in jugadores) {

            if (jugador.vida <= 0) return true
        }
        return false
    }
}
