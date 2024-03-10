import kotlin.random.Random

open class Jugador(
    val nombre: String,
    var vida: Int,
    val objetos: MutableList<Objeto> = mutableListOf<Objeto>()
){


    fun recibedanio(danio:Int){
        vida -= danio
    }

    fun anadirVida(){
        vida ++
    }

    fun anadirItemAleatorio(listaItems:List<Objeto>){
        objetos.add(listaItems.random())
    }



    open fun elegirOpcion(gestionConsola: Consola): Int{
        do {
            val opcion = readln().toIntOrNull() ?: 0

            // Verificar si la opción elegida está dentro del rango válido
            if (opcion < 1 || opcion > 2) {
                gestionConsola.opcionNoValida()
            } else {
                return opcion
            }
        } while (true)
    }

    open fun elegirItem():String{
        return readln().trim().uppercase()
    }
}




class Ia(vida: Int,objetos: MutableList<Objeto> = mutableListOf<Objeto>()): Jugador("Stiven", vida, objetos) {

    override fun elegirOpcion(gestionConsola: Consola): Int {
        TODO("Not yet implemented")
    }

    override fun elegirItem(): String {
        TODO("Not yet implemented")
    }
}