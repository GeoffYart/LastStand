package com.nathanfei.laststand.objects;

import android.graphics.Canvas;
import android.graphics.Color;

import com.nathanfei.laststand.framework.GameObject;
import com.nathanfei.laststand.framework.GameView;

public class Shooter extends GameObject {

    int shotCounts;

    public Shooter(int x, int y) {
        super(x, y);
        width = 300;
        height = 300;
        shotCounts = 0;
    }

    @Override
    public void tick() {
        shotCounts++;
        System.out.println(x + " " + y);
        if (shotCounts % 3 == 0)
            GameView.getObjHandler().addObject(new Bullet(x, y - height));
    }

    @Override
    public void render(Canvas canvas) {
        paint.setColor(Color.GREEN);
        canvas.drawRect(getRect(), paint);
    }
}
