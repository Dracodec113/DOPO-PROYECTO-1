package domain;

import java.awt.Shape;

public abstract class StackingItem {
    protected int id;
    protected String color;
    protected int x, y;
    protected int width, height;
    protected int colorNum;
    protected boolean isVisible;
    protected String key;
    public StackingItem(int id, String color, int x, int y, int colorNum) {
        this.id = id;
        this.color = color;
        this.x = x;
        this.y = y;
        this.colorNum = colorNum;
        this.isVisible = false;
    }
    
    public void setKey(String key) {
            this.key = key;
        }
    
    protected abstract Shape getShape();

    public void draw() {
        if (isVisible) {
            presentation.Canvas canvas = presentation.Canvas.getCanvas();
            canvas.draw(this, color, getShape());
        }
    }

    public void erase() {
        if (isVisible) {
            presentation.Canvas canvas = presentation.Canvas.getCanvas();
            canvas.erase(this);
        }
    }

    public void makeVisible() {
        isVisible = true;
        draw();
    }

    public void makeInvisible() {
        erase();
        isVisible = false;
    }
    
    public void redraw(int newX, int newY) {
        if (this.isVisible) {
            erase();
        }
        this.x = newX;
        this.y = newY;

        if (this.isVisible) {
            draw();
        }
    }
    
    public int getX() { return x; }
    public void setX(int x) { this.x = x; }

    public int getY() { return y; }
    public void setY(int y) { this.y = y; }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
    
    public int getId() { return id; }
    public String getColor() { return color; }
}
