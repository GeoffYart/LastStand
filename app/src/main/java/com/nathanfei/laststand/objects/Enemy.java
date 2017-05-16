package com.nathanfei.laststand.objects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;

import com.nathanfei.laststand.framework.GameObject;
import com.nathanfei.laststand.framework.GameView;

public class Enemy extends GameObject {

    private int health;

    public Enemy(double x, double y, int level) {
        super(x, y);
        health = setHealth(level);
        width = 0.16;
        height = 0.09;
    }

    private int setHealth(int level) {
        return level * 4 + 1;
    }

    @Override
    public void tick() {
        y += 0.005;
        if (y > 1 + height / 2) {
            for(GameObject obj : GameView.getObjHandler().list()) {
                if (obj instanceof Shooter) {
                    ((Shooter)obj).hit(health);
                }
            }
            remove();
        }
    }

    public void hit(int damage) {
        health -= damage;
        this.y -= 0.01;
        if (health <= 0) {
            remove();
        }
    }

    @Override
    public void render(Canvas canvas) {
        paint.setColor(Color.RED);
        canvas.drawRect(getRect(), paint);
        paint.setColor(Color.WHITE);
        paint.setTextSize(96f);
        canvas.drawText("" + health, getRect().left, getRect().bottom, paint);
    }
}
