
/**
 * La clase torre es la clase principal del problema StackingCups
 * En esta se maneja la lógica general y las operaciones posibles del juego.
 * 
 * @author Jeronimo Moreno
 * @version (0.1) Simulation test
 */
public class Tower
{
 
    private int width;
    private int maxHeight;
    private StackingItem[] items;
    private boolean ok;
    private boolean isVisible;
    private int size;
    /**
     * Constructor for objects of class Tower
     */
    public Tower(int width, int maxHeight) {
        this.width = width;
        this.maxHeight = maxHeight;
        this.items = new StackingItem[100];
        this.ok = true;
        this.isVisible = false;
    }
    
    private StackingItem getItem(int index) 
    {
        return items[index];
    }
    
    public int height() 
    {
        int total = 0;
    
        for(int i = 0; i < size; i++) {
            total += items[i].getHeight();
        }
    
        return total;
    }
    public void pushCup(int id, String color) {
    
        if(checkItem(id, Cup.class) != null) {
            ok = false;
            return;
        }
    
        items[size] = new Cup(id, color);
        size++;
        ok = true;
    }

    public void pushLid(int id) {
    
        Cup cup = (Cup) checkItem(id, Cup.class);
    
        if(cup == null) {
            ok = false;
            return;
        }
        if(cup.hasLid()) {
            ok = false;
            return;
        }
        if(checkItem(id, Lid.class) != null) {
            ok = false;
            return;
        }
        items[size] = new Lid(id, cup.getColor());
        size++;
        cup.addLid();
        ok = true;
    }


    
    private StackingItem checkItem(int id, Class<?> type) 
    {
    
        for(int i = 0; i < size; i++) {
            if(type.isInstance(items[i]) && items[i].getId() == id) {
                return items[i];
            }
        }
        return null;
    }

    
    public boolean ok() 
    {
        return ok;
    }
    
    public void makeVisible() 
    {
    
        for(int i = 0; i < size; i++) {
    
            items[i].makeVisible();
        }
    
        isVisible = true;
    }
    public void makeInvisible() 
    {
    
        for(int i = 0; i < size; i++) {
    
            items[i].makeInvisible();
        }
    
        isVisible = false;
    }
    


}