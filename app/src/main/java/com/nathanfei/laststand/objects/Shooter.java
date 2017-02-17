package com.nathanfei.laststand.objects;

import android.graphics.Canvas;
import android.graphics.Color;

import com.nathanfei.laststand.framework.GameObject;
import com.nathanfei.laststand.framework.GameView;

public class Shooter extends GameObject {

    private int shotCounts;

    public Shooter(int x, int y) {
        super(x, y);
        width = 300;
        height = 300;
        shotCounts = 0;
    }

    @Override
    public void tick() {
        if (shotCounts % 10 == 0)
            GameView.getObjHandler().addObject(new Bullet(x, y - height/2));
        shotCounts++;
    }

    @Override
    public void render(Canvas canvas) {
        paint.setColor(Color.GREEN);
        canvas.drawRect(getRect(), paint);
    }
}
