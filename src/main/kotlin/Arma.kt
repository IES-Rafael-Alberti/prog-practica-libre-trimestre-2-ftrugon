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
        comprobarValidez(contarCargadas,listaDeCartuchos,numBalas)

        // Mezclar la lista de cartuchos
        listaDeCartuchos.shuffle()


        return listaDeCartuchos
    }

    fun comprobarValidez(contarCargadas:Int,listaDeCartuchos: MutableList<Cartucho>,numBalas: Int){
        if (contarCargadas == 0) {
            listaDeCartuchos[0] = Cartucho(true)
        } else if (contarCargadas == numBalas) {
            listaDeCartuchos[0] = Cartucho(false)
        }
    }


    /**
     * Método para realizar un disparo con la escopeta.
     * @return true si el disparo fue exitoso (cartucho cargado), false si no hay cartucho cargado.
     */
    fun disparo(): Boolean {
        // Verificar si hay un cartucho cargado en el tambor
        return if (cargador.isNotEmpty() && cargador[0].cargado) {

            // Eliminar el cartucho disparado del cargador
            restarBala()
            true
        } else {

            // Eliminar el cartucho del cargador, aunque no haya disparo
            if (cargador.isNotEmpty()) {
                restarBala()
            }
            false
        }
    }


    fun restarBala(){
        cargador.remove(cargador[0])
    }


}


class Escopeta(capacidaMaxima: Int, danio: Int) : Arma(capacidaMaxima, danio){

    override fun toString(): String {

        val mensaje =
        """
         ,______________________________________       
        |_________________,----------._ [____]  ""-,__  __....-----=====
                       (_(||||||||||||)___________/   ""                |
                          `----------' #$$$$$$[ ))"-,                   |
                                               ""    `,  _,--....___    |
        """
        return mensaje
    }

}

class EscopetaDobleCanon(capacidaMaxima: Int, danio: Int) : Arma(capacidaMaxima, danio){

    override fun toString(): String {


        val mensaje=
        """
             ,________________________________       
            |__________,----------._ [____]  ""-,__  __...-----==="
                    (_(||||||||||||)___________/   ""             |
                       `----------'        [ ))"-,                |
                                            ""    `,  _,--...___  |
        """

        return mensaje
    }
}




class Revolver(capacidaMaxima: Int, danio: Int) : Arma(capacidaMaxima, danio){

    override fun elegirNumBalas(): Int {
        return capacidaMaxima
    }


    override fun toString(): String {

        val mensaje =
        """
                                              />
              __+_____________________/\/\___/ /|
             ()______________________      / /|/\
                         /0 0  ---- |----    /---\
                        |0 o 0 ----|| - \ --|      \
                         \0_0/____/ |    |  |\      \
                                     \__/__/  |      \
                                              |_______|
        """
        return mensaje
    }


}