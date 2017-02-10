package com.nathanfei.laststand.objects;

import android.graphics.Canvas;
import android.graphics.Color;

import com.nathanfei.laststand.framework.GameObject;

public class Bullet extends GameObject {
    public Bullet(int x, int y) {
        super(x, y);
        width = 30;
        height = 70;
    }

    @Override
    public void tick() {
        y -= 30;
        if (y < 0) {
            remove();
        }
    }

    @Override
    public void render(Canvas canvas) {
        paint.setColor(Color.YELLOW);
        canvas.drawRect(getRect(), paint);
    }
}
