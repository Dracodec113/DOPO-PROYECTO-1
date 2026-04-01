<<<<<<< Updated upstream:código/stackingItems/TowerContest.java
/**
 * Resuelve y simula el Problem J - Stacking Cups.
 *
 * @author Jeronimo Moreno
 * @version 1.0
 */
public class TowerContest {

    public static String solve(int n, int h) {
=======
package domain;

public class TowerContest {

    /**
     * @param n Número de tazas (taza i tiene altura 2i-1).
     * @param h Altura objetivo de la torre en unidades.
     * @return Cadena con el orden de alturas, o "impossible".
     */
    public static String solve(int n, int h) {
        if (h <= 0 || n <= 0) return "impossible";
>>>>>>> Stashed changes:código/stackingItems-Refact-/domain/TowerContest.java
        int[] heights = new int[n];
        for (int i = 0; i < n; i++) {
            heights[i] = 2 * (i + 1) - 1;
        }
<<<<<<< Updated upstream:código/stackingItems/TowerContest.java
        String result = permute(heights, 0, h);
        if (result == null) return "impossible";
        return result;
    }

    public static void simulate(int n, int h) {
        String result = solve(n, h);
        if ("impossible".equals(result)) {
            javax.swing.JOptionPane.showMessageDialog(
                null, "Imposible: no existe orden para altura " + h);
            return;
        }
        Tower tower = new Tower(25, 25);
        for (String s : result.split(" ")) {
            int height = Integer.parseInt(s);
            int id = (height + 1) / 2;
            tower.pushCup(id);
        }
    }

    private static String permute(int[] arr, int start, int h) {
        if (start == arr.length) {
            int altura = calculateHeight(arr);
            System.out.println(arrayToString(arr) + " -> " + altura);
            if (altura == h) return arrayToString(arr);
=======

        String result = permute(heights, 0, h);
        return (result == null) ? "impossible" : result;
    }

     public static void simulate(int n, int h) {
        String result = solve(n, h);

        if ("impossible".equals(result)) {
            javax.swing.JOptionPane.showMessageDialog(
                null,
                "No existe ningún orden de " + n + " tazas que dé altura " + h + ".\n" +
                "La altura mínima posible es 1 y la máxima es " + maxHeight(n) + ".",
                "Imposible",
                javax.swing.JOptionPane.WARNING_MESSAGE
            );
            return;
        }


        int canvasSize = Math.max(600, n * 60);
        Tower tower = new Tower(canvasSize, canvasSize);

        String[] parts = result.split(" ");
        for (String part : parts) {
            int height = Integer.parseInt(part.trim());
            int id = (height + 1) / 2;
            tower.pushCup(id);
  
            try { Thread.sleep(300); } catch (Exception e) {}
        }

        javax.swing.JOptionPane.showMessageDialog(
            null,
            "¡Solución encontrada!\n" +
            "Orden de apilamiento (abajo → arriba): " + result + "\n" +
            "Altura lograda: " + h + " unidades.",
            "TowerContest — Solución",
            javax.swing.JOptionPane.INFORMATION_MESSAGE
        );
    }

    // ── Lógica interna ───────────────────────────────────────────────────────


    private static String permute(int[] arr, int start, int h) {
        if (start == arr.length) {
            if (calculateHeight(arr) == h) {
                return arrayToString(arr);
            }
>>>>>>> Stashed changes:código/stackingItems-Refact-/domain/TowerContest.java
            return null;
        }
        for (int i = start; i < arr.length; i++) {
            swap(arr, start, i);
            String result = permute(arr, start + 1, h);
            if (result != null) return result;
            swap(arr, start, i);
        }
        return null;
    }

<<<<<<< Updated upstream:código/stackingItems/TowerContest.java
    private static int calculateHeight(int[] order) {
        int towerHeight = 0;
        int floorInside = 0;
        int container = 0;

        System.out.print("  calculando: ");
        for (int i = 0; i < order.length; i++) {
            int cup = order[i];
            System.out.print("cup=" + cup + " tower=" + towerHeight + 
                           " container=" + container + " floor=" + floorInside + " | ");
            
            if (towerHeight == 0) {
                towerHeight = cup;
                container = cup;
            } else if (cup < container) {
                if (cup + floorInside > container) {
                    towerHeight = towerHeight - container + cup + floorInside;
                    container = cup + floorInside + 1;
                    floorInside = 0;
                } else {
                    floorInside = Math.max(floorInside, cup + 1);
                }
            } else {
                towerHeight = towerHeight + cup;
                container = cup;
                floorInside = 0;
            }
        }
        System.out.println("=> " + towerHeight);
        return towerHeight;
    }

=======
    /**
     * Calcula la altura total de una torre dado un orden de tazas.

     */
    private static int calculateHeight(int[] order) {
        if (order.length == 0) return 0;


        int towerHeight = order[0];
        int containerHeight = order[0]; 
        for (int i = 1; i < order.length; i++) {
            int cup = order[i];

            if (cup < containerHeight) {

                int internalSpace = containerHeight - 1;
                int protruding = Math.max(0, cup - internalSpace);
                towerHeight += protruding;

            } else {

                towerHeight += cup;
                containerHeight = cup; 
            }
        }

        return towerHeight;
    }

    /**
     * Calcula la altura máxima posible con n tazas (todas apiladas una encima de otra).
     * @param n Número de tazas.
     * @return Suma de todas las alturas: 1 + 3 + 5 + ... + (2n-1) = n²
     */
    private static int maxHeight(int n) {
        return n * n; // Suma de los primeros n números impares = n²
    }

    /**
     * Intercambia dos elementos en un arreglo.
     */
>>>>>>> Stashed changes:código/stackingItems-Refact-/domain/TowerContest.java
    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

<<<<<<< Updated upstream:código/stackingItems/TowerContest.java
    private static String arrayToString(int[] arr) {
        String result = "";
        for (int i = 0; i < arr.length; i++) {
            if (i > 0) result += " ";
            result += arr[i];
        }
        return result;
    }
}
=======
    /**
     * Convierte un arreglo de enteros a String separado por espacios.
     * Ejemplo: [1, 3, 5] → "1 3 5"
     */
    private static String arrayToString(int[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            if (i > 0) sb.append(" ");
            sb.append(arr[i]);
        }
        return sb.toString();
    }
}
>>>>>>> Stashed changes:código/stackingItems-Refact-/domain/TowerContest.java
