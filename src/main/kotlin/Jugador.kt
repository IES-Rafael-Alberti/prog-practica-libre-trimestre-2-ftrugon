open class Jugador(
    val nombre: String,
    var vida: Int,
    val gestionConsola: Consola,
    val objetos: MutableList<Objeto> = mutableListOf<Objeto>()
){



    fun recibedanio(danio:Int){
        vida -= danio
    }

    fun anadirVida(){
        vida ++
    }

    fun anadirobjetoAleatorio(listaobjetos:List<Objeto>){
        objetos.add(listaobjetos.random())
    }

    open fun usarObjeto(partida: Partida) {
        // Verificar si el jugador tiene objetos en su inventario

        // Solicitar al jugador que elija un objeto para usar

        var estado = false

        // Bucle para manejar la entrada del jugador hasta que elija un objeto válido o decida no usar ningún objeto
        while (!estado) {
            if (this.objetos.isNotEmpty()) {
                gestionConsola.mostrarInventario(this)

                val respuesta = this.elegirobjeto()

                if (respuesta.toIntOrNull() != null) {
                    // Verificar si la opción elegida está dentro del rango de opciones válidas
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


    open fun elegirObjeto(partida:Partida, opcion: Int, jugador: Jugador):Boolean {
        when (opcion) {
            in 1..jugador.objetos.size -> {
                // Obtener el objeto seleccionado por el jugador
                val objetoElegido = jugador.objetos[opcion - 1]


                // Ejecutar la acción asociada al objeto en la partida
                val mensaje = objetoElegido.accion(partida, jugador)

                gestionConsola.printearMensajeObjeto(mensaje)

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

    open fun elegirobjeto():String{
        return readln().trim().uppercase()
    }

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


class Ia(
    val partida: Partida,
    vida: Int,
    gestionConsola: Consola,
    objetos: MutableList<Objeto> = mutableListOf<Objeto>()
):Jugador("Dealer", vida, gestionConsola, objetos){

    var chance = calcularChance(partida.arma)
    var objetosUsados = mutableListOf<Objeto>()


    override fun elegirobjeto(): String {

        if (chance != 100) chance = calcularChance(partida.arma)


        var cont = 0
        if (chance in 49..69){
            for (objeto in objetos){
                if (objeto is Lupa){
                    println(cont + 1)
                    chance = if (objeto.accion(partida,this) == "Este cartucho está cargado"){
                        100
                    }else 0
                    return (cont + 1).toString()
                }
                cont++
            }
        }


        println(objetos.size + 1)
        return (objetos.size + 1).toString()
    }


    override fun elegirOpcionDisparo(): Int {

        chance = calcularChance(partida.arma)

        return if (chance >= 50 ){
            println(2)
            2
        }else {
            println(1)
            1
        }
    }

    fun calcularChance(arma: Arma): Int {
        return ((arma.cargador.count { it.cargado }.toDouble() / arma.cargador.size.toDouble()) * 100).toInt()
    }


}

