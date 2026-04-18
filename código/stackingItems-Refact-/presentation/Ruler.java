package presentation;

import java.awt.Color;
import java.awt.Graphics2D;

public class Ruler {
    private int xPosition;
    private int yPosition;
    private int height;
    private int unitSize; // píxeles por unidad

    public Ruler(int heigth) {
        this.xPosition = 30;
        this.yPosition = 600;
        this.height = heigth;
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
            g.drawString(String.valueOf(i + "cm"), xPosition - 20, y + 5); // número
        }
    }
}
