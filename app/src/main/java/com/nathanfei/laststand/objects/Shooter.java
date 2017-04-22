package com.nathanfei.laststand.objects;

import android.graphics.Canvas;
import android.graphics.Color;

import com.nathanfei.laststand.framework.GameObject;
import com.nathanfei.laststand.framework.GameState;
import com.nathanfei.laststand.framework.GameView;
import com.nathanfei.laststand.framework.MainActivity;

import java.util.Random;

public class Shooter extends GameObject {

    private int tickCount = 0;
    private Random rand = new Random();
    private int damage = 5;

    public Shooter(double x, double y) {
        super(x, y);
        width = 0.16;
        height = 0.09;
    }

    @Override
    public void tick() {
        if (tickCount % 2 == 0) {
            GameView.getObjHandler().addObject(new Bullet(x, y - height / 2, damage));
        }
        if (tickCount % 60 == 0) {
            GameView.getObjHandler().addObject(new Enemy(rand.nextDouble(), -height, 5));
            System.out.println(GameView.getObjHandler().getSize());
        }
        tickCount++;
        collision();
    }

    private void collision() {
        for (GameObject obj : GameView.getObjHandler().list()) {
            if (obj instanceof Enemy && getRect().intersect(obj.getRect())) {
                gameOver();
            }
        }
    }

    @Override
    public void render(Canvas canvas) {
        paint.setColor(Color.GREEN);
        canvas.drawRect(getRect(), paint);
    }

    private void gameOver() {
        GameView.setGameState(GameState.GameOver);
    }
}
