import com.github.ajalt.mordant.terminal.Terminal
import kotlin.random.Random

/**
 * Crea una lista aleatoria de objetos a partir de una lista dada de objetos.
 * @param numitems El número de elementos que se desean en la lista aleatoria.
 * @param todosobjetos La lista de objetos disponibles para seleccionar aleatoriamente.
 * @return Una lista mutable de objetos aleatorios.
 */
fun crearlistaaleatoria(numitems:Int,todosobjetos:List<Objeto>): MutableList<Objeto> {
    val listaadevolver = mutableListOf<Objeto>()
    for (i in 1..numitems){
        listaadevolver.add(todosobjetos.random())
    }
    return listaadevolver
}

/**
 * Funcion principal del programa , donde se crea la partida los jugadores y sus items
 */
fun main() {

    // Lista de todos los objetos disponibles
    val todosObjetos = listOf(
        Cigarro(),
        Lupa(),
        Refresco(),
        Sierra(),
        Esposas()
    )

    // Generación aleatoria del número de elementos para cada jugador
    val aleatorio1 = Random.nextInt(2, 5)



    // Creación de lista de objetos aleatorios para el jugador 1
    val listaItemsJugador1 = crearlistaaleatoria(aleatorio1, todosObjetos)
    // Creación de lista de objetos aleatorios para el jugador 2
    val listaItemsJugador2 = crearlistaaleatoria(aleatorio1, todosObjetos)


    // Generación aleatoria de vida para cada jugador
    val aleatorio2 = Random.nextInt(3, 6)
    // Creación de los jugadores con sus respectivas listas de objetos aleatorios
    val jugador1 = Jugador("Fran", aleatorio2, listaItemsJugador1)
    val jugador2 = Jugador("Andres", aleatorio2, listaItemsJugador2)

    // Lista de jugadores
    val listaJugadores = listOf(
        jugador1,
        jugador2
    )


    // Lista de armas
    val armas = listOf(
        Escopeta(8,1),
        EscopetaDobleCanon(2,2),
        Revolver(6,1)
    )


    // Creación de la partida con la lista de jugadores

    val terminal = Terminal()
    val gestionConsola = GestionConsola(terminal)
    val partida = Partida(listaJugadores,armas,todosObjetos,gestionConsola)


    // Iniciar la partida
    partida.iniciarPartida()
}

