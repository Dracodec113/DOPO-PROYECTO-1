package domain;
import java.util.ArrayDeque;
import java.util.Deque;

public class TowerContest {

    public static String solve(int n, long h) {
        long minH = 2L * n - 1;
        long maxH = (long) n * n;

        if (h < minH || h > maxH || h == maxH - 2) return "impossible"; 
        
        if (h == minH + 2) {
            if (n < 4) return "impossible"; 
            return buildSpecialCase(n);
        }

        Deque<Integer> stack = new ArrayDeque<>();
        long diff = h - minH;
        stack.add(2 * n - 1); 

        // Algoritmo Greedy Corregido
        for (int i = n - 1; i >= 1; i--) {
            int cupValue = 2 * i - 1;
            int gainIfStacked = cupValue;

            if (diff >= gainIfStacked && diff - gainIfStacked != 2) {
                stack.addFirst(cupValue);
                diff -= gainIfStacked;
            } else {
                stack.addLast(cupValue); 
            }
        }
        
        return diff == 0 ? dequeToString(stack) : "impossible";
    }

    /**
     * Construye manualmente el patrón para el caso especial 2n+1.
     * Ejemplo para n=4: "7 3 5 1"
     */
    private static String buildSpecialCase(int n) {
        StringBuilder sb = new StringBuilder();
        sb.append(2 * n - 1).append(" 3 ").append(2 * n - 3);
        for (int i = n - 2; i >= 1; i--) {
            int cup = 2 * i - 1;
            if (cup != 3) { 
                sb.append(" ").append(cup);
            }
        }
        return sb.toString();
    }

    /**
     * Calcula la altura física real rastreando el borde y el fondo de cada taza.
     */
    public static long calculateHeight(int[] order) {
        if (order.length == 0) return 0;
        
        long maxH = 0;
        long currentRim = 0;   // Borde superior de la taza actual
        long currentFloor = 0; // Fondo interno de la taza actual
        
        for (int i = 0; i < order.length; i++) {
            int cup = order[i];
            
            if (i == 0) {
                currentRim = cup;
                currentFloor = 1;
            } else {
                int prevCup = order[i-1];
                if (cup < prevCup) {
                    // VA ADENTRO: Se apoya en el fondo interno
                    currentRim = currentFloor + cup;
                    currentFloor = currentFloor + 1; // Sube 1cm por el grosor de la base
                } else {
                    // VA ENCIMA: Su base se apoya EXACTAMENTE sobre el borde anterior
                    currentFloor = currentRim + 1; 
                    currentRim = currentRim + cup;
                }
            }
            maxH = Math.max(maxH, currentRim); 
        }
        return maxH;
    }

    public static void simulate(int n, int h) {
        String result = solve(n, (long)h);
        System.out.println("Orden encontrado para h=" + h + ": " + result);

        if ("impossible".equals(result)) {
            javax.swing.JOptionPane.showMessageDialog(null, "Imposible para altura " + h);
            return;
        }

        Tower tower = new Tower(600, 600);
        String[] parts = result.split(" ");
        for (String s : parts) {
            int height = Integer.parseInt(s);
            int id = (height + 1) / 2;
            tower.pushCup(id);
        }
        tower.makeVisible();
    }

    private static String dequeToString(Deque<Integer> stack) {
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append(stack.pollFirst()).append(stack.isEmpty() ? "" : " ");
        }
        return sb.toString();
    }
}