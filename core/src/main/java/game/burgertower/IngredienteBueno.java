package game.burgertower;

import com.badlogic.gdx.graphics.Texture;

public class IngredienteBueno extends Ingrediente implements Interactuable {

    private int valor; // Puntos

    public IngredienteBueno(Texture textura, int valor) {
        super(textura);
        this.valor = valor;
    }
    public void actualizarPosicion(float delta) {
        area.y -= velocidadCaida * delta;
    }
    public int getValor() {
        return valor;
    }

    @Override
    public void interactuarCon(Jugador jugador) {
        jugador.agregarIngrediente(this);
    }
}
