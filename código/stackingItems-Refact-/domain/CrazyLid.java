package domain;

import java.util.ArrayList;

/**
 * CrazyLid: en lugar de tapar a su taza, se ubica de base de la misma.
 * * @author Jeronimo Moreno and Juan David Rangel
 * @version Ciclo 4
 */
public class CrazyLid extends Lid {

    public CrazyLid(int id, String color, int x, int y, int size, int colorNum) {
        super(id, color, x, y, size, colorNum);
    }

    /**
     * Implementación polimórfica: busca la taza del mismo ID 
     * y se inserta justo en su misma posición (desplazándola hacia arriba).
     */

    public void addToOrder(ArrayList<String> order, String myKey) {
        String myCupKey = "Cup" + this.id; // Asumiendo que el formato de key es Cup + id
        
        int cupIndex = order.indexOf(myCupKey);
        
        if (cupIndex != -1) {
            // Si la taza está, la tapa se mete en su índice (la base de la taza)
            order.add(cupIndex, myKey);
        } else {
            // Si la taza no está, el enunciado no especifica, 
            // pero podrías ponerla al fondo de la torre por defecto.
            order.add(0, myKey);
        }
    }

    @Override
    public boolean canRemove(ArrayList<String> currentOrder) {
        return true;
    }
}