package domain;

import java.awt.geom.Path2D;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.HashMap;

public class Cup extends StackingItem implements Stackable {
    
    // CELL_SIZE debe ser 20 para coincidir con TowerGrid
    private static final int CELL_SIZE = 20;

    public Cup(int id, String color, int x, int y, int colorNum) {
        super(id, color, x, y, colorNum);
        
        // El ancho debe ser impar para que la torre tenga un eje central
        // id 1 -> 1 celda | id 2 -> 3 celdas | id 3 -> 5 celdas
        int cellsWide = (2 * id) - 1;
        
        this.width = cellsWide * CELL_SIZE; 
        this.height = cellsWide * CELL_SIZE; // O la altura que prefieras
    }

    @Override
    protected Shape getShape() {
        Path2D.Double path = new Path2D.Double();
        
        // Para que se vea como una taza que ocupa cuadros enteros:
        // El grosor de la pared será de exactamente 1 celda (20px)
        int thickness = CELL_SIZE; 

        // 1. Empezamos en la esquina superior izquierda (x, y)
        path.moveTo(x, y);
        
        // 2. Bajamos hasta el fondo (Pared izquierda externa)
        path.lineTo(x, y + height); 
        
        // 3. Vamos a la derecha (Suelo externo)
        path.lineTo(x + width, y + height); 
        
        // 4. Subimos (Pared derecha externa)
        path.lineTo(x + width, y); 
        
        // --- HUECO INTERNO (La "U" por dentro) ---
        
        // 5. Entramos el grosor de la pared derecha
        path.lineTo(x + width - thickness, y);
        
        // 6. Bajamos hasta el fondo interno (dejando el suelo de 20px)
        path.lineTo(x + width - thickness, y + height - thickness);
        
        // 7. Vamos a la izquierda hasta la pared interna izquierda
        path.lineTo(x + thickness, y + height - thickness);
        
        // 8. Subimos hasta el tope de la pared izquierda
        path.lineTo(x + thickness, y);
        
        path.closePath();

        return path;
    }

    @Override
    public ArrayList<Integer> onPush(ArrayList<String> currentOrder, HashMap<String, StackingItem> items) {
        return new ArrayList<>(); 
    }
}
