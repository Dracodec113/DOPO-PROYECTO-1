package domain;

import java.util.*;
import javax.swing.JOptionPane;

/**
 * La clase torre es la clase principal del problema StackingCups
 * En esta se maneja la lógica general y las operaciones posibles del juego.
 *
 * @author Jeronimo Moreno and Juan David Rangel
 * @version Ciclo 4 - Refactoring Polimórfico
 */
public class Tower {
    private int canvasHeight;
    private int canvasWidth;
    private int lastColorIndex;
    private presentation.TowerGrid grid;
    private int maxRows;
    private int maxCols;
    
    private HashMap<String, StackingItem> items; 
    private ArrayList<String> order;             
        
    private final String[] COLORS = {"black", "red", "blue", "yellow", "magenta", "green"};
    private presentation.Ruler ruler;
    
    // Constructor principal
    public Tower(int height, int width) {
        this.canvasHeight = height;
        this.canvasWidth = width;
    
        this.maxRows = height / 20;
        this.maxCols = width / 20;
        
        this.grid = new presentation.TowerGrid(this.maxRows, this.maxCols);
        this.items = new HashMap<>();
        this.order = new ArrayList<>();
  
        this.ruler = new presentation.Ruler(this.canvasHeight);
        this.ruler.draw(); 
    
        this.lastColorIndex = -1;
    
        this.grid.drawDebugGrid(); 
    }
    
    public Tower(int cups) {
        this(600, 600); 
        for (int i = 1; i <= cups; i++) {
            pushCup(i);
        }
    }
        
    public void makeVisible() {
        if (ruler != null) ruler.draw();
        drawTower(); 
    }

    public void makeInvisible() {
        drawTower();
    }
        
    private String makeKey(String type, int id) {
        return type.toLowerCase() + "-" + id;
    }
    
    /**
     * Búsqueda polimórfica: Ya no dependemos del texto exacto de la llave.
     * Evaluamos directamente si el objeto instanciado es Cup o Lid.
     */
    private boolean checkCup(int id) {
        return findKey("Cup", id) != null;
    }

    private boolean checkLid(int id) {
        return findKey("Lid", id) != null;
    }
    
    private int getRandomColorIndex() {
        Random rand = new Random();
        int newColor;
        do {
            newColor = rand.nextInt(COLORS.length);
        } while (newColor == lastColorIndex);
        return newColor;
    }
    
    public void pushCup(int id) {
        pushCup("normalCup", id);
    }
    
    public void pushCup(String type, int id) {
        if (!isValidType(type)) {
            showError("El tipo '" + type + "' no es una taza válida.");
            return;
        }
    
        if (!checkCup(id)) {
            int colorIndex = checkLid(id) ? items.get(findKey("Lid", id)).colorNum : getRandomColorIndex();
            String key = makeKey(type, id);
            Cup cup;
            
            // CORRECCIÓN: Se agrega el parámetro 'type' requerido por el nuevo constructor de Cup
            if (type.equalsIgnoreCase("opener")) {
                // Asumiendo que OpenerCup recibe los mismos parámetros
                cup = new OpenerCup(id, COLORS[colorIndex], 0, 0, colorIndex, type);
            } else if (type.equalsIgnoreCase("hierarchical")) {
                cup = new HierarchicalCup(id, COLORS[colorIndex], 0, 0, colorIndex, type);
            } else {
                cup = new Cup(id, COLORS[colorIndex], 0, 0, colorIndex, type);
            }
            
            // Guardamos la referencia en el diccionario
            items.put(key, cup);
            
            // CORRECCIÓN: Le pasamos el control absoluto al objeto para que se inserte en el order.
            // Eliminamos la llamada directa a order.add(key)
            ArrayList<Integer> affected = cup.onPush(order, items, key);
            
            processSpecialEffects(cup, affected);
            lastColorIndex = colorIndex;
            drawTower(); 
        }
    }
    
