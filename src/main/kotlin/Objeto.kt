/**
 * Interfaz que define un objeto del juego.
 */
interface Objeto {
    /**
     * Método para realizar la acción asociada al objeto en la partida.
     * @param partida La partida en curso.
     * @param jugador El jugador que realiza la acción.
     */
    fun accion(partida: Partida, jugador: Jugador):Any
}

/**
 * Clase que representa un objeto "Cigarro" en el juego.
 */
class Cigarro : Objeto {
    override fun accion(partida: Partida, jugador: Jugador) {
        jugador.anadirVida()
    }

    override fun toString(): String {
        return "Cigarro \uD83D\uDEAC -> El cigarro te cura 1 de vida"
    }
}

/**
 * Clase que representa un objeto "Lupa" en el juego.
 */
class Lupa : Objeto {
    override fun accion(partida: Partida, jugador: Jugador):Boolean {
        if (partida.arma.cargador.isNotEmpty() && partida.arma.cargador[0].cargado) {
            println("Este cartucho está cargado")
            return true
        } else {
            println("Este cartucho está descargado")
            return false
        }
    }

    override fun toString(): String {
        return "Lupa \uD83D\uDD0D -> Te permite ver el siguiente cartucho de cargador"
    }
}

/**
 * Clase que representa un objeto "Refresco" en el juego.
 */
class Refresco : Objeto {
    override fun accion(partida: Partida, jugador: Jugador) {
        if (partida.arma.cargador.isNotEmpty() && partida.arma.cargador[0].cargado) {
            println("Este cartucho estaba cargado")
        } else println("Este cartucho estaba descargado")
        partida.arma.cargador.removeAt(0)
    }

    override fun toString(): String {
        return "Refresco \uD83C\uDF7A -> Descarga un cartucho"
    }
}

/**
 * Clase que representa un objeto "Sierra" en el juego.
 */
class Sierra : Objeto {
    override fun accion(partida: Partida, jugador: Jugador) {
        partida.danio *= 2
    }

    override fun toString(): String {
        return "Sierra \uD83D\uDD2A -> Duplica el daño por 1 tiro"
    }
}

/**
 * Clase que representa un objeto "Esposas" en el juego.
 */
class Esposas : Objeto {
    override fun accion(partida: Partida, jugador: Jugador) {
        partida.saltarTurno = true
    }

    override fun toString(): String {
        return "Esposas ⛓ -> Te saltarás el turno del otro jugador"
    }
}
