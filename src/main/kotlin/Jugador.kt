/**
 * Clase que representa a un jugador en la partida.
 *
 * @property nombre El nombre del jugador.
 * @property vida La cantidad de vida del jugador.
 * @property gestionConsola La instancia de [Consola] utilizada para interactuar con el jugador en la consola.
 * @property objetos La lista de objetos que el jugador tiene en su inventario.
 * Por defecto, esta lista está vacía.
 */
open class Jugador(
    val nombre: String,
    var vida: Int,
    val gestionConsola: Consola,
    val objetos: MutableList<Objeto> = mutableListOf<Objeto>()
){

    init {
        val listaDeNombresNoPermitidos = listOf("DIOS","SATAN")
        require(nombre.isNotEmpty()){"El nombre no puede estar vacio"}
        require(nombre.uppercase() !in listaDeNombresNoPermitidos) {"El nombre no puede ser dealer,dios o satan"}
    }

    /**
     * Reduce la vida del jugador según el daño recibido.
     *
     * @param danio La cantidad de daño que se va a aplicar al jugador.
     */
    fun recibedanio(danio:Int){
        vida -= danio
    }

    /**
     * Incrementa la vida del jugador en una unidad.
     */
    fun anadirVida(){
        vida ++
    }

    /**
     * Añade un objeto aleatorio a la lista de objetos del jugador.
     *
     * @param listaobjetos La lista de objetos de la cual se seleccionará un objeto aleatorio para añadir al inventario del jugador.
     */
    fun anadirobjetoAleatorio(listaobjetos:List<Objeto>){
        objetos.add(listaobjetos.random())
    }

    /**
     * Método que permite al jugador usar un objeto de su inventario durante la partida.
     *
     * @param partida La instancia de [Partida] en la que se encuentra el jugador.
     */
    open fun usarObjeto(partida: Partida) {

        var estado = false

        while (!estado) {
            //Comprueba si el jugador tiene objetos
            if (this.objetos.isNotEmpty()) {

                gestionConsola.mostrarInventario(this)

                //Pide un objeto a usar
                val respuesta = this.elegirobjeto()

                //Comprueba si la respuesta es valida, y si es valida el jugador usa el objeto
                if (respuesta.toIntOrNull() != null) {

                    val opcion = respuesta.toInt()

                    estado = this.elegirObjeto(partida,opcion,this)

                } else {
                    gestionConsola.respuestaNoValida()
                }
            } else {
                estado = true
                gestionConsola.sinObjetosUsar()
            }
        }
    }

    /**
     * Método que permite al jugador elegir un objeto de su inventario y realizar una acción asociada a ese objeto durante la partida.
     *
     * @param partida La instancia de [Partida] en la que se encuentra el jugador.
     * @param opcion El número correspondiente a la opción seleccionada por el jugador.
     * @param jugador El jugador que realiza la acción.
     * @return `true` si el jugador decide no usar ningún objeto, `false` en caso contrario.
     */
    open fun elegirObjeto(partida:Partida, opcion: Int, jugador: Jugador):Boolean {
        when (opcion) {
            in 1..jugador.objetos.size -> {
                // Obtener el objeto seleccionado por el jugador
                val objetoElegido = jugador.objetos[opcion - 1]

                // Ejecutar la accion del objeto
                try {

                    val mensaje = objetoElegido.accion(partida, jugador)
                    gestionConsola.printearMensajeObjeto(mensaje)

                }catch (e:Exception) {
                    //Solo el refresco puede dar un error, asi que es el unico error que muestro
                    gestionConsola.mostrarErrorRefresco()
                }

                jugador.objetos.remove(objetoElegido)

                return false
            }
            jugador.objetos.size + 1 -> {
                gestionConsola.noUsarNada()
                return true
            }
            else -> {
                gestionConsola.opcionNoValida()
                return false
            }
        }
    }


    /**
     * Método que permite al jugador elegir un objeto del inventario.
     *
     * @return La opción seleccionada por el jugador en formato de cadena de texto, en mayúsculas y sin espacios adicionales.
     */
    open fun elegirobjeto():String{
        return readln().trim().uppercase()
    }

    /**
     * Método que permite al jugador elegir una opción de disparo durante la partida.
     *
     * @return La opción seleccionada por el jugador.
     */
    open fun elegirOpcionDisparo(): Int{

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
}

/**
 * Clase que representa a la inteligencia artificial (IA) en la partida.
 *
 * @property partida La instancia de [Partida] en la que se encuentra la IA.
 * @property nombre El nombre de la IA.
 * @property vida La vida inicial de la IA.
 * @property gestionConsola La instancia de [Consola] que gestiona la interacción con la consola.
 * @property objetos La lista de objetos que posee la IA.
 */
class Ia(
    private val partida: Partida,
    nombre: String,
    vida: Int,
    gestionConsola: Consola,
    objetos: MutableList<Objeto> = mutableListOf<Objeto>()
):Jugador(nombre, vida, gestionConsola, objetos){

    // Propiedades específicas de la IA
    private var chance = calcularChance(partida.arma)
    private var rondaAnterior = partida.ronda
    private var seaUsadoRefresco = false
    private var seaUsadoEsposa = false
    private var seaUsadoLupa = false



    /**
     * Método que permite a la IA elegir un objeto del inventario para usar durante la partida.
     *
     * @return La opción seleccionada por la IA en formato de cadena de texto.
     */
    override fun elegirobjeto(): String {

        // Reiniciar la probabilidad de la IA cuando se use un refresco o inicie una nueva ronda
        if (seaUsadoRefresco) {
            chance = calcularChance(partida.arma)
            seaUsadoRefresco = false
        }
        if (rondaAnterior != partida.ronda) {
            chance = calcularChance(partida.arma)
            rondaAnterior = partida.ronda
        }

        // Buscar y seleccionar un objeto específico del inventario
        var cont = 0
        for (objeto in objetos){
            if (objeto is Cigarro){
                gestionConsola.printearNum(cont + 1)
                return (cont + 1).toString()
            }
            cont++
        }

        // Buscar y seleccionar las esposas si están disponibles y no han sido usadas antes en la ronda
        cont = 0
        for (objeto in objetos){
            if (objeto is Esposas && !seaUsadoEsposa){
                seaUsadoEsposa = true
                println(cont + 1)
                return (cont + 1).toString()
            }else if (objeto is Esposas && rondaAnterior != partida.ronda){
                seaUsadoEsposa = false
            }
            cont++
        }

        // Seleccionar un objeto en función de la probabilidad de la IA
        when (chance){
            in 65..100 -> {
                cont = 0
                for (objeto in objetos){
                    if (objeto is Sierra){
                        gestionConsola.printearNum(cont + 1)
                        return (cont + 1).toString()
                    }
                    cont++
                }
                gestionConsola.printearNum(cont + 1)
                return (objetos.size + 1).toString()
            }
            in 40..79->{
                cont = 0
                for (objeto in objetos){
                    if (objeto is Lupa && !seaUsadoLupa){
                        seaUsadoLupa = true
                        println(cont + 1)
                        chance = if (objeto.accion(partida,this) == "Este cartucho está cargado"){
                            100
                        }else 0
                        return (cont + 1).toString()
                    }else if (objeto is Lupa){
                        seaUsadoLupa = false
                    }
                    cont++
                }
            }
            in 1..70 -> {
                cont = 0
                for (objeto in objetos){
                    if (objeto is Refresco){
                        seaUsadoRefresco = true
                        gestionConsola.printearNum(cont + 1)
                        return (cont + 1).toString()
                    }
                    cont++
                }
            }
        }

        println(objetos.size + 1)
        return (objetos.size + 1).toString()

    }


    /**
     * Método que permite a la IA elegir una opción de disparo durante la partida.
     *
     * @return La opción de disparo seleccionada por la IA.
     */
    override fun elegirOpcionDisparo(): Int {

        // Reiniciar la probabilidad de la IA cuando se use el refresco o inicie una nueva ronda
        if (seaUsadoRefresco) {
            chance = calcularChance(partida.arma)
            seaUsadoRefresco = false
        }
        if (rondaAnterior != partida.ronda) {
            chance = calcularChance(partida.arma)
            rondaAnterior = partida.ronda
        }

        // Elegir la opción de disparo en función de la probabilidad de la IA
        return if (chance >= 50 ){
            println(2)
            2
        }else {
            println(1)
            1
        }
    }

    /**
     * Método que calcula la probabilidad de la IA de realizar un disparo efectivo en función del estado del cargador del arma.
     *
     * @param arma El arma utilizada para calcular la probabilidad.
     * @return La probabilidad de la IA de realizar un disparo efectivo, en porcentaje, del 1 al 100.
     */
    private fun calcularChance(arma: Arma): Int {
        return ((arma.cargador.count { it.cargado }.toDouble() / arma.cargador.size.toDouble()) * 100).toInt()
    }


}

