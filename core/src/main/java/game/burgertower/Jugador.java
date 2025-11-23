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

public class Jugador {

    private Rectangle bucket;
    private Texture bucketImage;
    private Sound sonidoHerido;

    private int vidas = 3;
    private int puntos = 0;
    private int velx = 400;
    private boolean herido = false;
    private int tiempoHeridoMax = 50;
    private int tiempoHerido;

    private List<Ingrediente> stack;
    private float baseHeight = 25f;
    private float currentHeight = 0;
    private int maxIngredients = 17;
    private float alturaVisibleIngrediente = 18f;
    private int ingredientsCount = 0;


    private CalculadoraPuntaje calculadoraPuntos;

    public Jugador() {
        this.bucketImage = Recursos.getInstancia().texPanInf;
        this.sonidoHerido = Recursos.getInstancia().hurtSound;
        this.stack = new ArrayList<>();
        this.calculadoraPuntos = new PuntajeNormal();
    }


    public void setEstrategiaPuntaje(CalculadoraPuntaje nuevaCalculadora) {
        this.calculadoraPuntos = nuevaCalculadora;
    }

    public int getVidas() { return vidas; }
    public int getPuntos() { return puntos; }
    public Rectangle getArea() { return bucket; }
    public boolean estaHerido() { return herido; }
    public int getCantIng() { return ingredientsCount; }

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

        batch.draw(bucketImage, bucket.x, bucket.y + offsetY);

        float yActual = bucket.y + baseHeight;
        for (Ingrediente ing : stack) {
            batch.draw(ing.getTextura(), bucket.x, yActual + offsetY);
            yActual += alturaVisibleIngrediente;
        }
    }

    public void agregarIngrediente(Ingrediente ing) {
        if (stack.size() < maxIngredients) {
            stack.add(ing);
            currentHeight += alturaVisibleIngrediente;
            recalcularHitbox();
            ingredientsCount++;
        }
    }

    public void closeSandwich() {
        if (stack.size() <= 1) return;

        int valorBase = 0;
        for (Ingrediente i : stack) {
            if (i instanceof IngredienteBueno) {
                valorBase += ((IngredienteBueno) i).getValor();
            }
        }

        int puntosGanados = calculadoraPuntos.calcularPuntos(valorBase, ingredientsCount);

        puntos += puntosGanados;
        ingredientsCount = 0;

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
        ingredientsCount = 0;
    }

    public void reset() {
        stack.clear();
        currentHeight = 0;
        recalcularHitbox();
    }

    public void reiniciar() {
        this.vidas = 3;
        this.puntos = 0;
        this.bucket.x = 368;
        this.bucket.y = 20;
        stack.clear();
    }

    public void destruir() {
    }
}
