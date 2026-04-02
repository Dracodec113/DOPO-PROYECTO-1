package domain;

/**
 * Resuelve y simula el Problem J - Stacking Cups.
 *
 * @author Jeronimo Moreno
 * @version 1.0
 */
public class TowerContest {

    public static String solve(int n, int h) {
        int[] heights = new int[n];
        for (int i = 0; i < n; i++) {
            heights[i] = 2 * (i + 1) - 1;
        }
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

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    private static String arrayToString(int[] arr) {
        String result = "";
        for (int i = 0; i < arr.length; i++) {
            if (i > 0) result += " ";
            result += arr[i];
        }
        return result;
    }
}
