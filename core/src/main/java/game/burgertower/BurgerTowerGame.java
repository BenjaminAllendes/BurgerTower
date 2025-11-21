package game.burgertower;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class BurgerTowerGame extends ApplicationAdapter {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;

    private Jugador jugador;
    private Nivel nivel;

    @Override
    public void create() {
        Recursos.getInstancia().cargar();

        font = new BitmapFont();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();

        jugador = new Jugador();
        jugador.crear();

        nivel = new Nivel();
        nivel.crear();
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        // Dibujar textos
        font.draw(batch, "Puntos: " + jugador.getPuntos(), 5, 475);
        font.draw(batch, "Vidas : " + jugador.getVidas(), 720, 475);
        font.draw(batch, "Tamaño Torre : " + jugador.getCantIng(), 5, 450);

        if (!jugador.estaHerido()) {
            jugador.actualizarMovimiento();
            nivel.actualizarMovimiento(jugador);
        }

        jugador.dibujar(batch);
        nivel.actualizarDibujoLluvia(batch, jugador);

        batch.end();
    }

    @Override
    public void dispose() {
        jugador.destruir();
        nivel.destruir();
        batch.dispose();
        font.dispose();

        Recursos.getInstancia().dispose();
    }
}
