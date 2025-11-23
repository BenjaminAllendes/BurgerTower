package game.burgertower;

public class PuntajeNormal implements CalculadoraPuntaje {
    @Override
    public int calcularPuntos(int valorBase, int cantidadIngredientes) {
        float multiplicador = 1.0f;

        // Esta es la lógica que sacamos de tu clase Jugador original
        if (cantidadIngredientes >= 10 && cantidadIngredientes <= 16) {
            multiplicador = 1.5f;
        } else if (cantidadIngredientes >= 17) {
            multiplicador = 2.0f;
        }

        return (int) (valorBase * multiplicador);
    }
}
