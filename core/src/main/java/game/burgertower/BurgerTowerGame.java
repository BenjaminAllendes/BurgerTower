package game.burgertower;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;



public class BurgerTowerGame extends ApplicationAdapter {
       private OrthographicCamera camera;
	   private SpriteBatch batch;
	   private BitmapFont font;

	   private Jugador jugador;
	   private Nivel nivel;

	   private Texture texPanInf;
	   private Texture texLechuga;
	   private Texture texCarne;
	   private Texture texMalo;
	   private Texture texPanSup;
	   private Texture texTocino;
	   private Texture texTomate;
	   private Texture texQueso;

	   @Override
	    public void create() {
	        font = new BitmapFont();

	        // --- Cargar Nuevos Assets ---
	        texPanInf = new Texture(Gdx.files.internal("Pan_inferior.png"));
	        texLechuga = new Texture(Gdx.files.internal("lechuga.png"));
	        texCarne = new Texture(Gdx.files.internal("carne.png"));
	        texMalo = new Texture(Gdx.files.internal("malo.png"));
	        texPanSup = new Texture(Gdx.files.internal("pan_superior.png"));
	        texTocino = new Texture(Gdx.files.internal("tocino.png")) ;
	        texTomate = new Texture(Gdx.files.internal("tomate.png")) ;
	        texQueso = new Texture(Gdx.files.internal("queso.png")) ;

	        Sound hurtSound = Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));
	        Sound dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
	        Music rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

	        jugador = new Jugador(texPanInf, hurtSound);
	        jugador.crear();

	        nivel = new Nivel(texLechuga, texCarne, texMalo, texPanSup, texTocino, texTomate, texQueso, dropSound, rainMusic);
	        nivel.crear();

	        camera = new OrthographicCamera();
	        camera.setToOrtho(false, 800, 480);
	        batch = new SpriteBatch();
	    }


	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0.2f, 1);
		camera.update();
		//actualizar
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		//dibujar textos
		font.draw(batch, "Puntos: " + jugador.getPuntos(), 5, 475);
		font.draw(batch, "Vidas : " + jugador.getVidas(), 720, 475);

		if (!jugador.estaHerido()) {
			// movimiento del jugador desde teclado
	        jugador.actualizarMovimiento();
			// caida de la lluvia
	        nivel.actualizarMovimiento(jugador);
		}

		jugador.dibujar(batch);
        nivel.actualizarDibujoLluvia(batch, jugador);


		batch.end();

	}

	@Override
	public void dispose () {
	      jugador.destruir();
          nivel.destruir();
	      batch.dispose();
	      font.dispose();
	}
}

