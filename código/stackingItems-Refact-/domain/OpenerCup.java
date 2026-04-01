package domain;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * OpenerCup es una taza especial que al entrar a la torre elimina
 * TODAS las tapas que le impiden el paso, es decir, todas las tapas
 * cuyo ID sea menor al suyo (son más pequeñas y estarían encima).
 *
 * Regla del enunciado:
 * "opener: elimina todas las tapas que le impiden el paso"
 *
 * @author Jeronimo Moreno and Juan David Rangel
 * @version Ciclo 4
 */
public class OpenerCup extends Cup {

    public OpenerCup(int id, String color, int x, int y, int colorNum) {
        super(id, color, x, y, colorNum);
    }

    /**
     * Al entrar a la torre, elimina TODAS las tapas que le impiden el paso.
     * Una tapa "impide el paso" si su tamaño (id) es menor al de esta taza,
     * porque estaría flotando sobre ella sin poder asentarse.
     *
     * Retorna una lista de IDs de tapas a eliminar para que Tower
     * las borre del HashMap, del order y del canvas.
     *
     * @param currentOrder Lista actual de keys en la torre.
     * @param items        Mapa de todos los items.
     * @return Lista de IDs de tapas que deben ser eliminadas.
     */
    @Override
    public ArrayList<Integer> onPush(ArrayList<String> currentOrder,
                                     HashMap<String, StackingItem> items) {
        ArrayList<Integer> toRemove = new ArrayList<>();

        for (String key : currentOrder) {
            if (!key.startsWith("lid")) continue;

            int lidId = extractId(key);
            if (lidId == -1) continue;

            if (lidId < this.id) {
                toRemove.add(lidId);
            }
        }

        return toRemove;
    }

    /**
     * Extrae el ID numérico de una key con formato "tipo-id".
     * @param key La key a parsear.
     * @return El ID numérico, o -1 si no se puede parsear.
     */
    private int extractId(String key) {
        try {
            String[] parts = key.split("-");
            return Integer.parseInt(parts[parts.length - 1]);
        } catch (Exception e) {
            return -1;
        }
    }
}
