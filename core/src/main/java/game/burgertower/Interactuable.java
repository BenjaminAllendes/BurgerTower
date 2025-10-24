package game.burgertower;

/**
 * Interfaz para objetos que pueden interactuar con el Tarro.
 * Responsable únicamente de la interacción lógica al colisionar.
 */
public interface Interactuable {

    /**
     * Método que define la acción que ocurrirá al colisionar con el Tarro.
     * Cada clase concreta decide si suma puntos, daña, cierra sandwich, etc.
     *
     * @param tarro el jugador que colisionó con el objeto
     */
    void interactuarCon(Tarro tarro);
}
