package domain;

import java.util.*;

public interface Stackable {
    /**
     * @param currentOrder La lista de llaves de la torre.
     * @param items El mapa de objetos.
     * @param myKey La llave única asignada a este objeto.
     * @return Lista de IDs afectados.
     */
    ArrayList<Integer> onPush(ArrayList<String> currentOrder, HashMap<String, StackingItem> items, String myKey);
}