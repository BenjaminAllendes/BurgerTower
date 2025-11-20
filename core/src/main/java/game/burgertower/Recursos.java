package game.burgertower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class Recursos {

    private static Recursos instancia;

    // Texturas
    public Texture texPanInf;
    public Texture texLechuga;
    public Texture texCarne;
    public Texture texMalo;
    public Texture texPanSup;
    public Texture texTocino;
    public Texture texTomate;
    public Texture texQueso;

    // Sonidos
    public Sound hurtSound;
    public Sound dropSound;
    public Sound biteSound;
    public Sound gameOverSound;
    public Music rainMusic;

    private Recursos() {
    }

    public static Recursos getInstancia() {
        if (instancia == null) {
            instancia = new Recursos();
        }
        return instancia;
    }

    public void cargar() {
        texPanInf = new Texture(Gdx.files.internal("Pan_inferior.png"));
        texLechuga = new Texture(Gdx.files.internal("lechuga.png"));
        texCarne = new Texture(Gdx.files.internal("carne.png"));
        texMalo = new Texture(Gdx.files.internal("malo.png"));
        texPanSup = new Texture(Gdx.files.internal("pan_superior.png"));
        texTocino = new Texture(Gdx.files.internal("tocino.png"));
        texTomate = new Texture(Gdx.files.internal("tomate.png"));
        texQueso = new Texture(Gdx.files.internal("queso.png"));

        hurtSound = Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));
        dropSound = Gdx.audio.newSound(Gdx.files.internal("pop.mp3"));
        biteSound = Gdx.audio.newSound(Gdx.files.internal("bite.wav"));
        gameOverSound = Gdx.audio.newSound(Gdx.files.internal("GameOver.mp3"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
    }

    public void dispose() {
        texPanInf.dispose();
        texLechuga.dispose();
        texCarne.dispose();
        texMalo.dispose();
        texPanSup.dispose();
        texTocino.dispose();
        texTomate.dispose();
        texQueso.dispose();
        hurtSound.dispose();
        dropSound.dispose();
        biteSound.dispose();
        gameOverSound.dispose();
        rainMusic.dispose();
    }
}
