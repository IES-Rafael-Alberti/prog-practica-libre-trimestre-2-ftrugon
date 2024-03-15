import com.github.ajalt.mordant.terminal.Terminal
import kotlin.random.Random

/**
 * Función que permite al usuario elegir una opción entre 1 y 2.
 * Muestra un mensaje de error si la opción no está dentro del rango válido.
 *
 * @param gestionConsola Instancia de la clase GestionConsola utilizada para mostrar mensajes en la consola.
 * @return La opción elegida por el usuario.
 */
fun elegirOpcionEntre1y2(gestionConsola:GestionConsola): Int{
    do {
        val opcion = readln().toIntOrNull() ?: 0


        if (opcion < 1 || opcion > 2) {
            gestionConsola.opcionNoValida()
        } else {
            return opcion
        }
    } while (true)
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
        Escopeta(8,1,"Escopeta"),
        EscopetaDobleCanon(2,2,"Escopeta de doble cañon"),
        Revolver(6,1,"Revolver")
    )

    val gestionInfoPartida = GestionInfoPartida()

    val vida = Random.nextInt(3, 9)


    // Preguntar al usuario si quiere jugar solo o contra alguien
    gestionConsola.jugarSoloOContraAlguien()
    val opcion = elegirOpcionEntre1y2(gestionConsola)


    val listaJugadores = mutableListOf<Jugador>()

    // Dependiendo de la opción elegida, crear uno o dos jugadores
    when (opcion){
        1->{
            do {
                try {
                    print("Dime tu nombre: ")
                    val nombre = readln()
                    listaJugadores.add(Jugador(nombre, vida,gestionConsola))
                }catch (e:Exception) {
                    println(e.message)
                }
            }while (listaJugadores.size != 1)
        }
        2->{
            do {
                try {
                    print("Dime tu nombre: ")
                    val nombre = readln()
                    listaJugadores.add(Jugador(nombre, vida,gestionConsola))
                }catch (e:Exception) {
                    println(e.message)
                }
            }while (listaJugadores.size != 2)
        }

    }


    // Crear la partida con los jugadores, armas y objetos
    val partida = Partida(listaJugadores,armas,todosObjetos,gestionConsola,gestionInfoPartida)

    // Iniciar la partida
    partida.iniciarPartida()

    // Almacenar el resultado de la partida en un archivo
    gestionInfoPartida.almacenarResultado("resultado.txt")

}

