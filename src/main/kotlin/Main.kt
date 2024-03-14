import com.github.ajalt.mordant.terminal.Terminal
import kotlin.random.Random

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
        Escopeta(8,1,"Escopeta"),
        EscopetaDobleCanon(2,2,"Escopeta de doble cañon"),
        Revolver(6,1,"Revolver")
    )

    val gestionInfoPartida = GestionInfoPartida()
    gestionInfoPartida.inicializaDatosArmas(armas)

    val vida = Random.nextInt(3, 9)


    val jugador1 = Jugador("Fran", vida,gestionConsola)
    val jugador2 = Jugador("Stivem", vida,gestionConsola)


    val listaJugadores = mutableListOf<Jugador>(
        jugador1,
         //jugador2
    )


    val partida = Partida(listaJugadores,armas,todosObjetos,gestionConsola,gestionInfoPartida)


    partida.iniciarPartida()


    println("hola")
}

