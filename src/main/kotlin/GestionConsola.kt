import com.github.ajalt.mordant.rendering.TextColors
import com.github.ajalt.mordant.terminal.Terminal


/**
 * Como la partida tambien se va a poder jugar contra una IA pues hago una interfaz
 * y a la partida ,dependiendo si eliges jugar solo o contra alguien usara una gestion de consola u otro
 */
interface Consola{
    fun mostrarCosas(arma:Arma,jugadores: List<Jugador>, jugador: Jugador,ronda:Int)
    fun mostrarInventario(jugador: Jugador)
    fun opcionesDisparo()
    fun usarObjeto(objeto: Objeto)
    fun opcionNoValida()
    fun respuestaNoValida()
    fun sinObjetosUsar()
    fun anadirObjetos()
    fun saltarTurno(jugador: Jugador)
    fun tienesQueUsarItem(jugador: Jugador):String
    fun noUsarNada()
    fun clicOBoom(boolean: Boolean)
}


class GestionConsola(val terminal: Terminal):Consola {

    override fun mostrarCosas(arma:Arma, jugadores: List<Jugador>, jugador: Jugador, ronda:Int){
        // Imprimir el arma a usar y sus balas
        terminal.println((TextColors.red)("-----------------------------------------------------------------"))
        println(arma.mostrarInfo())
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
            println("${index + 1}. $objeto")
        }
        println("${jugador.objetos.size + 1}. No usar nada")
        print("Selecciona el número correspondiente al objeto que quieres usar (o introduce ${jugador.objetos.size + 1} para salir): ")
    }

    override fun opcionesDisparo(){
        println("Que quieres hacer?")
        println("1. Dispararte?")
        println("2. Disparar al oponente?")
        print("¿Qué quieres hacer?: ")
    }

    override fun usarObjeto(objeto: Objeto) = println("Has elegido usar: $objeto")

    override fun opcionNoValida() = println("Opcion no valida")

    override fun respuestaNoValida() = println("Respuesta no valida")

    override fun sinObjetosUsar() = println("No tienes objetos a usar")

    override fun anadirObjetos() {
        println("*** EL ARMA SE HA CAMBIADO, YA NO QUEDAN BALAS CARGADAS ***")
        println("**** SE VAN A AÑADIR OBJETOS A LOS INVENTARIOS ****")
    }

    override fun saltarTurno(jugador: Jugador) = println("Se ha saltado el turno de ${jugador.nombre}")

    override fun tienesQueUsarItem(jugador: Jugador): String {
        return "Usa algun item ${jugador.nombre}, no puedes tener mas de 8"
    }

    override fun noUsarNada() = println("No has usado nada")

    override fun clicOBoom(boolean: Boolean){
        if (boolean){
            println("BOO0O0O0OOM")
        }else println("clic")
    }
}