    private void drawTower() {
        grid.clearBackground(); 
    
        if (!grid.rebuild(this.order, this.items)) {
            return;
        }
    
        grid.drawDebugGrid();
    
        for (StackingItem item : items.values()) {
            item.makeVisible(); 
        }
    
        grid.render();
    
        if (this.ruler != null) {
            this.ruler.draw(); 
        }
    }

    public void pushLid(int id) {
        pushLid("normal", id);
    }
 
    public void pushLid(String type, int id) {
        if (!isValidLidType(type)) {
            showError("Tipo de tapa inválida.");
            return;
        }
    
        if (!checkLid(id)) {
            if (type.equalsIgnoreCase("fearful") && !checkCup(id)) {
                showError("FearfulLid " + id + " no puede entrar: su taza compañera no está en la torre.");
                return;
            }
    
            int colorIndex = checkCup(id) ? items.get(findKey("Cup", id)).colorNum : getRandomColorIndex();
            String key = makeKey(type, id);
            int size = id;
    
            Lid lid;
            if (type.equalsIgnoreCase("crazy")) {
                lid = new CrazyLid(id, COLORS[colorIndex], 0, 0, size, colorIndex);
            } else if (type.equalsIgnoreCase("fearful")) {
                // CORRECCIÓN: Tu código original creaba un Lid normal en lugar de FearfulLid
                lid = new FearfulLid(id, COLORS[colorIndex], 0, 0, size, colorIndex);
            } else {
                lid = new Lid(id, COLORS[colorIndex], 0, 0, size, colorIndex);
            }
    
            items.put(key, lid);
            
            // CORRECCIÓN: El objeto Lid decide dónde ubicarse usando el parámetro key.
            ArrayList<Integer> affected = lid.onPush(order, items, key);
    
            processSpecialEffects(null, affected); 
            lastColorIndex = colorIndex;
            drawTower();
        }
    }
    
    public void popCup() {
        if (order.isEmpty()) {
            showError("La torre está vacía.");
            return;
        }
    
        String topKey = order.get(order.size() - 1);
        StackingItem item = items.get(topKey);
    
        if (item instanceof Lid) {
            showError("El tope de la torre es una tapa (" + topKey + "), no una taza.\n" +
                      "Usa popLid() para sacar tapas.");
            return;
        }
    
        if (item != null) item.erase();
        items.remove(topKey);
        order.remove(order.size() - 1);
     
        drawTower();
    }

    public void popLid() {
        if (order.isEmpty()) {
            showError("La torre está vacía.");
            return;
        }
    
        String topKey = order.get(order.size() - 1);
        StackingItem item = items.get(topKey);
 
        if (!(item instanceof Lid)) {
            showError("El tope de la torre es una taza (" + topKey + "), no una tapa.\n" +
                      "Usa popCup() para sacar tazas.");
            return;
        }
    
        if (item != null) item.erase();
        items.remove(topKey);
        order.remove(order.size() - 1);
     
        drawTower();
    }

    public void removeCup(int id) {
        String key = findKey("Cup", id);
        if (key != null) {
            executeRemoval(key);
        } else {
            showError("No se encontró ninguna taza con ID " + id);
        }
    }  

    public void removeLid(int id) {
        String key = findKey("Lid", id); 
        
        if (key != null) {
            executeRemoval(key);
        } else {
            showError("No se encontró ninguna tapa con ID " + id);
        }
    }

    private void executeRemoval(String key) {
        StackingItem item = items.get(key);
        if (item == null) return;
    
        if (item instanceof Removable) {
            if (!((Removable) item).canRemove(this.order)) {
                showError("Regla de Objeto: El objeto '" + key + "' no puede ser retirado.");
                return; 
            }
        }

        item.erase();
        items.remove(key);
        order.remove(key);
        drawTower();
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Removal Error", JOptionPane.WARNING_MESSAGE);
    }
    
    public void orderTower() {
        if (order.isEmpty()) return;

        ArrayList<Integer> ids = new ArrayList<>();
        for (String key : items.keySet()) {
            int id = extractId(key);
            if (!ids.contains(id)) {
                ids.add(id);
            }
        }

         Collections.sort(ids, Collections.reverseOrder());

        ArrayList<String> newOrder = new ArrayList<>();
        for (int id : ids) {
            String cupKey = findKey("Cup", id);
            if (cupKey != null) newOrder.add(cupKey);
            
            String lidKey = findKey("Lid", id);
            if (lidKey != null) newOrder.add(lidKey);
        }

        updateOrderIfValid(newOrder);
    }

