import java.util.HashMap;
import java.util.*;

/**
 * La clase torre es la clase principal del problema StackingCups
 * En esta se maneja la lógica general y las operaciones posibles del juego.
 * id: Funciona de tamaño también.
 * 
 * @author Jeronimo Moreno
 * @version (0.1) Simulation test
 */
public class Tower
{
    private int height;
    private int width;
    private HashMap<Integer, StackingItem> items;
    private ArrayList<Integer> order;
    
    public Tower(int height, int width){
        this.height = height;
        this.width = width;
        this.items = new HashMap<>();
        this.order = new ArrayList<>();
    }
    
    //Consultamos con IA y dice que podemos hacer un MultiMap para dejar los siguientes métodos en el mismo lugar. No lo haremos hasta preguntar.
    
    public void pushCup(int id){
        if(!checkCup(id)){
            Cup cup = new Cup(1, "yellow", 1, 125, 125);
            items.put(id, cup);
            order.add(id);
        }
    }
    
    public void pushLid(int id){
         if(!checkLid(id)){
            Lid lid = new Lid(1, "yellow", 125, 140);
            items.put(-id, lid);
            order.add(-id);
        }
    }
    
    public void removeCup(int id){
        if(checkCup(id)){
            items.remove(id);
            order.remove(Integer.valueOf(id));
        }
    }
    
    public void removeLid(int id){
        if(checkLid(id)){
            items.remove(id);
            order.remove(Integer.valueOf(-id));
        }
    }
    
    public void orderTower(){
        ArrayList<Integer> positions = new ArrayList<>();
        
        for(Integer id:items.keySet()){
            
            int cup=Math.abs(id);//Agrega primero tasas si existe.
            if (!positions.contains(cup)){
                    positions.add(cup);
            }
            Collections.sort(positions, Collections.reverseOrder());
        }
        order.clear();
        for(Integer pos:positions) {
            if (items.containsKey(pos)) {
                order.add(pos);
            }

            if (items.containsKey(-pos)) {
                order.add(-pos);
            }
        }
    }
    
    public void reverseTower(){
        ArrayList<Integer> positions = new ArrayList<>();
        
        for(Integer id:items.keySet()){
            
            int cup = Math.abs(id);//Agrega primero tasas si existe.
            if (!positions.contains(cup)){
                    positions.add(cup);
            }
            Collections.sort(positions);
        }
        order.clear();
        for(Integer pos:positions) {
            if (items.containsKey(pos)) {
                order.add(pos);
            }

            if (items.containsKey(-pos)) {
                order.add(-pos);
            }
        }
    }
    
    private boolean checkCup(int id){
        return items.containsKey(id);
    }
    
    private boolean checkLid(int id){
        return items.containsKey(-id);
    }
}