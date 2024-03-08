import java.lang.IllegalArgumentException
import kotlin.random.Random

/**
 * Clase que representa una partida del juego.
 * @property jugadores La lista de jugadores que participan en la partida.
 */
class Partida(val jugadores: List<Jugador>, val listaArmas:List<Arma>, val listaItems: List<Objeto>,val gestionConsola: Consola) {

    private var estadoPartida = false
    var arma = cambiarArma()

    var danio = 1
    private var ronda = 1
    var saltarTurno = false


    /**
     * Método para iniciar la partida.
     */
    fun iniciarPartida() {
        // Establecer el estado de la partida como activo
        estadoPartida = true

        // Bucle principal de la partida
        do {

            // Elegir el jugador activo para esta ronda
            val jugador = elegirJugador(ronda)

            // Reiniciar el daño para esta ronda
            danio = arma.danio

            gestionConsola.mostrarCosas(arma,jugadores,jugador,ronda)

            // Permitir al jugador usar objetos

            usarObjeto(jugador)

            // Mostrar las opciones disponibles
            gestionConsola.opcionesDisparo()

            // Elegir la opción del jugador
            val opcion = elegirOpcion()

            // Realizar la acción correspondiente según la opción elegida

            gestiorarDisparo(opcion,jugador)

            // Verificar si alguien ha muerto durante esta ronda

            if (alguienMuere()) {
                estadoPartida = false
                break
            }

            // Si el cargador está vacío, se elige otra arma

            if ( arma.cargador.count { it.cargado } == 0){
                reasignarCargadores()
                try {
                    anadirItems()
                }catch (e:Exception){
                    println(e.message)
                }

                arma = cambiarArma()
            }

        } while (estadoPartida)
    }


    fun anadirItems(){
        val cantidadRandom = Random.nextInt(1,4)
        gestionConsola.anadirObjetos()
        jugadores.forEach {jugador ->
            repeat(cantidadRandom){
                if (jugador.objetos.size >= 8){
                    throw IllegalArgumentException(gestionConsola.tienesQueUsarItem(jugador))
                }
                jugador.objetos.add(listaItems.random())
            }
        }
    }


    fun reasignarCargadores(){
        listaArmas.forEach {
            it.cargador = it.recargar()
        }
    }

    fun cambiarArma():Arma{
        return listaArmas.random()
    }


    fun gestiorarDisparo(opcion:Int,jugador: Jugador){
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
    private fun usarObjeto(jugador: Jugador) {
        // Verificar si el jugador tiene objetos en su inventario

            // Solicitar al jugador que elija un objeto para usar

            var estado = false

            // Bucle para manejar la entrada del jugador hasta que elija un objeto válido o decida no usar ningún objeto
            while (!estado) {
                if (jugador.objetos.isNotEmpty()) {
                    gestionConsola.mostrarInventario(jugador)
                    val respuesta = readln().trim().uppercase()
                    if (respuesta.toIntOrNull() != null) {
                        // Verificar si la opción elegida está dentro del rango de opciones válidas
                        val opcion = respuesta.toInt()

                        estado = elegirItem(opcion,jugador)

                    } else {
                        gestionConsola.respuestaNoValida()
                    }
                } else {
                    estado = true
                    gestionConsola.sinObjetosUsar()
                }
            }

    }


    fun elegirItem(opcion: Int,jugador: Jugador):Boolean {
        when (opcion) {
            in 1..jugador.objetos.size -> {
                // Obtener el objeto seleccionado por el jugador
                val objetoElegido = jugador.objetos[opcion - 1]

                gestionConsola.usarObjeto(objetoElegido)

                // Ejecutar la acción asociada al objeto en la partida
                objetoElegido.accion(this, jugador)
                // Eliminar el objeto usado del inventario del jugador
                jugador.objetos.remove(objetoElegido)

                return false
            }
            jugador.objetos.size + 1 -> {
                gestionConsola.noUsarNada()
                return true
            }
            else -> {
                gestionConsola.opcionNoValida()
                return false
            }
        }
    }


    /**
     * Método para que el jugador realice un disparo contra sí mismo.
     * @param jug El jugador que realiza el disparo.
     * @param danio El daño causado por el disparo.
     */
    private fun dispararme(jug: Jugador, danio: Int) {
        // Verificar si el disparo es exitoso

        val estabaCargado = arma.disparo()

        gestionConsola.clicOBoom(estabaCargado)

        if (estabaCargado) {
            // Reducir la vida del jugador según el daño especificado
            jug.vida -= danio
        }
    }

    /**
     * Método para que el jugador realice un disparo contra el oponente.
     * @param jug El jugador que realiza el disparo.
     * @param danio El daño causado por el disparo.
     */
    private fun dispararle(jug: Jugador, danio: Int) {
        // Verificar si el disparo es exitoso
        val estabaCargado = arma.disparo()

        gestionConsola.clicOBoom(estabaCargado)

        if (estabaCargado) {
            // Identificar al oponente del jugador actual
            val oponente = if (jug == jugadores[0]) jugadores[1] else jugadores[0]
            // Reducir la vida del oponente según el daño especificado
            oponente.vida -= danio
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
     * Método para que el jugador elija una opción de acción durante su turno.
     * @return La opción elegida por el jugador.
     */
    private fun elegirOpcion(): Int {
        do {

            val opcion = readlnOrNull()?.toIntOrNull() ?: 0

            // Verificar si la opción elegida está dentro del rango válido
            if (opcion < 1 || opcion > 2) {
                gestionConsola.opcionNoValida()
            } else {
                return opcion
            }
        } while (true)
    }

    /**
     * Método para verificar si algún jugador ha muerto durante la partida.
     * @return true si algún jugador ha muerto, false en caso contrario.
     */
    private fun alguienMuere(): Boolean {
        // Iterar a través de todos los jugadores
        for (jugador in jugadores) {
            // Verificar si el jugador actual tiene 0 de vida
            if (jugador.vida <= 0) return true
        }
        return false
    }
}