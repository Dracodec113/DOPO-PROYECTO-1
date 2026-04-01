package domain;

import java.util.*;
import javax.swing.JOptionPane;
/**
 * La clase torre es la clase principal del problema StackingCups
 * En esta se maneja la lógica general y las operaciones posibles del juego.
 * id: Funciona de tamaño también.
 * 
 * @author Jeronimo Moreno and Juan David Rangel
 * @version (0.1) Simulation test
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
        
        // 1. Inicializar y dibujar regla
        this.ruler = new presentation.Ruler(this.canvasHeight);
        this.ruler.draw(); 
    
        this.lastColorIndex = -1;
    
        // 2. Dibujar cuadrícula de 20x20
        this.grid.drawDebugGrid(); 
    }
    
    /**
     * Constructor de sobrecarga mejorado.
     * Si pides 5 cups, crea un espacio de 600x600 para que todo quepa 
     * holgadamente y se vea la regla de 30cm.
     */
    public Tower(int cups) {
        // En lugar de cups * 20, usamos un tamaño estándar de 600px (30 celdas)
        // para que la regla y la cuadrícula se vean bien por defecto.
        this(600, 600); 
        
        // Agregamos las tazas solicitadas
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
        
    /**
     * Genera una llave única para el HashMap.
     * Ejemplo: "normalCup-3" o "lid-5"
     */
    private String makeKey(String type, int id) {
        return type + "-" + id;
    }
    
    /**
     * Verifica si una taza de cualquier tipo existe en la torre.
     */
    private boolean checkCup(int id) {
        for (String key : items.keySet()) {
            if (key.endsWith("-" + id) && !key.startsWith("lid")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica si una tapa específica existe.
     */
    private boolean checkLid(int id) {
        return items.containsKey(makeKey("lid", id));
    }
    
    /**
     * Método auxiliar para obtener un color aleatorio sin repetir el anterior.
     */
    private int getRandomColorIndex() {
        Random rand = new Random();
        int newColor;
        do {
            newColor = rand.nextInt(COLORS.length);
        } while (newColor == lastColorIndex);
        return newColor;
    }
    
    /**
     * Adds a standard cup to the tower if it doesn't already exist.
     * Use of String keys: "normalCup-id"
     * * @param id The identifier and size of the cup.
     */
    public void pushCup(int id) {
        pushCup("normalCup", id);
    }
    
    public void pushCup(String type, int id) {
        if (!isValidType(type)) {
            showError("El tipo '" + type + "' no es una taza válida.");
            return;
        }
    
        if (!checkCup(id)) {
            // 1. Determinar color: Si ya existe la tapa de este ID, usamos su color
            int colorIndex = checkLid(id) ? items.get(makeKey("lid", id)).colorNum : getRandomColorIndex();
            String key = makeKey(type, id);
            Cup cup;
    
            // 2. Instanciar según el tipo (TODAS con los mismos parámetros)
            // Ya NO pasamos (2*id)-1 porque el constructor de Cup lo calcula internamente
            if (type.equalsIgnoreCase("opener")) {
                cup = new OpenerCup(id, COLORS[colorIndex], 0, 0, colorIndex);
            } else if (type.equalsIgnoreCase("hierarchical")) {
                cup = new HierarchicalCup(id, COLORS[colorIndex], 0, 0, colorIndex);
            } else {
                cup = new Cup(id, COLORS[colorIndex], 0, 0, colorIndex);
            }
    
            // 3. Ejecutar lógica especial (reubicar o marcar para eliminar)
            ArrayList<Integer> affected = cup.onPush(order, items);
            
            // 4. Registrar en el sistema
            items.put(key, cup);
            order.add(key); // El HierarchicalCup se moverá dentro de processSpecialEffects o mediante su propio onPush
            
            // 5. Limpiar objetos si el OpenerCup eliminó a alguien
            processSpecialEffects(cup, affected);
            
            lastColorIndex = colorIndex;
            
            // 6. Dibujar todo
            drawTower(); 
        }
    }
    
    /**
     * Updates the visual representation of the tower, making everything visible.
     * Use of String keys: "normalCup-id", "opener-id", "hierarchical-id", "lid-id"
     * * @author Jeronimo Moreno and Juan David Rangel
     */
    private void drawTower() {
        // 1. EL PASO MÁS IMPORTANTE: Dibujar un fondo blanco limpio
        // Esto borra físicamente la taza negra anterior de la pantalla.
        grid.clearBackground(); 
    
        // 2. El Grid intenta acomodar los objetos según el 'order'
        if (!grid.rebuild(this.order, this.items)) {
            return;
        }
    
        // 3. Dibujamos la cuadrícula de fondo (las líneas grises)
        grid.drawDebugGrid();
    
        // 4. Hacemos visibles y dibujamos los items lógicamente
        for (StackingItem item : items.values()) {
            item.makeVisible(); 
        }
    
        // 5. El Grid los posiciona visualmente
        grid.render();
    
        // 6. ¡LA CLAVE! Redibujamos la regla al final para que quede al frente
        if (this.ruler != null) {
            this.ruler.draw(); 
        }
    }

    /**
     * Adds a standard lid to the tower if it doesn't already exist.
     * Use of String keys: "lid-id"
     * @param id The identifier corresponding to its cup's size.
     */
    public void pushLid(int id) {
        pushLid("normal", id);
    }
 
    public void pushLid(String type, int id) {
        if (!isValidLidType(type)) {
            showError("Tipo de tapa inválida.");
            return;
        }
     
        // FIX: size SIEMPRE es id, tenga o no taza compañera.
        // El id ES el tamaño — la fórmula (2*size)-1 en Lid lo convierte a celdas.
        // Antes: int size = 1  cuando no había taza → tapa de 1 celda (BUG)
        // Ahora: int size = id siempre              → tapa del ancho correcto
        int size = id;
     
        // El color sí depende de si hay taza: si existe, heredamos su color
        int colorIndex;
        if (checkCup(id)) {
            StackingItem cup = items.get(findKey("Cup", id));
            colorIndex = cup.colorNum;
        } else {
            colorIndex = getRandomColorIndex();
        }
     
        if (!checkLid(id)) {
     
            // FearfulLid no puede entrar si su taza no está
            if (type.equalsIgnoreCase("fearful") && !checkCup(id)) {
                showError("FearfulLid " + id + " no puede entrar: su taza compañera no está en la torre.");
                return;
            }
     
            String key = makeKey("lid", id);
            Lid lid;
     
            if (type.equalsIgnoreCase("crazy")) {
                lid = new CrazyLid(id, COLORS[colorIndex], 0, 0, size, colorIndex);
                order.add(0, key);  // CrazyLid va al fondo
            } else if (type.equalsIgnoreCase("fearful")) {
                lid = new FearfulLid(id, COLORS[colorIndex], 0, 0, size, colorIndex);
                order.add(key);
            } else {
                lid = new Lid(id, COLORS[colorIndex], 0, 0, size, colorIndex);
                order.add(key);
            }
     
            items.put(key, lid);
            lastColorIndex = colorIndex;
            drawTower();
        }
    }
    
    /**
     * Removes the item at the very top of the tower, only if it's a cup.
     */
        public void popCup() {
        if (order.isEmpty()) {
            showError("La torre está vacía.");
            return;
        }

        String topKey = order.get(order.size() - 1);
        if (topKey.startsWith("lid")) {
            showError("El tope de la torre es una tapa, no una taza.\n" +
                      "Usa popLid() para sacar tapas.");
            return;
        }
     
        StackingItem item = items.get(topKey);
        if (item != null) item.erase();
        items.remove(topKey);
        order.remove(topKey);
     
        drawTower();
    }

    /**
     * Removes the item at the very top of the tower, only if it's a lid.
     */
    public void popLid() {
        if (order.isEmpty()) {
            showError("La torre está vacía.");
            return;
        }
 
        String topKey = order.get(order.size() - 1);
     
        if (!topKey.startsWith("lid")) {
            showError("El tope de la torre es una taza, no una tapa.\n" +
                      "Usa popCup() para sacar tazas.");
            return;
        }
     
        StackingItem item = items.get(topKey);
        if (item != null) item.erase();
        items.remove(topKey);
        order.remove(topKey);
     
        drawTower();
    }

    /**
     * Removes a specific cup by ID from any position (if rules allow).
     */
    public void removeCup(int id) {
        String key = findKey("Cup", id);
        if (key != null) {
            // Here we would check the interface: if(items.get(key).canBeRemove
            executeRemoval(key);
        } else {
            showError("Cup with ID " + id + " not found.");
        }
    }

    /**
     * Removes a specific lid by ID from any position.
     */
    public void removeLid(int id) {
        String key = makeKey("lid", id);
        if (items.containsKey(key)) {
            executeRemoval(key);
        } else {
            showError("Lid with ID " + id + " not found.");
        }
    }


    /**
     * Internal method to handle removal with rule checking via Interfaces.
     */
    private void executeRemoval(String key) {
        StackingItem item = items.get(key);
        
        if (item instanceof Removable) {
            if (!((Removable) item).canRemove(this.order)) {
                showError("Rule Violation: This item cannot be removed right now.");
                return; 
            }
        }


        items.remove(key);
        order.remove(key);
        drawTower();
        makeVisible();
    }

    /**
     * Helper to show consistent error messages.
     */
    private void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Removal Error", JOptionPane.WARNING_MESSAGE);
    }
    
    /**
     * Reorders the tower from largest to smallest (bottom to top).
     */
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
            
            String lidKey = makeKey("lid", id);
            if (items.containsKey(lidKey)) newOrder.add(lidKey);
        }

        updateOrderIfValid(newOrder);
    }

    /**
     * Reverses the current order list of the tower.
     */
    public void reverseTower() {
        if (order.isEmpty()) return;
        
        ArrayList<String> reversedOrder = new ArrayList<>(order);
        Collections.reverse(reversedOrder);
        
        updateOrderIfValid(reversedOrder);
    }

    /**
     * Swaps two elements in the tower using their keys.
     */
        public void swap(String[] o1, String[] o2) {
        // Validar formato de entrada
        if (o1 == null || o2 == null || o1.length < 2 || o2.length < 2) {
            showError("Formato inválido. Usa: swap(new String[]{\"cup\",\"3\"}, new String[]{\"lid\",\"3\"})");
            return;
        }
     
        // Construir las keys desde los arrays recibidos
        // o1[0] = tipo ("cup" o "lid"), o1[1] = id numérico
        String key1 = buildKeyFromArray(o1);
        String key2 = buildKeyFromArray(o2);
     
        if (key1 == null || key2 == null) {
            showError("Tipo inválido. Los tipos válidos son: 'cup' y 'lid'.");
            return;
        }
     
        // Verificar que ambos existen en la torre
        if (!items.containsKey(key1)) {
            showError("No se encontró '" + key1 + "' en la torre.");
            return;
        }
        if (!items.containsKey(key2)) {
            showError("No se encontró '" + key2 + "' en la torre.");
            return;
        }
     
        // Hacer una copia del order para probar el intercambio
        ArrayList<String> testOrder = new ArrayList<>(order);
        int index1 = testOrder.indexOf(key1);
        int index2 = testOrder.indexOf(key2);
     
        if (index1 == -1 || index2 == -1) {
            showError("Error al localizar los items en el order.");
            return;
        }
     
        // Realizar el intercambio en la copia
        testOrder.set(index1, key2);
        testOrder.set(index2, key1);
     
        // Validar con el grid antes de aplicar
        // updateOrderIfValid reconstruye el grid y solo aplica si es válido
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
            // Las tapas tienen key fija: "lid-id"
            return makeKey("lid", id);
        } else if (tipo.equals("cup")) {
            // Las tazas pueden ser de varios tipos: buscar en items por id
            return findKey("Cup", id);
        }
     
        return null;
    }
    
    /**
     * Validates the new order with the TowerGrid before applying changes.
     */
    private void updateOrderIfValid(ArrayList<String> newOrder) {
     if (grid.rebuild(newOrder, this.items)) {
            this.order = newOrder;
            drawTower();
            makeVisible();
        }
    }
    


    /**
     * Groups each cup with its corresponding lid immediately above it.
     */
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
            String lidKey = makeKey("lid", id);
            if (items.containsKey(lidKey)) newOrder.add(lidKey);
        }
        updateOrderIfValid(newOrder);
    }

