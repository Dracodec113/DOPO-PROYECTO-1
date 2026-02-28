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
    private HashMap<Integer, Cup> cups;
    private HashMap<Integer, Lid> lids;

    public Tower(int height, int width){
        this.height = height;
        this.width = width;
    }
    
    //Consultamos con IA y dice que podemos hacer un MultiMap para dejar los siguientes métodos en el mismo lugar. No lo haremos hasta preguntar.
    
    public void pushCup(int id){
        if(!checkCup(id)){
            Cup cup = new Cup();
            cups.put(id, cup);
        }
    }
    
    public void pushLid(int id){
         if(!checkLid(id)){
            Lid lid = new Lid();
            lids.put(id, lid);
        }
    }
    
    public void removeCup(int id){
        if(checkCup(id)){
            cups.remove(id);
        }
    }
    public void removeLid(int id){
        if(checkLid(id)){
            lids.remove(id);
        }
    }
    public void orderTower(){
        
    }
    private boolean checkCup(int id){
        return cups.containsKey(id);
    }
    
    private boolean checkLid(int id){
        return cups.containsKey(id);
    }
}