package domain;

import java.awt.geom.Path2D;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.HashMap;

// Agregamos implements Removable para que Tower pueda preguntar canRemove
public class Cup extends StackingItem implements Stackable, Removable {
    
    protected String type; 
    private static final int CELL_SIZE = 20;

    // Añadimos String type al parámetro del constructor
    public Cup(int id, String color, int x, int y, int colorNum, String type) {
        super(id, color, x, y, colorNum);
        this.type = type; // Ahora sí se asigna correctamente
        
        int cellsWide = (2 * id) - 1;
        this.width = cellsWide * CELL_SIZE; 
        this.height = cellsWide * CELL_SIZE;
    }

    @Override
    protected Shape getShape() {
        Path2D.Double path = new Path2D.Double();
        int thickness = CELL_SIZE; 

        path.moveTo(x, y);
        path.lineTo(x, y + height); 
        path.lineTo(x + width, y + height); 
        path.lineTo(x + width, y); 
        path.lineTo(x + width - thickness, y);
        path.lineTo(x + width - thickness, y + height - thickness);
        path.lineTo(x + thickness, y + height - thickness);
        path.lineTo(x + thickness, y);
        path.closePath();

        return path;
    }

    @Override
    public ArrayList<Integer> onPush(ArrayList<String> currentOrder, HashMap<String, StackingItem> items, String myKey) {
        this.key = myKey; 
        currentOrder.add(myKey);
        return new ArrayList<>(); 
    }

    @Override
    public boolean canRemove(ArrayList<String> order) {
        return true; 
    }
}