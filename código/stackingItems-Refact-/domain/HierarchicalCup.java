package domain;

import java.util.*;

public class HierarchicalCup extends Cup {
    
    public HierarchicalCup(int id, String color, int x, int y, int colorNum) {
        super(id, color, x, y, colorNum);
    }

    @Override
    public ArrayList<Integer> onPush(ArrayList<String> currentOrder, HashMap<String, StackingItem> items) {
        // 1. Identificar mi llave exacta (debe coincidir con la de Tower)
        String myKey = "hierarchical-" + this.id;
        
        // 2. Si Tower ya me agregó al final, me quito para reubicarme
        currentOrder.remove(myKey);
        
        // 3. Buscar la posición correcta (Índice 0 es el fondo de la torre)
        // Queremos que los IDs más grandes estén abajo (índices menores)
        int insertAt = currentOrder.size(); // Por defecto, va al final (arriba)
        
        for (int i = 0; i < currentOrder.size(); i++) {
            String currentKey = currentOrder.get(i);
            try {
                String[] parts = currentKey.split("-");
                int currentId = Integer.parseInt(parts[parts.length - 1]);
                
                // Si mi ID es mayor al de la posición actual, yo debo ir DEBAJO de él
                if (this.id > currentId) {
                    insertAt = i;
                    break;
                }
            } catch (Exception e) {
                continue;
            }
        }
        
        // 4. Insertar en la posición calculada
        currentOrder.add(insertAt, myKey);
        
        return new ArrayList<>(); // No elimina a nadie
    }
}
