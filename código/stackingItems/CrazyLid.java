import java.util.ArrayList;

/**
 * CrazyLid es una tapa especial que en lugar de tapar a su taza compañera,
 * se ubica en la BASE de la torre (fondo del order, índice 0).
 *
 * No tiene restricciones de entrada ni de salida.
 * Puede removerse en cualquier momento como una tapa normal.
 *
 * Tower consulta goesToBase() para saber que esta tapa debe ir al fondo,
 * y usa order.add(0, key) en lugar de order.add(key).
 *
 * Regla del enunciado:
 * "crazy: en lugar de tapar a su taza, se ubica de base."
 *
 * @author Jeronimo Moreno and Juan David Rangel
 * @version Ciclo 4
 */
public class CrazyLid extends Lid {

    /**
     * Constructor de CrazyLid.
     * @param id       Identificador de la tapa.
     * @param color    Color de la tapa.
     * @param x        Posición X inicial en el canvas.
     * @param y        Posición Y inicial en el canvas.
     * @param size     Tamaño que determina el ancho visual.
     * @param colorNum Índice numérico del color.
     */
    public CrazyLid(int id, String color, int x, int y, int size, int colorNum) {
        super(id, color, x, y, size, colorNum);
    }

    /**
     * Indica a Tower que esta tapa debe ir a la BASE de la torre,
     * no encima de su taza compañera.
     *
     * Tower usa este método en pushLid() para decidir si hace
     * order.add(0, key) en lugar de order.add(key).
     *
     * @return true — CrazyLid siempre va al fondo.
     */
    public boolean goesToBase() {
        return true;
    }

    /**
     * CrazyLid no tiene restricciones de salida.
     * Puede removerse en cualquier momento.
     *
     * @param currentOrder Lista actual de keys en la torre.
     * @return true siempre.
     */
    @Override
    public boolean canRemove(ArrayList<String> currentOrder) {
        return true;
    }
}