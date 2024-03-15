import kotlin.random.Random

/**
 * Clase abstracta que representa un tipo genérico de arma en un juego.
 *
 * @property capacidaMaxima La capacidad máxima de munición que puede contener el arma.
 * @property danio El daño infligido por el arma.
 * @property tipo El tipo o nombre del arma.
 */
abstract class Arma(val capacidaMaxima:Int, var danio:Int,val tipo:String) {

    var cargador = recargar()


    /**
     * Recarga el cargador del arma.
     *
     * Este método invoca la función [cargarTambor] con el número de balas elegido por el jugador para recargar el cargador.
     *
     * @return Una lista mutable de objetos [Cartucho] que representan el nuevo cargador recargado.
     */
    fun recargar():MutableList<Cartucho>{
        return cargarTambor(elegirNumBalas())
    }

    /**
     * Método para elegir el número de balas en el cargador.
     * @return El número de balas seleccionado aleatoriamente.
     */
    open fun elegirNumBalas(): Int {
        return Random.nextInt(2, capacidaMaxima + 1)
    }

    /**
     * Método para cargar el tambor con los cartuchos.
     * @param numBalas El número de balas a cargar en el tambor.
     * @return Una lista de cartuchos cargados.
     */
    private fun cargarTambor(numBalas: Int): MutableList<Cartucho> {
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


    /**
     * Verifica la validez del tambor , si se crea y todas las balas estan cargadas, se restara 1 y se es al reves se sumara 1
     *
     * @param contarCargadas El número de cartuchos cargados en el tambor del arma.
     * @param listaDeCartuchos La lista mutable de objetos [Cartucho] que representan el tambor del arma.
     * @param numBalas El número total de cartuchos que se esperaban cargar en el tambor del arma.
     */
    private fun comprobarValidez(contarCargadas:Int, listaDeCartuchos: MutableList<Cartucho>, numBalas: Int){
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
        // Verificar si hay un cartucho cargado en el tambor y siempre resta una bala
        return if (cargador[0].cargado) {
            restarBala()
            true
        } else {
            restarBala()
            false
        }
    }

    /**
     * Resta una balaa al cargador
     */
    private fun restarBala(){
        cargador.remove(cargador[0])
    }


}

/**
 * Clase que representa una escopeta en el juego.
 *
 * @param capacidaMaxima La capacidad máxima de munición que puede contener la escopeta.
 * @param danio El daño infligido por la escopeta.
 * @param tipo El tipo de escopeta que es, en este caso es una escopeta normal
 */
class Escopeta(capacidaMaxima: Int, danio: Int, tipo: String) : Arma(capacidaMaxima, danio, tipo){

    /**
     * @return Una cadena que representa una representación ASCII del arma.
     */
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

/**
 * Clase que representa una escopeta de doble cañon.
 *
 * @param capacidaMaxima La capacidad máxima de munición que puede contener la escopeta.
 * @param danio El daño infligido por la escopeta.
 * @param tipo El tipo de escopeta que es, en este caso es una escopeta de 2 cañones
 */
class EscopetaDobleCanon(capacidaMaxima: Int, danio: Int, tipo: String) : Arma(capacidaMaxima, danio, tipo){

    /**
     * @return Una cadena que representa una representación ASCII del arma.
     */
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



/**
 * Clase que representa un revolver en el juego.
 *
 * @param capacidaMaxima La capacidad máxima de munición que puede contener la escopeta.
 * @param danio El daño infligido por la escopeta.
 * @param tipo El tipo de escopeta que es, en este caso es una escopeta de 2 cañones
 */
class Revolver(capacidaMaxima: Int, danio: Int, tipo: String) : Arma(capacidaMaxima, danio, tipo){

    /**
     * Sobreescribo elegurnumbalas porque quiero siempre que el revolver se juegue con la capacidad maxima
     *
     * @return la capacidad del revolver, 6 basicamente
     */
    override fun elegirNumBalas(): Int {
        return capacidaMaxima
    }

    /**
     * @return Una cadena que representa una representación ASCII del arma.
     */
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