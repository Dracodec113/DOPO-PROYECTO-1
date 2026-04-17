package domain;

import java.util.ArrayList;

/**
 * FearfulLid es una tapa especial con dos comportamientos de miedo:
 *
 * 1. REGLA DE ENTRADA: Si su taza compañera NO está en la torre, no puede entrar.
 *    Esta regla la verifica Tower antes de instanciar FearfulLid,
 *    consultando el método requiresCompanion().
 *
 * 2. REGLA DE SALIDA: Si está tapando directamente a su taza compañera
 *    (la taza está justo debajo de ella en el order), no puede salir.
 *    Si no está tapando a su taza, sí puede salir libremente.
 *
 * Regla del enunciado:
 * "fearful: si taza compañera no está en la torre, no entra
 *  y si está tapando a su taza, no sale."
 *
 * @author Jeronimo Moreno and Juan David Rangel
 * @version Ciclo 4
 */
public class FearfulLid extends Lid {

    /**
     * Constructor de FearfulLid.
     * @param id       Identificador de la tapa (coincide con el id de su taza).
     * @param color    Color de la tapa.
     * @param x        Posición X inicial en el canvas.
     * @param y        Posición Y inicial en el canvas.
     * @param size     Tamaño de la taza compañera (determina el ancho visual).
     * @param colorNum Índice numérico del color.
     */
    public FearfulLid(int id, String color, int x, int y, int size, int colorNum) {
        super(id, color, x, y, size, colorNum);
    }

    /**
     * Indica a Tower que esta tapa requiere que su taza compañera esté en la torre
     * antes de poder entrar.
     * Tower consulta este método en pushLid() antes de instanciar la tapa.
     *
     * @return true — FearfulLid siempre requiere su compañera.
     */
    public boolean requiresCompanion() {
        return true;
    }

    /**
     * Regla de salida de FearfulLid:
     * - Si está tapando directamente a su taza (la taza está justo antes en el order),
     *   NO puede salir. El miedo la retiene.
     * - Si no está tapando a su taza (la taza no está o hay algo entre ellas),
     *   SÍ puede salir.
     *
     * Ejemplo de order donde NO puede salir:
     *   [..., "normalCup-3", "lid-3"]  ← taza justo antes = cubriendo = no sale
     *
     * Ejemplo de order donde SÍ puede salir:
     *   [..., "normalCup-3", "normalCup-1", "lid-3"]  ← hay algo entre ellas = sale
     *   [..., "lid-3"]  ← su taza no está = sale (ya no tiene a quien tapar)
     *
     * @param currentOrder Lista actual de keys en la torre.
     * @return false si está tapando a su taza compañera, true en cualquier otro caso.
     */
    @Override
    public boolean canRemove(ArrayList<String> order) {
        // Usamos el atributo 'key' que guardamos en onPush
        int myIndex = order.indexOf(this.key);
        
        if (myIndex > 0) {
            String itemBelow = order.get(myIndex - 1);
            // Si el de abajo tiene mi ID y NO es una tapa, es mi taza
            if (itemBelow.endsWith("-" + this.id) && !itemBelow.toLowerCase().contains("lid")) {
                return false; // Bloqueado: mi taza está justo debajo
            }
        }
        return true;
    }
}
