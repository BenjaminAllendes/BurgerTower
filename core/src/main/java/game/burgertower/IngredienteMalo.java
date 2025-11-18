package game.burgertower;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
public class IngredienteMalo extends Ingrediente implements Interactuable {
	private float tiempoVida = 0 ;
    public IngredienteMalo(Texture textura) {
        super(textura);
        this.velocidadCaida = 300; // Más rápido
    }
    @Override
    public void actualizarPosicion(float delta) {
        tiempoVida += delta;

        // 1. Cae hacia abajo
        area.y -= velocidadCaida * delta;

        // 2. Movimiento Zig-Zag en X
        // MathUtils.sin(tiempo * velocidad) * amplitud
        float oscilacion = MathUtils.sin(tiempoVida * 10) * 500 * delta; 
        area.x += oscilacion;

        // 3. Evitar que se salga de la pantalla por el zig-zag
        if (area.x < 0) area.x = 0;
        if (area.x > 800 - 64) area.x = 800 - 64;
    }
    @Override
    public void interactuarCon(Jugador jugador) {
        jugador.dañar();
        jugador.reset();
    }
}
