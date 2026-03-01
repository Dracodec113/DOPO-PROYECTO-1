import java.util.HashMap;
import java.util.*;
import javax.swing.JOptionPane;
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
    
    public Tower(int cups){
        this(cups * 20, cups * 20);
        int i;
        for(i = 1; i <= cups; i++){
            pushCup(i);
        }
    }
    //Consultamos con IA y dice que podemos hacer un MultiMap para dejar los siguientes métodos en el mismo lugar. No lo haremos hasta preguntar.
    
    public void  makeVisible(){
        ruler.draw();

    }
    
    public void pushCup(int id){
        int randomColor;
        if(!checkLid(-id)){
            Random rand = new Random();
            randomColor=rand.nextInt(5);
        }
        else{
            randomColor = items.get(-id).colorNum;
        }
        
        if(!checkCup(id)){
            Cup cup = new Cup(id, colors.get(randomColor), (2*id) - 1, 125, 125, randomColor);
            items.put(id, cup);
            order.add(id);
            makeVisible();
        }
        drawTower();
        makeVisible();
    }
    
    public void pushLid(int id){
        int randomColor;
        if(!checkCup(id)){
            Random rand = new Random();
            randomColor=rand.nextInt(5);
        }
        else{
            randomColor = items.get(id).colorNum;
        }
        if(!checkLid(id)){
            Lid lid = new Lid(id, colors.get(randomColor), 125, 140, randomColor);
            items.put(-id, lid);
            order.add(-id);
            makeVisible();
        }
        drawTower();
        makeVisible();
    }
    
    public void removeCup(int id){
        if(checkCup(id)){
            items.get(id).eraseShape();
            items.remove(id);
            order.remove(Integer.valueOf(id));
        }
        drawTower();
        makeVisible();
    }
    
    public void removeLid(int id){
        if(checkLid(id)){
            items.get(-id).eraseShape();
            items.remove(-id);
            order.remove(Integer.valueOf(-id));
        }
        drawTower();
        makeVisible();
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
        makeVisible();
    }
    
    public void cover(){
        ArrayList<Integer> positions = new ArrayList<>();
        for(Integer id:items.keySet()){
            
            int cup=Math.abs(id);
            if (!positions.contains(cup)){
                    positions.add(cup);
            }
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
        makeVisible();
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
        drawTower();
        makeVisible();
    }
    
    public void swap(String[] item1, String[] item2){
        int idSwap1, idSwap2;
        
        if(item1[0].equals("cup")){ idSwap1 =Integer.parseInt(item1[1]); }
        else{idSwap1=-Integer.parseInt(item1[1]); }
        
        if(item2[0].equals("cup")){ idSwap2 =Integer.parseInt(item2[1]); }
        else{idSwap2 =-Integer.parseInt(item2[1]); }
        
        int posSwap1 = order.indexOf(idSwap1);
        int posSwap2 = order.indexOf(idSwap2);
        
        int aux = order.get(posSwap1);
        order.set(posSwap1,idSwap2); 
        order.set(posSwap2,aux); 
        
        drawTower();
        makeVisible();
    }
    
    public void liddedCups(){
        ArrayList<Integer> positions = new ArrayList<>();
        for(int i=0;i<order.size()-1;i++){
            if(order.get(i)==-order.get(i+1)){
                positions.add(order.get(i));
            }
            Collections.sort(positions);
        }
        System.out.println(positions.toString());
    }
    
    private boolean checkCup(int id){
        return items.containsKey(id);
    }
    
    private boolean checkLid(int id){
        return items.containsKey(-id);
    }
    
    private void drawTower() {
        int baseY = 600;
        int currentX = 125;
    
        // [width, x, y, hasLid, height]
        java.util.Deque<int[]> stack = new java.util.ArrayDeque<>();
    
        for (Integer id : order) {
            StackingItem item = items.get(id);
            int itemWidth = Math.abs(id) * 20;
            int itemHeight = item.getHeight();
            int floorThickness = 20;
    
            if (id > 0) {
                // Buscar contenedor válido: más ancho y sin lid
                // NO sacamos copas más pequeñas solo buscamos el primer válido
                int[] validContainer = null;
                int[] topItem = null; // el item más reciente dentro del contenedor válido
    
                for (int[] entry : stack) { // stack itera de tope a fondo
                    if (entry[0] > itemWidth && entry[3] == 0) {
                        validContainer = entry;
                        break;
                    } else if (entry[0] > itemWidth && entry[3] == 1) {
                        // tiene lid, no puede anidar
                        break;
                    }
                    if (topItem == null) topItem = entry;
                }
    
                if (validContainer != null) {
                    // la base del item toca la cima del topItem dentro del contenedor
                    // o el piso del contenedor si no hay nada dentro
                    int offset = (validContainer[0] - itemWidth) / 2;
                    currentX = validContainer[1] + offset;
    
                    if (topItem != null && topItem[0] < validContainer[0]) {
                        // hay algo dentro, apoyarse encima de ese algo
                        currentX = topItem[1] + (topItem[0] - itemWidth) / 2;
                        baseY = topItem[2]; // cima del item de abajo
                    } else {
                        baseY = validContainer[2] + validContainer[4] - floorThickness;
                    }
    
                } else {
                    // Va encima de la torre
                    currentX = 125;
                }
    
                int currentY = baseY - itemHeight;
                baseY = currentY;
                stack.push(new int[]{itemWidth, currentX, currentY, 0, itemHeight});
                item.redraw(currentX, currentY);
    
            } else { // lid
                if (!stack.isEmpty()) stack.peek()[3] = 1;
                int currentY = baseY - itemHeight;
                baseY = currentY;
                item.redraw(currentX, currentY);
            }
        }
        makeVisible();
    }
    
    public void exit()
    {
        int op = JOptionPane.showConfirmDialog(null,"¿Deseas salir?","Confirmación",JOptionPane.YES_NO_CANCEL_OPTION); // Selección generada por IA.
        if (op == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
        else
        {
            drawTower();
        }

    }
}