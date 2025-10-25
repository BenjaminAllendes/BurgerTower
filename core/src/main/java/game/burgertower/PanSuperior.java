package game.burgertower;

import com.badlogic.gdx.graphics.Texture;

public class PanSuperior extends Ingrediente implements Interactuable {

    public PanSuperior(Texture textura) {
        super(textura);
        this.velocidadCaida = 150; // Más lento, para dar tiempo
    }

    @Override
    public void interactuarCon(Tarro tarro) {
        // Al chocar, le decimos al tarro que "cierre" el sándwich
        tarro.closeSandwich();
    }
}