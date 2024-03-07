import kotlin.random.Random

abstract class Arma(val capacidaMaxima:Int, var danio:Int) {

    var cargador = recargar()


    fun recargar():MutableList<Cartucho>{
        return cargarTambor(elegirNumBalas())
    }

    /**
     * Método privado para elegir el número de balas en el cargador.
     * @return El número de balas seleccionado aleatoriamente.
     */
    open fun elegirNumBalas(): Int {
        return Random.nextInt(2, capacidaMaxima + 1)
    }

    /**
     * Método privado para cargar el tambor con los cartuchos.
     * @param numBalas El número de balas a cargar en el tambor.
     * @return Una lista mutable de cartuchos cargados.
     */
    fun cargarTambor(numBalas: Int): MutableList<Cartucho> {
        val listaDeCartuchos = mutableListOf<Cartucho>()
        // Cargar los cartuchos en el tambor
        for (i in 1..numBalas) {
            listaDeCartuchos.add(Cartucho(Random.nextBoolean()))
        }
        val contarCargadas = listaDeCartuchos.count { it.cargado }

        // Verificar si no hay cartuchos cargados o todos están cargados
        if (contarCargadas == 0) {
            listaDeCartuchos[0] = Cartucho(true)
        } else if (contarCargadas == numBalas) {
            listaDeCartuchos[0] = Cartucho(false)
        }

        // Mezclar la lista de cartuchos
        listaDeCartuchos.shuffle()


        return listaDeCartuchos
    }


    /**
     * Método para realizar un disparo con la escopeta.
     * @return true si el disparo fue exitoso (cartucho cargado), false si no hay cartucho cargado.
     */
    fun disparo(): Boolean {
        // Verificar si hay un cartucho cargado en el tambor
        return if (cargador.isNotEmpty() && cargador[0].cargado) {
            println("BOOM")
            // Eliminar el cartucho disparado del cargador
            cargador.remove(cargador[0])
            true
        } else {
            println("clic")
            // Eliminar el cartucho del cargador, aunque no haya disparo
            if (cargador.isNotEmpty()) {
                cargador.remove(cargador[0])
            }
            false
        }
    }

    open fun mostrarInfo():String{
        return "capacidad maxima de $capacidaMaxima balas y un daño de $danio"
    }

}


class Escopeta(capacidaMaxima: Int, danio: Int) : Arma(capacidaMaxima, danio){

    override fun mostrarInfo(): String {
        return "Escopeta con " + super.mostrarInfo()
    }

    override fun toString(): String {
        return "Escopeta"
    }

}

class EscopetaDobleCanon(capacidaMaxima: Int, danio: Int) : Arma(capacidaMaxima, danio){
    override fun mostrarInfo(): String {
        return "Escopeta de doble cañon con " + super.mostrarInfo()
    }

    override fun toString(): String {
        return "Escopeta de doble cañon"
    }
}

class Revolver(capacidaMaxima: Int, danio: Int) : Arma(capacidaMaxima, danio){

    override fun elegirNumBalas(): Int {
        return capacidaMaxima
    }

    override fun mostrarInfo(): String {
        return "Revolver con " + super.mostrarInfo()
    }

    override fun toString(): String {
        return "Revolver"
    }


}