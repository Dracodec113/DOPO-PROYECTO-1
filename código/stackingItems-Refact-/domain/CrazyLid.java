package domain;



import java.util.ArrayList;

import java.util.HashMap;



/**

 * CrazyLid: en lugar de tapar a su taza, se ubica de base de la misma.

 * * @author Jeronimo Moreno and Juan David Rangel

 * @version Ciclo 4

 */

public class CrazyLid extends Lid {



    public CrazyLid(int id, String color, int x, int y, int size, int colorNum) {

        super(id, color, x, y, size, colorNum);

    }



    /**

     * Implementación polimórfica: busca la taza del mismo ID 

     * y se inserta justo en su misma posición (desplazándola hacia arriba).

     */



    @Override

        public ArrayList<Integer> onPush(ArrayList<String> currentOrder, HashMap<String, StackingItem> items, String myKey) {

            this.key = myKey;

            int targetIndex = currentOrder.size();

    

            for (int i = 0; i < currentOrder.size(); i++) {

                String k = currentOrder.get(i);

                if (k.endsWith("-" + this.id) && !k.toLowerCase().contains("lid")) {

                    targetIndex = i;

                    break;

                }

            }

            currentOrder.add(targetIndex, myKey);

            return new ArrayList<>();

    }

}