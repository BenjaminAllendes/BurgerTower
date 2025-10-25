package game.burgertower;

import com.badlogic.gdx.graphics.Texture;

public class IngredienteMalo extends Ingrediente implements Interactuable {

    public IngredienteMalo(Texture textura) {
        super(textura);
        this.velocidadCaida = 300; // Más rápido
    }

    @Override
    public void interactuarCon(Tarro tarro) {
        tarro.dañar();
        tarro.reset();
    }
}