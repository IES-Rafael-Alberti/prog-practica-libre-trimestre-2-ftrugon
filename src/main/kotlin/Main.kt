import com.github.ajalt.mordant.terminal.Terminal
import kotlin.random.Random

/**
 * Crea una lista aleatoria de objetos a partir de una lista dada de objetos.
 * @param numobjetos El número de elementos que se desean en la lista aleatoria.
 * @param todosobjetos La lista de objetos disponibles para seleccionar aleatoriamente.
 * @return Una lista mutable de objetos aleatorios.
 */
fun crearlistaaleatoria(numobjetos:Int,todosobjetos:List<Objeto>): MutableList<Objeto> {
    val listaadevolver = mutableListOf<Objeto>()
    for (i in 1..numobjetos){
        listaadevolver.add(todosobjetos.random())
    }
    return listaadevolver
}

/**
 * Funcion principal del programa , donde se crea la partida los jugadores y sus objetos
 */
fun main() {

    val terminal = Terminal()
    val gestionConsola = GestionConsola(terminal)


    val todosObjetos = listOf(
        Cigarro(),
        Lupa(),
        Refresco(),
        Sierra(),
        Esposas()
    )

    val armas = listOf(
        Escopeta(8,1),
        EscopetaDobleCanon(2,2),
        Revolver(6,1)
    )

    val vida = Random.nextInt(3, 6)


    val jugador1 = Jugador("Fran", vida,gestionConsola)

    //val jugador2 = Jugador("Stivem", aleatorio2)


    val listaJugadores = mutableListOf(
        jugador1,
        // jugador2
    )


    val partida = PartidaIa(listaJugadores,armas,todosObjetos,gestionConsola)


    partida.iniciarPartida()


}

