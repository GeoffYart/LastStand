package com.nathanfei.laststand.objects;

import android.graphics.Canvas;

import com.nathanfei.laststand.framework.GameObject;
import com.nathanfei.laststand.framework.GameView;

public class Bob extends GameObject {

    private int color;
    private int counter = 0;

    public Bob(int x, int y, int color) {
        super(x, y);
        width = 250;
        height = 250;
        this.color = color;
    }

    @Override
    public void tick() {
        GameView.getObjHandler().addObject(new Test(x, y));
    }

    @Override
    public void render(Canvas canvas) {
        paint.setColor(color);
        canvas.drawRect(getRect(), paint);
    }
}
