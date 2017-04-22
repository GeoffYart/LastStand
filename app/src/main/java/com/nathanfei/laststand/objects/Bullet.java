package com.nathanfei.laststand.objects;

import android.graphics.Canvas;
import android.graphics.Color;

import com.nathanfei.laststand.framework.GameObject;
import com.nathanfei.laststand.framework.GameView;

public class Bullet extends GameObject {

    private int damage;

    public Bullet(double x, double y, int damage) {
        super(x, y);
        this.damage = damage;
        width = 0.02;
        height = 0.05;
    }

    @Override
    public void tick() {
        y -= 0.04;
        if (y < 0) {
            remove();
        }
        collision();
    }

    private void collision() {
        for (GameObject obj : GameView.getObjHandler().list()) {
            if (obj instanceof Enemy && getRect().intersect(obj.getRect())) {
                ((Enemy) obj).hit(damage);
                remove();
                break;
            }
        }
    }

    @Override
    public void render(Canvas canvas) {
        paint.setColor(Color.YELLOW);
        canvas.drawRect(getRect(), paint);
    }
}
