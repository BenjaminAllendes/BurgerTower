package game.burgertower;

public interface IngredienteFactory {

    Ingrediente crearPanSuperior();
    Ingrediente crearIngredienteMalo();
    Ingrediente crearCarne();
    Ingrediente crearLechuga();
    Ingrediente crearTocino();
    Ingrediente crearQueso();
    Ingrediente crearTomate();
}
