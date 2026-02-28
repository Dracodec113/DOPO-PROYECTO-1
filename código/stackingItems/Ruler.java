import java.awt.Graphics2D;
import java.awt.Color;

public class Ruler {
    private int xPosition;
    private int yPosition;
    private int height;
    private int unitSize; // píxeles por unidad

    public Ruler() {
        this.xPosition = 20;
        this.yPosition = 280;
        this.height = 14;
        this.unitSize = 20;
        draw();
    }

    public void draw() {
        Canvas canvas = Canvas.getCanvas();
        Graphics2D g = canvas.getGraphics();

        g.setColor(Color.BLACK);

        // Línea vertical principal
        g.drawLine(xPosition, yPosition, xPosition, yPosition - height * unitSize);

        // Marcas y números
        for (int i = 0; i <= height; i++) {
            int y = yPosition - i * unitSize;
            g.drawLine(xPosition - 5, y, xPosition + 5, y); // marca horizontal
            g.drawString(String.valueOf(i), xPosition - 20, y + 5); // número
        }
    }
}