// Dentro de Tower.java
    private int calculatePotentialHeight(ArrayList<String> testOrder) {
        // Usamos el grid para simular el rebuild, pero NO lo renderizamos (así no parpadea la pantalla)
        // Para que esto funcione sin fallar, necesitamos un grid temporal o un método de simulación en TowerGrid.
        // Simplificación: si no cabe, la altura es infinita.
        if (!this.grid.rebuild(testOrder, this.items)) {
            return Integer.MAX_VALUE; 
        }
        // Devolvemos la altura ocupada en celdas para el cálculo.
        return this.grid.getOccupiedHeight();
    }
    
    public void swapToReduce() {
        int n = order.size();
        boolean huboIntercambio;
        int pasosContados = 0;

        // Algoritmo Bubble Sort Clásico
        do {
            huboIntercambio = false;
            for (int i = 0; i < n - 1; i++) {
                // 1. Extraemos las llaves (Strings)
                String keyActual = order.get(i);
                String keySiguiente = order.get(i + 1);

                // 2. Extraemos los IDs numéricos para poder comparar tamaños
                int idActual = extractId(keyActual);
                int idSiguiente = extractId(keySiguiente);

                // 3. Lógica de Bubble Sort: Si el de abajo es más pequeño que el de arriba, sugerimos cambio
                // (Queremos los IDs más grandes en los índices menores de la lista)
                if (idActual < idSiguiente) {
                    
                    // Informar el hallazgo en consola
                    System.out.println("\n[PASO DE OPTIMIZACIÓN ENCONTRADO]");
                    System.out.println("Índice " + i + " (" + keyActual + ") es menor que Índice " + (i+1) + " (" + keySiguiente + ")");
                    
                    // 4. Preguntar al usuario antes de proceder
                    int respuesta = javax.swing.JOptionPane.showConfirmDialog(null,
                        "¿Intercambiar '" + keyActual + "' con '" + keySiguiente + "'?\n" +
                        "Esto moverá el objeto más grande hacia la base para reducir altura.",
                        "Bubble Sort Interactivo",
                        javax.swing.JOptionPane.YES_NO_OPTION);

                    if (respuesta == javax.swing.JOptionPane.YES_OPTION) {
                        // 5. Realizar el intercambio físico en la lista 'order'
                        order.set(i, keySiguiente);
                        order.set(i + 1, keyActual);
                        
                        huboIntercambio = true;
                        pasosContados++;
                        
                        System.out.println("-> Intercambio confirmado y aplicado.");
                        
                        // 6. Actualizar la visualización inmediatamente
                        // Esto invoca a grid.clearBackground() y grid.rebuild()
                        drawTower();
                        makeVisible(); 
                    } else {
                        System.out.println("-> Intercambio rechazado por el usuario. Continuando...");
                    }
                }
            }
            // Optimizamos el ciclo: el más pequeño ya llegó al final
            n--; 
        } while (huboIntercambio);

        System.out.println("\n[PROCESO FINALIZADO]");
        System.out.println("Total de intercambios realizados: " + pasosContados);
        
        if (pasosContados == 0) {
            System.out.println("La torre ya se encuentra en su orden óptimo de tamaños.");
        }
    }
    /**
     * Returns a list of IDs of cups that are currently covered by their lid.
     * @return ArrayList of Integers (cup IDs).
     */
    public ArrayList<Integer> liddedCups() {
        ArrayList<Integer> lidded = new ArrayList<>();
        for (int i = 0; i < order.size() - 1; i++) {
            String current = order.get(i);
            String next = order.get(i + 1);

            int id = extractId(current);
            if (!current.startsWith("lid") && next.equals(makeKey("lid", id))) {
                lidded.add(id);
            }
        }
        return lidded;
    }
    
    /**
     * Devuelve una representación en matriz de la torre.
     * @return Una matriz de String donde cada celda contiene la llave del item o "" si está vacía.
     */
    public String[][] stackingItems() {
        String[][] result = new String[order.size()][2];
        for (int i = 0; i < order.size(); i++) {
            String key = order.get(i);          // ej: "normalCup-3" o "lid-3"
            String[] parts = key.split("-");
            int id = Integer.parseInt(parts[parts.length - 1]);
     
            // Determinar si es cup o lid para que el test lo reconozca
            if (key.startsWith("lid")) {
                result[i][0] = "lid";
            } else {
                result[i][0] = "cup";
            }
            result[i][1] = String.valueOf(id);
        }
        return result;
    }

    /**
     * Método auxiliar para encontrar la llave de un objeto específico.
     */
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
    
    private String findKey(String category, int id) {
        for (String k : items.keySet()) {
            if (k.endsWith("-" + id)) {
                if (category.equalsIgnoreCase("Cup") && !k.startsWith("lid")) {
                    return k;
                }
                if (category.equalsIgnoreCase("Lid") && k.startsWith("lid")) {
                    return k;
                }
            }
        }
        return null;
    }
    
    private void processSpecialEffects(Cup source, ArrayList<Integer> affectedIds) {
        if (affectedIds == null || affectedIds.isEmpty()) return;
    
        for (int id : affectedIds) {
            // OpenerCup solo elimina TAPAS, no tazas
            String lidKey = makeKey("lid", id);
            if (items.containsKey(lidKey)) {
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
    


    
