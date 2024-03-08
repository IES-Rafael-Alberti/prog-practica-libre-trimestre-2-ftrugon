
interface Jugable


class Jugador(
    val nombre: String,
    var vida: Int,
    val objetos: MutableList<Objeto> = mutableListOf<Objeto>()
):Jugable{

}


class Ia(
    var vida: Int,
    val objetos: MutableList<Objeto> = mutableListOf<Objeto>()
):Jugable {
    val nombre = "Stiwi"
}
