/**

 * Resuelve y simula el Problem J - Stacking Cups.

 *

 * @author Jeronimo Moreno

 * @version 1.0

 */

public class TowerContest {



    public static String solve(int n, int h) {

        int[] cupsHeights= new int[n];

        for (int i = 0; i < n; i++) {

            cupsHeights[i]=2*(i+1)-1;

        }

        String result = permute(cupsHeights, h,0);

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

        Tower tower=new Tower(25, 25);

        for (String s:result.split(" ")) {

            int height = Integer.parseInt(s);

            int id=(height+1)/2;

            tower.pushCup(id);

        }

    }





    private static String permute(int[] cupsHeights, int h, int index) {

        if (index == cupsHeights.length) {

            if (calculateHeight(cupsHeights) == h) {

                return arrayToString(cupsHeights);

            }

            return null;

        }

    

        for (int i = index; i < cupsHeights.length; i++) {

            swap(cupsHeights, index, i);

            String res = permute(cupsHeights, h, index + 1);

            if (res != null) return res; 

            swap(cupsHeights, index, i); 

        }

        return null;

    }



    private static int calculateHeight(int[] order) {

        if (order.length == 0) return 0;

    

        int totalHeight = order[0];

        int currentContainer = order[0];

        int maxInsideHeight = 0;

    

        for (int i = 1; i < order.length; i++) {

            int nextCup = order[i];

            if (nextCup < currentContainer) {

                if (nextCup > maxInsideHeight) {

                    maxInsideHeight = nextCup;

                }

                if (1 + maxInsideHeight > currentContainer) {

                    int excess = (1 + maxInsideHeight) - currentContainer;

                    totalHeight += excess;

                    currentContainer += excess;

                }

            } else {

                totalHeight += nextCup;

                currentContainer = nextCup;

                maxInsideHeight = 0; 

            }

        }

        return totalHeight;

    }

    

    private static void swap(int[] arr, int i, int j) {

        int tmp = arr[i];

        arr[i] = arr[j];

        arr[j] = tmp;

    }



    private static String arrayToString(int[] arr) {

        String result="";

        for (int i = 0; i < arr.length; i++) {

            if (i>0) result+=" ";

            result += arr[i];

        }

        return result;

    }

}