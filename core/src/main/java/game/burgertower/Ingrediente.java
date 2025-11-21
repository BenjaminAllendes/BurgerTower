/*
package game.burgertower;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Ingrediente {

    // Todas las subclases tendrán esto (Encapsulamiento)
    protected Rectangle area;
    protected Texture textura;
    protected int velocidadCaida;

    public Ingrediente(Texture textura) {
        this.textura = textura;
        this.area = new Rectangle();
        this.area.width = 64;  //
        this.area.height = 64; //
        this.velocidadCaida = 200; //
    }

    public abstract void actualizarPosicion(float delta);
    public void dibujar(SpriteBatch batch) {
        batch.draw(textura, area.x, area.y);
    }

    public Rectangle getArea() {
        return area;
    }

    public Texture getTextura() {
        return textura;
    }
}
*/
package game.burgertower;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Ingrediente {

    protected Rectangle area;
    protected Texture textura;
    protected int velocidadCaida;

    // Nuevo: Promovimos esto aquí porque casi todos lo usan para funciones senoidales
    protected float tiempoVida = 0;

    public Ingrediente(Texture textura) {
        this.textura = textura;
        this.area = new Rectangle();
        this.area.width = 64;
        this.area.height = 64;
        this.velocidadCaida = 200;
    }

    // ---------------------------------------------------------
    // PATRÓN TEMPLATE METHOD
    // ---------------------------------------------------------

    // 1. El "Esqueleto" del algoritmo (final para que nadie lo rompa)
    public final void actualizarPosicion(float delta) {
        // Paso A: Actualizar tiempo (Hook común)
        tiempoVida += delta;

        // Paso B: Movimiento Vertical (Lógica compartida: Gravedad)
        area.y -= velocidadCaida * delta;

        // Paso C: Movimiento Horizontal (Lógica específica: CADA HIJO DECIDE)
        float desplazamientoX = calcularMovimientoHorizontal(delta);
        area.x += desplazamientoX;

        // Paso D: Restricciones (Lógica compartida: Límites de pantalla)
        verificarLimitesPantalla();
    }

    // 2. El método "Primitivo" o Abstracto que los hijos DEBEN implementar
    protected abstract float calcularMovimientoHorizontal(float delta);

    // 3. Método auxiliar compartido (Reutilización de código)
    private void verificarLimitesPantalla() {
        if (area.x < 0) area.x = 0;
        if (area.x > 800 - 64) area.x = 800 - 64;
    }

    // ---------------------------------------------------------

    public void dibujar(SpriteBatch batch) {
        batch.draw(textura, area.x, area.y);
    }

    public Rectangle getArea() { return area; }
    public Texture getTextura() { return textura; }
}
