package domain;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * OpenerCup: Elimina todas las tapas que le impiden el paso.
 * Una tapa impide el paso si su tamaño (id) es menor al de esta taza.
 */
public class OpenerCup extends Cup {

    public OpenerCup(int id, String color, int x, int y, int colorNum, String type) {
        super(id, color, x, y, colorNum, type);
    }

    /**
     * Al entrar a la torre, identifica las tapas menores que ella para eliminarlas.
     */
    @Override
    public ArrayList<Integer> onPush(ArrayList<String> currentOrder, 
                                     HashMap<String, StackingItem> items, 
                                     String myKey) {

        this.key = myKey;
        
        ArrayList<Integer> toRemove = new ArrayList<>();

        for (String currentKey : currentOrder) {
            StackingItem item = items.get(currentKey);

            if (item instanceof Lid) {
                if (item.id < this.id) {
                    toRemove.add(item.id);
                }
            }
        }
        currentOrder.add(myKey);
        return toRemove;
    }
}