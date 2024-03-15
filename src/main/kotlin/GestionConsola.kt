import com.github.ajalt.mordant.rendering.TextColors
import com.github.ajalt.mordant.terminal.Terminal



/**
 * Interfaz que define métodos para interactuar con la consola durante el programa
 */
interface Consola {
    /**
     * Muestra información relevante sobre el estado de la partida, como el arma, los jugadores, el jugador actual y la ronda.
     *
     * @param arma El arma actualmente en uso.
     * @param jugadores La lista de jugadores en la partida.
     * @param jugador El jugador actual.
     * @param ronda El número de la ronda actual.
     */
    fun mostrarCosas(arma: Arma, jugadores: List<Jugador>, jugador: Jugador, ronda: Int)

    /**
     * Muestra el inventario del jugador actual.
     *
     * @param jugador El jugador del cual mostrar el inventario.
     */
    fun mostrarInventario(jugador: Jugador)

    /**
     * Muestra las opciones de disparo disponibles para el jugador actual.
     */
    fun opcionesDisparo()

    /**
     * Informa al usuario que la opción seleccionada no es válida.
     */
    fun opcionNoValida()

    /**
     * Informa al usuario que la respuesta proporcionada no es válida.
     */
    fun respuestaNoValida()

    /**
     * Informa al usuario que no tiene objetos para usar en este momento.
     */
    fun sinObjetosUsar()

    /**
     * Solicita al usuario que seleccione objetos para añadir a su inventario.
     */
    fun anadirObjetos()

    /**
     * Permite al jugador saltar su turno.
     *
     * @param jugador El jugador que desea saltar su turno.
     */
    fun saltarTurno(jugador: Jugador)

    /**
     * Informa al jugador que debe usar un objeto en su inventario.
     *
     * @param jugador El jugador que debe usar un objeto.
     * @return Un mensaje indicando que el jugador debe usar un objeto.
     */
    fun tienesQueUsarobjeto(jugador: Jugador): String

    /**
     * Informa al jugador que no debe usar ningún objeto en este momento.
     */
    fun noUsarNada()

    /**
     * Representa visualmente el resultado de un disparo, indicando si ha sido exitoso o no.
     *
     * @param boolean `true` si el disparo ha sido exitoso, `false` en caso contrario.
     */
    fun clicOBoom(boolean: Boolean)

    /**
     * Muestra los puntos de vida de los jugadores entre disparos.
     */
    fun puntosEntreDisparos()

    /**
     * Imprime un mensaje relacionado con un objeto en la consola.
     *
     * @param mensaje El mensaje a imprimir.
     */
    fun printearMensajeObjeto(mensaje: String)

    /**
     * Imprime un número en la consola.
     *
     * @param num El número a imprimir.
     */
    fun printearNum(num: Int)

    /**
     * Informa al jugador que ha ocurrido un error al usar un refresco.
     */
    fun mostrarErrorRefresco()

    /**
     * Presenta al usuario la opción de jugar solo o contra alguien.
     */
    fun jugarSoloOContraAlguien()
}

/**
 * GestionConsola basicamente implementa lo de la interfaz
 */
class GestionConsola(private val terminal: Terminal):Consola {

    override fun mostrarCosas(arma:Arma, jugadores: List<Jugador>, jugador: Jugador, ronda:Int){
        // Imprimir el arma a usar y sus balas
        terminal.println((TextColors.red)("-----------------------------------------------------------------"))
        println(arma)
        println(arma.tipo)
        println("Hay ${arma.cargador.size} balas en el cargador")

        val numbalas = arma.cargador.count{ it.cargado}

        if (numbalas == 1){
            println("$numbalas esta cargada")
        }else println("$numbalas estan cargadas")

        // Imprimir información sobre la ronda y los jugadores
        println("-------------------------Ronda $ronda, ${jugador.nombre}---------------------------")
        println("${jugadores[0].nombre}:${jugadores[0].vida}")
        println("${jugadores[1].nombre}:${jugadores[1].vida}")
    }

    override fun mostrarInventario(jugador: Jugador){
        println("¿Quieres usar algún objeto? Este es tu inventario:")
        // Mostrar los objetos disponibles en el inventario del jugador
        jugador.objetos.forEachIndexed { index, objeto ->
            terminal.println((TextColors.blue)("${index + 1}. $objeto"))
        }
        terminal.println((TextColors.blue)("${jugador.objetos.size + 1}. No usar nada"))
        print("Selecciona el número correspondiente al objeto que quieres usar (o introduce ${jugador.objetos.size + 1} para salir): ")
    }

    override fun opcionesDisparo(){
        println("Que quieres hacer?")
        println("1. Dispararte?")
        println("2. Disparar al oponente?")
        print("¿Qué quieres hacer?: ")
    }

    override fun opcionNoValida() = println("Opcion no valida")

    override fun respuestaNoValida() = println("Respuesta no valida")

    override fun sinObjetosUsar() = println("No tienes objetos a usar")

    override fun anadirObjetos() {
        terminal.println((TextColors.red)("-----------------------------------------------------------------"))
        println("*** EL ARMA SE HA CAMBIADO, YA NO QUEDAN BALAS CARGADAS ***")
        println("**** SE VAN A AÑADIR OBJETOS A LOS INVENTARIOS ****")
    }

    override fun saltarTurno(jugador: Jugador) = println("Se ha saltado el turno de ${jugador.nombre}")

    override fun tienesQueUsarobjeto(jugador: Jugador): String {
        return "Usa algun objeto ${jugador.nombre}, no puedes tener mas de 8"
    }

    override fun noUsarNada() = println("No has usado nada")

    override fun clicOBoom(boolean: Boolean){
        if (boolean){
            puntosEntreDisparos()
            terminal.println((TextColors.red)("\nBOO0O0O0OOM"))
        }else {
            puntosEntreDisparos()
            terminal.println((TextColors.green)("\nclic"))
        }
    }

    override fun puntosEntreDisparos(){
        for (i in 1..3) {
            print(". ")
            Thread.sleep(750) // Pausa de medio segundo entre puntos
        }
    }

    override fun printearMensajeObjeto(mensaje:String) = terminal.println((TextColors.green)(mensaje))

    override fun printearNum(num:Int) = println(num)

    override fun mostrarErrorRefresco() = println("No puedes usar el refresco si no hay balas")

    override fun jugarSoloOContraAlguien(){
        println("Que quieres hacer?")
        println("1. Jugar solo")
        println("2. Jugar contra alguien ")
        print("Y bien?: ")
    }
}