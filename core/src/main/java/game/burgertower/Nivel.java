

package game.burgertower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

// Renombrada de Lluvia a Nivel
public class Nivel {

    private Array<Ingrediente> ingredientesCaen;

    private long lastDropTime;
    private long lastBreadTime; // Timer para el pan superior

    // Texturas que el nivel puede crear
    private Texture texLechuga;
    private Texture texCarne;
    private Texture texMalo;
    private Texture texPanSup;

    private Sound dropSound;
    private Music rainMusic;

    // NUEVO: fuente y estado de juego
    private BitmapFont font;
    private boolean gameOver = false;


    // Constructor actualizado para recibir las nuevas texturas
    public Nivel(Texture texLechuga, Texture texCarne, Texture texMalo, Texture texPanSup, Sound ss, Music mm) {
        this.texLechuga = texLechuga;
        this.texCarne = texCarne;
        this.texMalo = texMalo;
        this.texPanSup = texPanSup;

        this.rainMusic = mm;
        this.dropSound = ss;
    }

    public void crear() {
        // Inicializamos la nueva lista
        ingredientesCaen = new Array<>();
        crearIngrediente(); // Creamos el primero
        lastBreadTime = TimeUtils.nanoTime();

        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(1.3f);

        rainMusic.setLooping(true);
        rainMusic.play();
    }

    // Método actualizado para crear INGREDIENTES
    private void crearIngrediente() {
        Ingrediente nuevoIngrediente;
        int tipo = MathUtils.random(1, 10);

        // Timer de 15 segundos para el pan DE ARRIBA
        if(TimeUtils.nanoTime() - lastBreadTime > 15000000000L) {
            nuevoIngrediente = new PanSuperior(texPanSup); // <-- CORRECTO
            lastBreadTime = TimeUtils.nanoTime();
        }
        // 20% de chance de ser malo
        else if (tipo <= 2) {
            nuevoIngrediente = new IngredienteMalo(texMalo);
        }
        // 40% de chance de ser carne
        else if (tipo <= 6) {
            nuevoIngrediente = new IngredienteBueno(texCarne, 20);
        }
        // 40% de chance de ser lechuga
        else {
            nuevoIngrediente = new IngredienteBueno(texLechuga, 10);
        }


        // (Aquí no debe haber ninguna lógica que cree un texPanInf)

        nuevoIngrediente.getArea().x = MathUtils.random(0, 800 - 64);
        nuevoIngrediente.getArea().y = 480;

        ingredientesCaen.add(nuevoIngrediente);
        lastDropTime = TimeUtils.nanoTime();
    }
    // Método de actualización totalmente refactorizado
    public void actualizarMovimiento(Tarro tarro) {

        if (gameOver) return; // si terminó el juego, no seguir
        // generar más ingredientes
        if (TimeUtils.nanoTime() - lastDropTime > 1000000000) crearIngrediente(); // 1 por segundo

        // revisar si los ingredientes cayeron al suelo o chocaron con el tarro
        for (int i = 0; i < ingredientesCaen.size; i++) {
            Ingrediente ing = ingredientesCaen.get(i);

            // 1. Mover el ingrediente (Polimorfismo)
            ing.actualizarPosicion(Gdx.graphics.getDeltaTime());

            // 2. Cae al suelo y se elimina
            if (ing.getArea().y + 64 < 0) {
                ingredientesCaen.removeIndex(i);
            }

            // 3. Choca con el tarro
            if (ing.getArea().overlaps(tarro.getArea())) {

                ((Interactuable) ing).interactuarCon(tarro);

                if (ing instanceof IngredienteBueno) {
                    dropSound.play();
                }

                ingredientesCaen.removeIndex(i);
            }

            //  DETECTAR GAME OVER
            if (tarro.getVidas() <= 0) {
                gameOver = true;
                rainMusic.stop();
            }

        }
    }

    // Método de dibujo actualizado
    public void actualizarDibujoLluvia(SpriteBatch batch, Tarro tarro) {
        for (Ingrediente ing : ingredientesCaen) {
            ing.dibujar(batch);
        }
        if (gameOver) {
            font.draw(batch, "¡JUEGO TERMINADO!", 290, 300);
            font.draw(batch, "PUNTAJE: " + tarro.getPuntos(), 330, 260);
            font.draw(batch, "Presiona R para reiniciar", 280, 200);

            // Si se presiona R, reiniciar el juego
            if (Gdx.input.isKeyPressed(Input.Keys.R)) {
                reiniciar(tarro);
            }
        }
    }

    private void reiniciar(Tarro tarro) {
        ingredientesCaen.clear();
        tarro.reiniciar();
        gameOver = false;
        rainMusic.play();
    }

    public void destruir() {
        dropSound.dispose();
        rainMusic.dispose();
        font.dispose();
    }
}

