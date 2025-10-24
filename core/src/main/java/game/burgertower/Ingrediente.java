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

    // Método concreto que las subclases heredan
    public void actualizarPosicion(float delta) {
        area.y -= velocidadCaida * delta;
    }

    // Método concreto que las subclases heredan
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