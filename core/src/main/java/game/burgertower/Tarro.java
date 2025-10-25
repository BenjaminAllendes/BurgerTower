package game.burgertower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase Tarro: representa el "plato" del jugador con el pan inferior.
 * Controla stack de ingredientes, puntaje, vidas y colisiones.
 */
public class Tarro {

    private Rectangle bucket;              // Área de colisión del pan inferior
    private Texture bucketImage;           // Textura del pan inferior
    private Sound sonidoHerido;            // Sonido al recibir daño
    private int vidas = 3;
    private int puntos = 0;
    private int velx = 400;
    private boolean herido = false;
    private int tiempoHeridoMax = 50;
    private int tiempoHerido;

    // Pila de ingredientes
    private List<Ingrediente> stack;
    private float baseHeight = 30f;        // Altura del pan inferior
    private float ingredientHeight = 64;   // Altura de cada ingrediente
    private float currentHeight = 0;       // Altura total del sandwich
    private int sandwichCount = 0;
    private int maxIngredients = 10;       // Limite de ingredientes por sandwich
    private float alturaVisibleIngrediente = 20f;
    // ==================== Constructor ====================
    public Tarro(Texture tex, Sound sonidoHerido) {
        this.bucketImage = tex;
        this.sonidoHerido = sonidoHerido;
        this.stack = new ArrayList<>();
    }

    // ==================== Getters ====================
    public int getVidas() { return vidas; }
    public int getPuntos() { return puntos; }
    public Rectangle getArea() { return bucket; }
    public float getCurrentHeight() { return currentHeight; }
    public boolean estaHerido() { return herido; }
    public int getSandwichCount() { return sandwichCount; }

    // ==================== Métodos principales ====================
    public void crear() {
        bucket = new Rectangle();
        bucket.x = 800 / 2f - 64 / 2f;
        bucket.y = 20;
        bucket.width = 64;
        bucket.height = baseHeight;
        stack.clear();
        currentHeight = 0;
    }

    public void actualizarMovimiento() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= velx * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += velx * Gdx.graphics.getDeltaTime();

        // Limites pantalla
        if (bucket.x < 0) bucket.x = 0;
        if (bucket.x > 800 - bucket.width) bucket.x = 800 - bucket.width;
    }

    public void dibujar(SpriteBatch batch) {
        float offsetY = 0;
        if (herido) {
            offsetY = MathUtils.random(-10, 10);
            tiempoHerido--;
            if (tiempoHerido <= 0) herido = false;
        }

        // 1. Dibuja el pan inferior (el jugador)
        batch.draw(bucketImage, bucket.x, bucket.y + offsetY);

        // 2. Dibuja el stack de ingredientes
        //    Comienza a dibujar justo encima del pan (baseHeight)
        float yActual = bucket.y + baseHeight;

        for (Ingrediente ing : stack) {
            // Dibuja la textura del ingrediente en la posición del stack
            batch.draw(ing.getTextura(), bucket.x, yActual + offsetY);

            // Incrementa la altura para el *siguiente* ingrediente
            yActual += alturaVisibleIngrediente;
        }
    }


    /**
     * Agrega ingrediente al stack y suma puntos si es bueno
	*/
    public void agregarIngrediente(Ingrediente ing) {
        if (stack.size() < maxIngredients) {
            stack.add(ing);
            currentHeight += alturaVisibleIngrediente;
            recalcularHitbox();


        } else {
            // Penalización por sandwich demasiado alto
            puntos -= 3;
        }
    }

    /**
     * Cierra el sandwich al recibir pan superior y aplica puntaje total
	 */
    public void closeSandwich() {
        if (stack.size() <= 1) return; // nada que cerrar

        int valorBase = 0;
        for (Ingrediente i : stack) {
            if (i instanceof IngredienteBueno) {
                valorBase += ((IngredienteBueno) i).getValor();
            }
        }

        int puntosGanados = valorBase ;
        puntos += puntosGanados;
        sandwichCount++;

        // Reinicia stack con pan inferior
        stack.clear();
        currentHeight = 0;
        recalcularHitbox();
    }

    private void recalcularHitbox() {
        bucket.height = baseHeight + currentHeight;
    }

    public void dañar() {
        vidas--;
        herido = true;
        tiempoHerido = tiempoHeridoMax;
        sonidoHerido.play();
    }

    public void reset() {
        stack.clear();
        currentHeight = 0;
        recalcularHitbox();
    }

    public void destruir() {
        bucketImage.dispose();
    }

    public boolean estaVivo() {
        return vidas > 0;
    }


    public void reiniciar() {
        this.vidas = 3;
        this.puntos = 0;
        this.bucket.x = 368; // posición inicial centrada aprox.
        this.bucket.y = 20;
        stack.clear();
    }

}
