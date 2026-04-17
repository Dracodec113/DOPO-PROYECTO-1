package domain;

import java.util.ArrayList;
import java.util.HashMap;

public class OpenerCup extends Cup {

    public OpenerCup(int id, String color, int x, int y, int colorNum, String type) {
        super(id, color, x, y, colorNum, type);
    }
    
    @Override
    public ArrayList<Integer> onPush(ArrayList<String> currentOrder,HashMap<String, StackingItem> items,String myKey) {
        this.key = myKey;

        for (int i = currentOrder.size() - 1; i >= 0; i--) {
            String currentKey = currentOrder.get(i);
            StackingItem item = items.get(currentKey);

            if (item instanceof Lid) {
                item.erase(); 
                
                items.remove(currentKey);
                
                currentOrder.remove(i); 
                
            } else if (item instanceof Cup) {
                break;
            }
        }
        currentOrder.add(myKey);
        return new ArrayList<>(); 
    }
}