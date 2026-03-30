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
    private int lastColor;
    private int currentHeight;
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
    //La sobrecarga del método tower.
    public Tower(int cups){
        this(cups * 20, cups * 20);
        int i;
        for(i = 1; i <= cups; i++){
            pushCup(i);
        }
    }
    
    public void  makeVisible(){
        ruler.draw();

    }
    public void makeInvisible(){
        ruler.draw();
    }
    
    public void pushCup(int id){
        int randomColor;
        Random rand = new Random();
        if(!checkLid(-id)){
            do{
                randomColor=rand.nextInt(5);
            }while(randomColor == lastColor);
        }
        else{
            randomColor = items.get(-id).colorNum;
        }
        
        if(!checkCup(id)){
            Cup cup = new Cup(id, colors.get(randomColor), (2*id) - 1, 125, 125, randomColor);
            items.put(id, cup);
            order.add(id);
            drawTower();
            makeVisible();
        }
        lastColor = randomColor;
        drawTower();
        makeVisible();
    }
    
    public void pushLid(int id){
        int randomColor;
        Random rand = new Random();
        if(!checkCup(id)){
            do{
            randomColor=rand.nextInt(5);
            }while(lastColor == randomColor);
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
        lastColor = randomColor;
        drawTower();
        makeVisible();
    }
    public void pushLid(String type, int id) {
        int randomColor;
        Random rand = new Random();
        if (!checkCup(id)) {
            do {
                randomColor = rand.nextInt(5);
            } while (lastColor == randomColor);
        } else {
            randomColor = items.get(id).colorNum;
        }
    
        if (!checkLid(id)) {
            Lid lid;
            if (type.equals("crazy")) {
                lid = new CrazyLid(id, colors.get(randomColor), 125, 140, randomColor);
            } else {
                lid = new Lid(id, colors.get(randomColor), 125, 140, randomColor);
            }
            items.put(-id, lid);
            order.add(0, -id);
            makeVisible();
        }
        lastColor = randomColor;
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

    public void swapToReduce(){
        ArrayList<Integer> cas=new ArrayList<>(order);
        int cont = 0;
    
        for (int i=0;i<order.size();i++){
            for (int j=0;j<cas.size()-1-i;j++) {
                if (Math.abs(cas.get(j))<Math.abs(cas.get(j+1))) {
                    cont++;
                    System.out.println("Cambia a el id:"+ cas.get(j)+" por " + cas.get(j+1));
                    
                    int aux = cas.get(j);
                    cas.set(j, cas.get(j+1));
                    cas.set(j+1, aux);
                }
            }
        }
        System.out.println("No se puede bajar más la altura de la torre.");
    }
    
    
    public ArrayList<Integer> liddedCups(){
        ArrayList<Integer> positions = new ArrayList<>();
        for(int i=0;i<order.size()-1;i++){
            if(order.get(i)==-order.get(i+1)){
                positions.add(order.get(i));
            }
            Collections.sort(positions);
        }
        return positions;
    }
    
    private boolean checkCup(int id){
        return items.containsKey(id);
    }
    
    private boolean checkLid(int id){
        return items.containsKey(-id);
    }
    
    private void drawTower() {
        int groundLevel = 600;
        int currentX = 125;
        java.util.Deque<int[]> stack = new java.util.ArrayDeque<>();
        int currentBaseY = groundLevel; 
        int topPointY = groundLevel; 
    
        for (Integer id : order) {
            StackingItem item = items.get(id);

            int[] placement = item.placeCorrectPosition(currentBaseY, currentX, stack);
            
            if (placement[2] < topPointY) {
                topPointY = placement[2];
            }
    
            if (stack.isEmpty() || placement[0] >= stack.peek()[0]) {
                 currentBaseY = placement[2];
            }
    
            stack.push(placement);
            item.redraw(placement[1], placement[2]);
        }
        currentHeight = (groundLevel - topPointY) / 20; 
        
    }
    
    public String[][] stackingItems(){
        String[][] result = new String[order.size()][2];
        for(int i = 0; i < order.size(); i++){
            int id = order.get(i);
            if(id > 0){
                result[i][0] = "cup";
            } else {
                result[i][0] = "lid";
            }
            result[i][1] = String.valueOf(Math.abs(id));
        }
        return result;
    }
    
    public boolean ok(){
        return true;
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
    
    public int height(){
        return currentHeight;
    }
}