package presentation;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * TowerGrid maneja la representación visual de la torre.
 * Se corrigió el error de drawLine usando rectángulos delgados como líneas.
 */
public class TowerGrid {
    private static final int CELL_SIZE = 20;  
    private static final int CANVAS_X  = 125; 
    private static final int CANVAS_Y  = 600; 

    private domain.StackingItem[][] grid;
    private int rows;
    private int cols;

    public TowerGrid(int heightCells, int widthCells) {
        this.rows = heightCells;
        this.cols = widthCells;
        this.grid = new domain.StackingItem[rows][cols];
        clearGrid();
    }

    /**
     * Dibuja la cuadrícula usando rectángulos muy delgados para simular líneas.
     * Esto evita que el método drawLine dé error y previene el fondo negro.
     */
    public void drawDebugGrid() {
        Canvas canvas = Canvas.getCanvas();
        int widthPx = cols * CELL_SIZE;
        int heightPx = rows * CELL_SIZE;
        
        // Dibujar "líneas" verticales (rectángulos de 1px de ancho)
        for (int c = 0; c <= cols; c++) {
            int x = CANVAS_X + (c * CELL_SIZE);
            String id = "v-line-" + c;
            canvas.draw(id, "lightgray", new Rectangle(x, CANVAS_Y - heightPx, 1, heightPx));
        }
        
        // Dibujar "líneas" horizontales (rectángulos de 1px de alto)
        for (int r = 0; r <= rows; r++) {
            int y = CANVAS_Y - (r * CELL_SIZE);
            String id = "h-line-" + r;
            canvas.draw(id, "lightgray", new Rectangle(CANVAS_X, y, widthPx, 1));
        }
    }

    public boolean rebuild(ArrayList<String> order, HashMap<String, domain.StackingItem> items) {
            clearGrid(); 
            int centerCol = cols / 2;
    
            for (String key : order) {
                domain.StackingItem item = items.get(key);
                if (item == null) continue;
    
                int itemW = item.getWidth() / CELL_SIZE;
                int itemH = item.getHeight() / CELL_SIZE;
                int startC = centerCol - (itemW / 2);
    
                if (itemH > rows || itemW > cols || startC < 0) return false;
    
                int targetRow = 0;
                
                // CORRECCIÓN AQUÍ: Agregamos 'item' al final de la llamada
                while (targetRow + itemH < rows && canFitDeeper(targetRow + 1, startC, itemW, itemH, item)) {
                    targetRow++;
                }
                fillGrid(targetRow, startC, itemW, itemH, item);
            }
            render(); 
            return true;
        }

    private boolean canFitDeeper(int r, int c, int w, int h, domain.StackingItem item) {
        // Si se sale de los bordes del canvas, no puede bajar más
        if (r + h > rows) return false;
    
        for (int i = r; i < r + h; i++) {
            for (int j = c; j < c + w; j++) {
                if (!inGrid(i, j)) return false;
                
                domain.StackingItem occupant = grid[i][j];
                if (occupant != null && occupant != item) {
                    // Si lo que intenta bajar es una TAPA:
                    // Se detiene si choca con el suelo de una taza o con otra tapa
                    if (item instanceof domain.Lid) {
                        return false; 
                    }
                    
                    // Si lo que intenta bajar es una TAZA:
                    // Solo choca si sus paredes (bordes) o suelo tocan algo
                    boolean isMyBorder = (i == r + h - 1 || j == c || j == c + w - 1);
                    if (isMyBorder) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean isCupFloor(domain.StackingItem item, int r, int c) {
        if (item == null || item instanceof domain.Lid) return false;
        // Un objeto es suelo si estamos en su última fila de ocupación
        // En nuestro grid, si r es la fila actual y el item está ahí, 
        // y la fila de abajo está vacía o es otro item, es un punto de apoyo.
        return true; 
    }
    
    private void fillGrid(int r, int c, int w, int h, domain.StackingItem item) {
        for (int i = r; i < r + h; i++) {
            for (int j = c; j < c + w; j++) {
                if (inGrid(i, j)) {
                    // Si es TAPA: Llena toda su forma (normalmente 1 fila de alto)
                    if (item instanceof domain.Lid) {
                        grid[i][j] = item;
                    } 
                    // Si es TAZA: Solo registramos las paredes y el suelo
                    // Esto deja las celdas internas como NULL (vacías)
                    else {
                        boolean isLeftWall = (j == c);
                        boolean isRightWall = (j == c + w - 1);
                        boolean isFloor = (i == r + h - 1);
                        
                        if (isLeftWall || isRightWall || isFloor) {
                            grid[i][j] = item;
                        }
                    }
                }
            }
        }
    } 

    public void render() {
        HashSet<domain.StackingItem> drawn = new HashSet<>();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                domain.StackingItem item = grid[r][c];
                if (item != null && !drawn.contains(item)) {
                    int pixelX = CANVAS_X + (c * CELL_SIZE);
                    int pixelY = CANVAS_Y - ((rows - r) * CELL_SIZE);
                    item.redraw(pixelX, pixelY);
                    drawn.add(item);
                }
            }
        }
    }

    // Métodos de utilidad
    public int getOccupiedHeight() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] != null) return rows - r;
            }
        }
        return 0;
    }

    public domain.StackingItem getCell(int row, int col) {
        return inGrid(row, col) ? grid[row][col] : null;
    }

    public boolean isEmpty(int row, int col) {
        return inGrid(row, col) && grid[row][col] == null;
    }

    public int getMaxHeight() { return rows; }
    public int getMaxWidth()  { return cols; }

    private void clearGrid() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = null;
            }
        }
    }

    private boolean inGrid(int row, int col) {
        return (row >= 0 && row < rows && col >= 0 && col < cols);
    }
    
    public void clearBackground() {
        Canvas canvas = Canvas.getCanvas();
        int widthPx = cols * CELL_SIZE;
        int heightPx = rows * CELL_SIZE;
        // Dibuja un gran rectángulo blanco sobre todo el área del grid
        // para borrar cualquier rastro de objetos anteriores.
        canvas.draw("white_background", "white", new Rectangle(CANVAS_X, CANVAS_Y - heightPx, widthPx, heightPx));
    }
    
}
