/**
 * Data class que representa un jugador.
 * @property nombre El nombre del jugador.
 * @property vida La cantidad de vida del jugador.
 * @property objetos La lista mutable de objetos del jugador.
 */
data class Jugador(
    val nombre: String,
    var vida: Int,
    val objetos: MutableList<Objeto> = mutableListOf<Objeto>(),
    val id:Int = contJug

){
    init {
        contJug++
    }

    companion object{
        var contJug = 1
    }
}