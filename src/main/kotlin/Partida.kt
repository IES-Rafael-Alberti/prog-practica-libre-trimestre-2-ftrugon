import java.lang.IllegalArgumentException
import kotlin.random.Random



/**
 * Clase que representa una partida del juego.
 * @property jugadores La lista de jugadores que participan en la partida.
 */
open class Partida(
    val jugadores: List<Jugador>,
    val listaArmas: List<Arma>,
    val listaobjetos: List<Objeto>,
    val gestionConsola: Consola
) {

    private var estadoPartida = false
    var arma = cambiarArma()
    var danio = 1
    private var ronda = 1
    var saltarTurno = false



    /**
     * Método para iniciar la partida.
     */
    open fun iniciarPartida() {
        // Establecer el estado de la partida como activo
        estadoPartida = true

        //Añade objetos al empezar la partida

        anadirobjetos()


        // Bucle principal de la partida
        do {

            // Elegir el jugador activo para esta ronda
            val jugador = elegirJugador(ronda)

            // Reiniciar el daño para esta ronda
            danio = arma.danio

            gestionConsola.mostrarCosas(arma,jugadores,jugador,ronda)

            // Permitir al jugador usar objetos
            jugador.usarObjeto(this)

            // Mostrar las opciones disponibles
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


    open fun anadirobjetos(){
        val cantidadRandom = Random.nextInt(2,5)
        jugadores.forEach {jugador ->
            repeat(cantidadRandom){
                if (jugador.objetos.size >= 8){
                    throw IllegalArgumentException(gestionConsola.tienesQueUsarobjeto(jugador))
                }
                jugador.anadirobjetoAleatorio(listaobjetos)
            }
        }
    }


    open fun reasignarCargadores(){
        listaArmas.forEach {
            it.cargador = it.recargar()
        }
    }

    fun cambiarArma():Arma{
        return listaArmas.random()
    }


    open fun gestiorarDisparo(opcion:Int,jugador: Jugador){
        if (opcion == 1) {
            dispararme(jugador, danio)
        } else {
            dispararle(jugador, danio)
            // Si se activa la opción de saltar turno, avanzar al siguiente jugador
            if (saltarTurno) {
                ronda++
                val jugador2 = elegirJugador(ronda)

                gestionConsola.saltarTurno(jugador2)

                // Vuelve saltarturno al estodo original para que no se vuelva a saltar el turno del oponente
                saltarTurno = false
            }
            ronda++
        }
    }

    /**
     * Método para permitir al jugador usar objetos durante su turno.
     * @param jugador El jugador que desea usar objetos.
     */
    





    /**
     * Método para que el jugador realice un disparo contra sí mismo.
     * @param jug El jugador que realiza el disparo.
     * @param danio El daño causado por el disparo.
     */
    open fun dispararme(jug: Jugador, danio: Int) {
        // Verificar si el disparo es exitoso

        val estabaCargado = arma.disparo()

        gestionConsola.clicOBoom(estabaCargado)

        if (estabaCargado) {
            // Reducir la vida del jugador según el daño especificado
            jug.recibedanio(danio)
        }
    }

    /**
     * Método para que el jugador realice un disparo contra el oponente.
     * @param jug El jugador que realiza el disparo.
     * @param danio El daño causado por el disparo.
     */
    open fun dispararle(jug: Jugador, danio: Int) {
        // Verificar si el disparo es exitoso
        val estabaCargado = arma.disparo()

        gestionConsola.clicOBoom(estabaCargado)

        if (estabaCargado) {
            // Identificar al oponente del jugador actual
            val oponente = if (jug == jugadores[0]) jugadores[1] else jugadores[0]
            // Reducir la vida del oponente según el daño especificado
            oponente.recibedanio(danio)
        }
    }

    /**
     * Método para elegir al jugador activo según el número de ronda.
     * @param numRonda El número de la ronda actual.
     * @return El jugador activo para esta ronda.
     */
    open fun elegirJugador(numRonda: Int): Jugador {
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
    open fun alguienMuere(): Boolean {
        // Iterar a través de todos los jugadores
        for (jugador in jugadores) {
            // Verificar si el jugador actual tiene 0 de vida
            if (jugador.vida <= 0) return true
        }
        return false
    }
}

class PartidaIa(jugadores: MutableList<Jugador>, listaArmas: List<Arma>, listaobjetos: List<Objeto>, gestionConsola: Consola): Partida(jugadores, listaArmas,
    listaobjetos,
    gestionConsola
) {
    init {
        jugadores.addLast(Ia(this,jugadores[0].vida,gestionConsola))
    }

    override fun iniciarPartida() {
        super.iniciarPartida()
    }

    override fun anadirobjetos() {
        super.anadirobjetos()
    }

    override fun reasignarCargadores() {
        super.reasignarCargadores()
    }

    override fun gestiorarDisparo(opcion: Int, jugador: Jugador) {
        super.gestiorarDisparo(opcion, jugador)
    }

    override fun dispararme(jug: Jugador, danio: Int) {
        super.dispararme(jug, danio)
    }

    override fun dispararle(jug: Jugador, danio: Int) {
        super.dispararle(jug, danio)
    }

    override fun elegirJugador(numRonda: Int): Jugador {
        return super.elegirJugador(numRonda)
    }

    override fun alguienMuere(): Boolean {
        return super.alguienMuere()
    }

}