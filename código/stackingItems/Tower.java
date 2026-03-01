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
    private HashMap<Integer,String> colors;
    private Ruler ruler;
    public Tower(int height, int width){
        this.height = height;
        this.width = width;
        this.items = new HashMap<>();
        this.order = new ArrayList<>();
        this.colors= new HashMap<>(){{
            put(0,"black");
            put(1,"red");
            put(2,"blue");
            put(3,"yellow");
            put(4,"magenta");
            put(5,"green");
        }};
        ruler=new Ruler(this.height);
    }
    
    //Consultamos con IA y dice que podemos hacer un MultiMap para dejar los siguientes métodos en el mismo lugar. No lo haremos hasta preguntar.
    
    public void  makeVisible(){
        ruler.draw();

    }
    
    public void pushCup(int id){
        Random rand=new Random();
        int randomColor=rand.nextInt(5);
        if(!checkCup(id)){
            Cup cup = new Cup(id, colors.get(randomColor), id, 125, 125);
            items.put(id, cup);
            order.add(id);
            makeVisible();
        }
        drawTower();
    }
    
    public void pushLid(int id){
        Random rand=new Random();
        int randomColor=rand.nextInt(5);
         if(!checkLid(id)){
            Lid lid = new Lid(id, colors.get(randomColor), 125, 140);
            items.put(-id, lid);
            order.add(-id);
            makeVisible();
        }
        drawTower();
    }
    
    public void removeCup(int id){
        if(checkCup(id)){
            items.get(id).eraseShape();
            items.remove(id);
            order.remove(Integer.valueOf(id));
        }
        drawTower();
    }
    
    public void removeLid(int id){
        if(checkLid(id)){
            items.get(id).eraseShape();
            items.remove(id);
            order.remove(Integer.valueOf(-id));
        }
        drawTower();
    }
    
    public void orderTower(){
        ArrayList<Integer> positions = new ArrayList<>();
        
        for(Integer id:items.keySet()){
            
            int cup=Math.abs(id);
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
        drawTower();
    }
    
    public void reverseTower(){
        ArrayList<Integer> positions = new ArrayList<>();
        
        for(Integer id:items.keySet()){
            
            int cup = Math.abs(id);
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
    
    public void drawTower() {
        int baseY = 600;
        int currentY = baseY;
        
        for (Integer id : order) {
            StackingItem item = items.get(id);
            if (id < 0) {
                currentY -= item.getHeight();
                item.redraw(125, currentY);
            } else {
                currentY -= item.getHeight();
                item.redraw(125, currentY);
            }
        }
        makeVisible();
    }
}