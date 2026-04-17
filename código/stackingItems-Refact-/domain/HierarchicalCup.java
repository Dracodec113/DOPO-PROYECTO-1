package domain;

import java.util.*;

/**
 * HierarchicalCup: Al entrar, desplaza hacia arriba todos los objetos de menor tamaño.
 * Si queda en el fondo (índice 0), no se puede remover.
 */
public class HierarchicalCup extends Cup {
    
    public HierarchicalCup(int id, String color, int x, int y, int colorNum, String type) {
        super(id, color, x, y, colorNum, type);
    }

    @Override
    public ArrayList<Integer> onPush(ArrayList<String> currentOrder, HashMap<String, StackingItem> items, String myKey) {
        this.key = myKey;

        int insertAt = currentOrder.size(); 
 
        for (int i = 0; i < currentOrder.size(); i++) {
            String otherKey = currentOrder.get(i);
            StackingItem otherItem = items.get(otherKey);
            
            if (otherItem != null) {

                if (this.id > otherItem.id) {
                    insertAt = i;
                    break;
                }
            }
        }
        
        currentOrder.add(insertAt, myKey);
        
        return new ArrayList<Integer>();
    }
    
    @Override
    public boolean canRemove(ArrayList<String> order) {
        if (order.indexOf(this.key) == 0) {
            return false;
        }
        return true;
    }
}