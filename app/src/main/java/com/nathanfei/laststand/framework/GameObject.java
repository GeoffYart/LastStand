package com.nathanfei.laststand.framework;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public abstract class GameObject {

    protected int x, y;
    protected int width, height;
    protected Paint paint;

    public GameObject (int x, int y) {
        this.x = x;
        this.y = y;
        paint = new Paint();
    }

    public abstract void tick();
    public abstract void render(Canvas canvas);

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Rect getRect() {
        return new Rect(x - width / 2, y - height / 2, x + width / 2, y + height / 2);
    }

     public void remove() {
         GameView.getObjHandler().removeObject(this);
     }
}
