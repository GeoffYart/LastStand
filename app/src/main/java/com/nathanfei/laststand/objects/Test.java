package com.nathanfei.laststand.objects;

import android.graphics.Canvas;
import android.graphics.Color;

import com.nathanfei.laststand.framework.GameObject;

public class Test extends GameObject {

    public Test(int x, int y) {
        super(x, y);
        width = 100;
        height = 100;
    }

    @Override
    public void tick() {
        width -= 5;
        height -= 5;
        if (height <= 0 && width <= 0) {
            remove();
        }
    }

    @Override
    public void render(Canvas canvas) {
        paint.setColor(Color.RED);
        canvas.drawRect(getRect(), paint);
    }
}
