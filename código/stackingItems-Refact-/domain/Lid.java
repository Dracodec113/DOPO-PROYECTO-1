package domain;

import java.awt.Rectangle;
import java.awt.Shape;
import java.util.*;

/**
 * Lid representa una tapa normal para una taza en la torre.
 * Una tapa normal puede ser removida en cualquier momento sin restricciones.
 *
 * El parámetro 'size' define el ancho visual de la tapa para que coincida
 * exactamente con el ancho de su taza compañera.
 *
 * @author Jeronimo Moreno and Juan David Rangel
 * @version Ciclo 4
 */
public class Lid extends StackingItem implements Removable {
    protected String type;
    
    /**
     * Constructor de Lid.
     * @param id       Identificador de la tapa (coincide con el id de su taza).
     * @param color    Color de la tapa.
     * @param x        Posición X inicial en el canvas.
     * @param y        Posición Y inicial en el canvas.
     * @param size     Tamaño de la taza compañera (determina el ancho visual).
     * @param colorNum Índice numérico del color.
     */
    public Lid(int id, String color, int x, int y, int size, int colorNum) {
        super(id, color, x, y, colorNum);
        this.type=type;
        this.height = 20;
        int cellsWide = (2 * size) - 1;
        this.width = cellsWide * 20;
    }

    /**
     * Define la forma visual de la tapa: un rectángulo plano de 20px de alto.
     * @return Shape rectangular que representa la tapa.
     */
    @Override
    protected Shape getShape() {
        return new Rectangle(x, y, width, height);
    }

    /**
     * Una tapa normal siempre puede ser removida.
     * Las subclases (FearfulLid) sobreescriben este método para añadir restricciones.
     *
     * @param currentOrder Lista actual de keys en la torre.
     * @return true siempre para la tapa normal.
     */
    @Override
    public boolean canRemove(ArrayList<String> currentOrder) {
        return true;
    }
    
    public ArrayList<Integer> onPush(ArrayList<String> currentOrder, HashMap<String, StackingItem> items, String myKey) {
        this.key = myKey; 
        currentOrder.add(myKey);
        return new ArrayList<>();
    }

}
