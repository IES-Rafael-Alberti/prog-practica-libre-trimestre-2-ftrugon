/**
 * Interfaz que define un objeto del juego.
 */
interface Objeto {
    /**
     * Método para realizar la acción asociada al objeto en la partida.
     * @param partida La partida en curso.
     * @param jugador El jugador que realiza la acción.
     */
    fun accion(partida: Partida, jugador: Jugador):String
}

/**
 * Clase que representa un objeto "Cigarro" en el juego.
 */
class Cigarro : Objeto {
    override fun accion(partida: Partida, jugador: Jugador):String {
        jugador.anadirVida()
        return "Te has curado 1 de vida"
    }

    override fun toString(): String {
        return "Cigarro \uD83D\uDEAC -> El cigarro te cura 1 de vida"
    }
}

/**
 * Clase que representa un objeto "Lupa" en el juego.
 */
class Lupa : Objeto {
    override fun accion(partida: Partida, jugador: Jugador):String {
        return if (partida.arma.cargador.isNotEmpty() && partida.arma.cargador[0].cargado) {
            "Este cartucho está cargado"
        } else {
            "Este cartucho está descargado"
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
    override fun accion(partida: Partida, jugador: Jugador): String {
        val copiaPrimeraBala = partida.arma.cargador[0]
        partida.arma.cargador.remove(partida.arma.cargador[0])
        return if (copiaPrimeraBala.cargado) {
            "Este cartucho estaba cargado"
        } else "Este cartucho estaba descargado"

    }

    override fun toString(): String {
        return "Refresco \uD83C\uDF7A -> Descarga un cartucho"
    }
}

/**
 * Clase que representa un objeto "Sierra" en el juego.
 */
class Sierra : Objeto {
    override fun accion(partida: Partida, jugador: Jugador): String {
        partida.danio *= 2
        return "El daño se ha multiplicado por 2"
    }

    override fun toString(): String {
        return "Sierra \uD83D\uDD2A -> Duplica el daño por 1 tiro"
    }
}

/**
 * Clase que representa un objeto "Esposas" en el juego.
 */
class Esposas : Objeto {
    override fun accion(partida: Partida, jugador: Jugador): String {
        partida.saltarTurno = true
        return "Te saltaras el turno del rival"
    }

    override fun toString(): String {
        return "Esposas ⛓ -> Te saltarás el turno del otro jugador"
    }
}