    public void reverseTower() {
        if (order.isEmpty()) return;
        
        ArrayList<String> reversedOrder = new ArrayList<>(order);
        Collections.reverse(reversedOrder);
        
        updateOrderIfValid(reversedOrder);
    }

    public void swap(String[] o1, String[] o2) {
        if (o1 == null || o2 == null || o1.length < 2 || o2.length < 2) {
            showError("Formato inválido. Usa: swap(new String[]{\"cup\",\"3\"}, new String[]{\"lid\",\"3\"})");
            return;
        }
     
        String key1 = buildKeyFromArray(o1);
        String key2 = buildKeyFromArray(o2);
     
        if (key1 == null || key2 == null) {
            showError("Tipo inválido. Los tipos válidos son: 'cup' y 'lid'.");
            return;
        }
     
        if (!items.containsKey(key1)) {
            showError("No se encontró '" + key1 + "' en la torre.");
            return;
        }
        if (!items.containsKey(key2)) {
            showError("No se encontró '" + key2 + "' en la torre.");
            return;
        }
     
        ArrayList<String> testOrder = new ArrayList<>(order);
        int index1 = testOrder.indexOf(key1);
        int index2 = testOrder.indexOf(key2);
     
        if (index1 == -1 || index2 == -1) {
            showError("Error al localizar los items en el order.");
            return;
        }
     
        testOrder.set(index1, key2);
        testOrder.set(index2, key1);
     
        updateOrderIfValid(testOrder);
    }
     
    private String buildKeyFromArray(String[] arr) {
        String tipo = arr[0].toLowerCase();
        int id;
        try {
            id = Integer.parseInt(arr[1]);
        } catch (Exception e) {
            return null;
        }
     
        if (tipo.equals("lid")) {
            return findKey("Lid", id);
        } else if (tipo.equals("cup")) {
            return findKey("Cup", id);
        }
     
        return null;
    }
    
    private void updateOrderIfValid(ArrayList<String> newOrder) {
     if (grid.rebuild(newOrder, this.items)) {
            this.order = newOrder;
            drawTower();
            makeVisible();
        }
    }
    
    public void cover() {
        if (order.isEmpty()) return;

        ArrayList<Integer> ids = new ArrayList<>();
        for (String key : items.keySet()) {
            int id = extractId(key);
            if (!ids.contains(id)) ids.add(id);
        }

       ArrayList<String> newOrder = new ArrayList<>();
        for (int id : ids) {
            String cupKey = findKey("Cup", id);
            if (cupKey != null) newOrder.add(cupKey);
            String lidKey = findKey("Lid", id);
            if (lidKey != null) newOrder.add(lidKey);
        }
        updateOrderIfValid(newOrder);
    }

    private int calculatePotentialHeight(ArrayList<String> testOrder) {
        if (!this.grid.rebuild(testOrder, this.items)) {
            return Integer.MAX_VALUE; 
        }
        return this.grid.getOccupiedHeight();
    }
    
