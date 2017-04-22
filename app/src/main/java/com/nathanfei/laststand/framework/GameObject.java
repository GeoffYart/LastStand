package com.nathanfei.laststand.framework;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public abstract class GameObject {

    protected double x, y;
    protected double width, height;
    protected Paint paint;

    public GameObject (double x, double y) {
        this.x = x;
        this.y = y;
        paint = new Paint();
    }

    public abstract void tick();
    public abstract void render(Canvas canvas);

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setX(int x) {
        this.x = x / MainActivity.screenWidth();
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setY(int y) {
        this.y = y / MainActivity.screenHeight();
    }

    public Rect getRect() {
        return new Rect(
                (int)((x - width / 2) * MainActivity.screenWidth()),
                (int)((y - height / 2) * MainActivity.screenHeight()),
                (int)((x + width / 2) * MainActivity.screenWidth()),
                (int)((y + height / 2) * MainActivity.screenHeight()));
    }

    protected void remove() {
         GameView.getObjHandler().removeObject(this);
    }
}
