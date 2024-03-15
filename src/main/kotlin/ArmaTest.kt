import org.junit.Assert
import org.junit.Test

class ArmaTest {

    @Test
    fun disparo() {


        val arma = Escopeta(4,1,"Escopeta")

        val cargador = mutableListOf(
            Cartucho(true),
            Cartucho(false),
            Cartucho(false),
            Cartucho(false),
            Cartucho(false)
        )

        arma.cargador = cargador

        val resultado = arma.disparo(cargador)

        Assert.assertTrue(resultado)

    }
}