    public void swapToReduce() {
        int n = order.size();
        boolean huboIntercambio;
        int pasosContados = 0;

        do {
            huboIntercambio = false;
            for (int i = 0; i < n - 1; i++) {
                String keyActual = order.get(i);
                String keySiguiente = order.get(i + 1);

                int idActual = extractId(keyActual);
                int idSiguiente = extractId(keySiguiente);

                if (idActual < idSiguiente) {
                    System.out.println("\n[PASO DE OPTIMIZACIÓN ENCONTRADO]");
                    System.out.println("Índice " + i + " (" + keyActual + ") es menor que Índice " + (i+1) + " (" + keySiguiente + ")");
                    
                    int respuesta = javax.swing.JOptionPane.showConfirmDialog(null,
                        "¿Intercambiar '" + keyActual + "' con '" + keySiguiente + "'?\n" +
                        "Esto moverá el objeto más grande hacia la base para reducir altura.",
                        "Bubble Sort Interactivo",
                        javax.swing.JOptionPane.YES_NO_OPTION);

                    if (respuesta == javax.swing.JOptionPane.YES_OPTION) {
                        order.set(i, keySiguiente);
                        order.set(i + 1, keyActual);
                        
                        huboIntercambio = true;
                        pasosContados++;
                        
                        System.out.println("-> Intercambio confirmado y aplicado.");
                        drawTower();
                        makeVisible(); 
                    } else {
                        System.out.println("-> Intercambio rechazado por el usuario. Continuando...");
                    }
                }
            }
            n--; 
        } while (huboIntercambio);

        System.out.println("\n[PROCESO FINALIZADO]");
        System.out.println("Total de intercambios realizados: " + pasosContados);
        
        if (pasosContados == 0) {
            System.out.println("La torre ya se encuentra en su orden óptimo de tamaños.");
        }
    }

    public ArrayList<Integer> liddedCups() {
        ArrayList<Integer> lidded = new ArrayList<>();
        for (int i = 0; i < order.size() - 1; i++) {
            String current = order.get(i);
            String next = order.get(i + 1);

            int id = extractId(current);
            StackingItem itemActual = items.get(current);
            StackingItem itemSiguiente = items.get(next);
            
            // Verificación segura con instanceof
            if (itemActual instanceof Cup && itemSiguiente instanceof Lid) {
                // Comprobamos si la tapa le pertenece a esta taza
                if (extractId(next) == id) {
                    lidded.add(id);
                }
            }
        }
        return lidded;
    }
    
    public String[][] stackingItems() {
        String[][] result = new String[order.size()][2];
        for (int i = 0; i < order.size(); i++) {
            String key = order.get(i);         
            StackingItem item = items.get(key);
            
            int id = extractId(key);
     
            if (item instanceof Lid) {
                result[i][0] = "lid";
            } else {
                result[i][0] = "cup";
            }
            result[i][1] = String.valueOf(id);
        }
        return result;
    }

    private String findKeyForItem(StackingItem item) {
        for (Map.Entry<String, StackingItem> entry : items.entrySet()) {
            if (entry.getValue().equals(item)) {
                return entry.getKey();
            }
        }
        return "";
    }
    
    private int extractId(String key) {
        try {
            String[] parts = key.split("-");
            return Integer.parseInt(parts[parts.length - 1]);
        } catch (Exception e) {
            return -1;
        }
    }
    
    /**
     * CORRECCIÓN: Búsqueda segura usando 'instanceof'.
     * Previene fallos si cambias el nombre de las llaves en el futuro.
     */
    private String findKey(String category, int id) {
        for (Map.Entry<String, StackingItem> entry : items.entrySet()) {
            String k = entry.getKey();
            if (k.endsWith("-" + id)) {
                StackingItem item = entry.getValue();
                if (category.equalsIgnoreCase("Cup") && item instanceof Cup) return k;
                if (category.equalsIgnoreCase("Lid") && item instanceof Lid) return k;
            }
        }
        return null;
    }
    
    private void processSpecialEffects(Cup source, ArrayList<Integer> affectedIds) {
        if (affectedIds == null || affectedIds.isEmpty()) return;
    
        for (int id : affectedIds) {
            String lidKey = findKey("Lid", id);
            if (lidKey != null && items.containsKey(lidKey)) {
                items.get(lidKey).erase();
                items.remove(lidKey);
                order.remove(lidKey);
            }
        }
    }
    
    public int getHeight() {
        return this.grid.getOccupiedHeight() * 20; 
    }
    
    private boolean isValidType(String type) {
        return type.equalsIgnoreCase("normalCup") || 
               type.equalsIgnoreCase("opener") || 
               type.equalsIgnoreCase("hierarchical");
    }

    private boolean isValidLidType(String type) {
        return type.equalsIgnoreCase("normal") || 
               type.equalsIgnoreCase("crazy") || 
               type.equalsIgnoreCase("fearful");
    }
}