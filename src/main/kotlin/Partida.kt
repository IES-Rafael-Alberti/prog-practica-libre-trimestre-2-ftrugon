import kotlin.random.Random

/**
 * Clase que representa una partida del juego.
 * @property jugadores La lista de jugadores que participan en la partida.
 */
class Partida(private val jugadores: List<Jugador>,val listaArmas:List<Arma>, val listaItems: List<Objeto>) {
    private var estadoPartida = false
    var arma = cambiarArma()
    var danio = 1
    private var cont = 1
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
            val jugador = elegirJugador(cont)

            // Reiniciar el daño para esta ronda
            danio = arma.danio

            printearCosas(jugador)

            // Permitir al jugador usar objetos

            usarObjeto(jugador)

            // Mostrar las opciones disponibles
            opciones()

            // Elegir la opción del jugador
            val opcion = elegirOpcion()

            // Realizar la acción correspondiente según la opción elegida

            gestiorarDisparo(opcion,jugador)

            // Verificar si alguien ha muerto durante esta ronda

            if (alguienMuere()) estadoPartida = false

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

        jugadores.forEach {jugador ->
            if (jugador.objetos.size > 8){
                throw NumberFormatException ("No puedes tener mas de 8 objetos")
            }else{
                repeat(cantidadRandom){
                    jugador.objetos.add(listaItems.random())
                }
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
                cont++
                val jugador2 = elegirJugador(cont)
                println("Se ha saltado el turno de ${jugador2.nombre}")

                // Vuelve saltarturno al estodo original para que no se vuelva a saltar el turno del oponente
                saltarTurno = false
            }
            cont++
        }
    }

    fun printearCosas(jugador: Jugador){
        // Imprimir el arma a usar y sus balas
        println("----------------------------------------------------------------------------")
        println(arma.mostrarInfo())
        println("Hay ${arma.cargador.size} balas en el cargador")

        val numbalas = arma.cargador.count{ it.cargado}

        if (numbalas == 1){
            println("$numbalas esta cargada")
        }else println("$numbalas estan cargadas")

        // Imprimir información sobre la ronda y los jugadores
        println("-------------------------Ronda $cont, ${jugador.nombre}---------------------------")
        println("${jugadores[0].nombre}:${jugadores[0].vida}")
        println("${jugadores[1].nombre}:${jugadores[1].vida}")
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
                    printearInventario(jugador)
                    val respuesta = pedirrespuesta()
                    if (respuesta.toIntOrNull() != null) {
                        // Verificar si la opción elegida está dentro del rango de opciones válidas
                        val opcion = respuesta.toInt()

                        estado = elegirItem(opcion,jugador)

                    } else {
                        println("Respuesta no válida")
                    }
                } else {
                    estado = true
                    println("No tienes objetos para usar")
                }
            }

    }


    fun elegirItem(opcion: Int,jugador: Jugador):Boolean {
        when (opcion) {
            in 1..jugador.objetos.size -> {
                // Obtener el objeto seleccionado por el jugador
                val objetoElegido = jugador.objetos[opcion - 1]
                println("Has elegido usar: $objetoElegido")
                // Ejecutar la acción asociada al objeto en la partida
                objetoElegido.accion(this, jugador)
                // Eliminar el objeto usado del inventario del jugador
                jugador.objetos.remove(objetoElegido)

                return false
            }
            jugador.objetos.size + 1 -> {
                println("No has usado nada")
                return true
            }
            else -> {
                println("Opción no válida")
                return false
            }
        }
    }

    fun printearInventario(jugador: Jugador){
        println("¿Quieres usar algún objeto? Este es tu inventario:")
        // Mostrar los objetos disponibles en el inventario del jugador
        jugador.objetos.forEachIndexed { index, objeto ->
            println("${index + 1}. $objeto")
        }
        println("${jugador.objetos.size + 1}. No usar nada")
        print("Selecciona el número correspondiente al objeto que quieres usar (o introduce ${jugador.objetos.size + 1} para salir): ")
    }


    /**
     * Simplemete es un readln
     * Esta funcion y la de arriba son las 2 unicas que he usado chatgpt
     * @return El mensaje
     */
    private fun pedirrespuesta(): String {
        return readln().trim().uppercase()
    }


    /**
     * Método para que el jugador realice un disparo contra sí mismo.
     * @param jug El jugador que realiza el disparo.
     * @param danio El daño causado por el disparo.
     */
    private fun dispararme(jug: Jugador, danio: Int) {
        // Verificar si el disparo es exitoso
        if (arma.disparo()) {
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
        if (arma.disparo()) {
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
     * Muestra lo que puede hacer el jugador
     */
    private fun opciones(){
        println("Que quieres hacer?")
        println("1. Dispararte?")
        println("2. Disparar al oponente?")
    }

    /**
     * Método para que el jugador elija una opción de acción durante su turno.
     * @return La opción elegida por el jugador.
     */
    private fun elegirOpcion(): Int {
        do {
            print("¿Qué quieres hacer?: ")
            val opcion = readlnOrNull()?.toIntOrNull() ?: 0

            // Verificar si la opción elegida está dentro del rango válido
            if (opcion < 1 || opcion > 2) {
                println("Opción no válida")
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