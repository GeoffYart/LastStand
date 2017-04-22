package com.nathanfei.laststand.objects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;

import com.nathanfei.laststand.framework.GameObject;
import com.nathanfei.laststand.framework.GameState;
import com.nathanfei.laststand.framework.GameView;
import com.nathanfei.laststand.framework.MainActivity;

public class Button extends GameObject {

    private String text;
    private GameState target;

    public Button(double x, double y, double width, double height, String text, GameState target) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.text = text;
        this.target = target;
        stretchText();
    }

    private void stretchText() {
        float testTextSize = 48f;
        paint.setTextSize(testTextSize);
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        float textW = testTextSize * (float)(width * (double)MainActivity.screenWidth()) / bounds.width();
        float textH = testTextSize * (float)(height * (double)MainActivity.screenHeight()) / bounds.height();
        if (textW < textH)
            paint.setTextSize(textW);
        else
            paint.setTextSize(textH);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Canvas canvas) {
        paint.setColor(Color.BLUE);
        canvas.drawRect(getRect(), paint);
        paint.setColor(Color.WHITE);
        canvas.drawText(text, (float)(getRect().left),
                (float)(getRect().bottom), paint);
    }

    public void tap() {
        GameView.setGameState(target);
    }
}
