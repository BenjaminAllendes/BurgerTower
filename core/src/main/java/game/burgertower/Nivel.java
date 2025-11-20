package game.burgertower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Nivel {

    private Array<Ingrediente> ingredientesCaen;
    private long lastDropTime;
    private long lastBreadTime; 
    private BitmapFont font;
    private boolean gameOver = false;

    public Nivel() {
    }

    public void crear() {
        ingredientesCaen = new Array<>();
        crearIngrediente(); 
        lastBreadTime = TimeUtils.nanoTime();

        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(1.3f);

        Recursos.getInstancia().rainMusic.setLooping(true);
        Recursos.getInstancia().rainMusic.play();
    }

    private void crearIngrediente() {
        Ingrediente nuevoIngrediente;
        int tipo = MathUtils.random(1, 10); 

        // Referencia rápida al Singleton
        Recursos r = Recursos.getInstancia();

        // Timer pan superior
        if(TimeUtils.nanoTime() - lastBreadTime > 15000000000L) {
            nuevoIngrediente = new PanSuperior(r.texPanSup);
            lastBreadTime = TimeUtils.nanoTime();
        }
        else if (tipo <= 2) {
            nuevoIngrediente = new IngredienteMalo(r.texMalo);
        }
        else if (tipo <= 4) {
            nuevoIngrediente = new IngredienteBueno(r.texCarne, 20);
        }
        else if (tipo <= 6) {
            nuevoIngrediente = new IngredienteBueno(r.texLechuga, 10);
        }
        else if (tipo == 7) {
            nuevoIngrediente = new IngredienteBueno(r.texTocino, 15);
        }
        else if (tipo == 8) {
            nuevoIngrediente = new IngredienteBueno(r.texQueso, 15);
        }
        else {
            nuevoIngrediente = new IngredienteBueno(r.texTomate, 10);
        }

        nuevoIngrediente.getArea().x = MathUtils.random(0, 800 - 64);
        nuevoIngrediente.getArea().y = 480;

        ingredientesCaen.add(nuevoIngrediente);
        lastDropTime = TimeUtils.nanoTime();
    }

    public void actualizarMovimiento(Jugador jugador) {
        if (gameOver) return; 
        if (TimeUtils.nanoTime() - lastDropTime > 1000000000) crearIngrediente(); 

        for (int i = 0; i < ingredientesCaen.size; i++) {
            Ingrediente ing = ingredientesCaen.get(i);
            ing.actualizarPosicion(Gdx.graphics.getDeltaTime());

            if (ing.getArea().y + 64 < 0) {
                ingredientesCaen.removeIndex(i);
            }

            if (ing.getArea().overlaps(jugador.getArea())) {
                ((Interactuable) ing).interactuarCon(jugador);

                // Sonidos desde Singleton
                if (ing instanceof IngredienteBueno) {
                    Recursos.getInstancia().dropSound.play();
                }
                else if (ing instanceof PanSuperior) {
                    Recursos.getInstancia().biteSound.play();
                }
                ingredientesCaen.removeIndex(i);
            }

            if (jugador.getVidas() <= 0) {
                Recursos.getInstancia().gameOverSound.play();
                gameOver = true;
                Recursos.getInstancia().rainMusic.stop();
            }
        }
    }

    public void actualizarDibujoLluvia(SpriteBatch batch, Jugador jugador) {
        for (Ingrediente ing : ingredientesCaen) {
            ing.dibujar(batch);
        }
        if (gameOver) {
            font.draw(batch, "¡JUEGO TERMINADO!", 290, 300);
            font.draw(batch, "PUNTAJE: " + jugador.getPuntos(), 330, 260);
            font.draw(batch, "Presiona R para reiniciar", 280, 200);

            if (Gdx.input.isKeyPressed(Input.Keys.R)) {
                reiniciar(jugador);
            }
        }
    }

    private void reiniciar(Jugador jugador) {
        ingredientesCaen.clear();
        jugador.reiniciar();
        gameOver = false;
        Recursos.getInstancia().rainMusic.play();
    }

    public void destruir() {
        font.dispose();
        // No destruimos texturas aquí, lo hace el Singleton
    }
}