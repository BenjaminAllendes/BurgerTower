/*
package game.burgertower;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
public class PanSuperior extends Ingrediente implements Interactuable {

	private float tiempoVida = 0;
    public PanSuperior(Texture textura) {
        super(textura);
        this.velocidadCaida = 150; // Más lento, para dar tiempo
    }
    @Override
    public void actualizarPosicion(float delta) {
        tiempoVida += delta;

        // Cae despacio
        area.y -= velocidadCaida * delta;

        // Oscilación suave y amplia (tipo hoja cayendo)
        float oscilacion = MathUtils.cos(tiempoVida * 3) * 150 * delta;
        area.x += oscilacion;

        // Mantener en pantalla
        if (area.x < 0) area.x = 0;
        if (area.x > 800 - 64) area.x = 800 - 64;
    }
    @Override
    public void interactuarCon(Jugador jugador) {
        // Al chocar, le decimos al jugador que "cierre" el sándwich
        jugador.closeSandwich();
    }
}
*/
package game.burgertower;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

public class PanSuperior extends Ingrediente implements Interactuable {

    public PanSuperior(Texture textura) {
        super(textura);
        this.velocidadCaida = 150;
    }

    @Override
    protected float calcularMovimientoHorizontal(float delta) {
        // Implementación específica: Oscilación suave (Coseno)
        return MathUtils.cos(tiempoVida * 3) * 150 * delta;
    }

    @Override
    public void interactuarCon(Jugador jugador) {
        jugador.closeSandwich();
    }
}
