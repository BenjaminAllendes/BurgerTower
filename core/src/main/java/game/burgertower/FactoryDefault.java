package game.burgertower;

public class FactoryDefault implements IngredienteFactory {
    private final Recursos r = Recursos.getInstancia();

    @Override
    public Ingrediente crearPanSuperior() {
        return new PanSuperior(r.texPanSup);
    }

    @Override
    public Ingrediente crearIngredienteMalo() {
        return new IngredienteMalo(r.texMalo);
    }

    @Override
    public Ingrediente crearCarne() {
        return new IngredienteBueno(r.texCarne, 20);
    }

    @Override
    public Ingrediente crearLechuga() {
        return new IngredienteBueno(r.texLechuga, 10);
    }

    @Override
    public Ingrediente crearTocino() {
        return new IngredienteBueno(r.texTocino, 15);
    }

    @Override
    public Ingrediente crearQueso() {
        return new IngredienteBueno(r.texQueso, 15);
    }

    @Override
    public Ingrediente crearTomate() {
        return new IngredienteBueno(r.texTomate, 10);
    }
}
