package com.nathanfei.laststand.objects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;

import com.nathanfei.laststand.framework.GameObject;
import com.nathanfei.laststand.framework.GameState;
import com.nathanfei.laststand.framework.GameView;
import com.nathanfei.laststand.framework.MainActivity;

import java.util.Random;

public class Shooter extends GameObject {

    private int tickCount = 0;
    private Random rand = new Random();
    private int damage = 5;
    private int maxHealth = 1000;
    private int health = maxHealth;

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
            GameView.getObjHandler().addObject(new Enemy(rand.nextDouble(), -height, (int)Math.sqrt(tickCount)));
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

    public void hit(int enemHealth) {
        health -= enemHealth;
        if (health <= 0)
            gameOver();
    }

    @Override
    public void render(Canvas canvas) {
        //draw player thing
        paint.setColor(Color.BLUE);
        canvas.drawRect(getRect(), paint);
        //draw healthbar thing
        paint.setColor(Color.RED);
        canvas.drawRect(new Rect(0, (int)(0.99 * MainActivity.screenHeight()),
                MainActivity.screenWidth(), MainActivity.screenHeight()), paint);
        paint.setColor(Color.GREEN);
        canvas.drawRect(new Rect(0, (int)(0.99 * MainActivity.screenHeight()),
                (int)(((double)health/(double)maxHealth) * MainActivity.screenWidth()),
                MainActivity.screenHeight()), paint);
        //draw score
        paint.setColor(Color.WHITE);
        float testTextSize = 48f;
        paint.setTextSize(testTextSize);
        Rect bounds = new Rect();
        paint.getTextBounds("test", 0, 4, bounds);
        paint.setTextSize(testTextSize * (float)(0.06 * (double)MainActivity.screenHeight()) / bounds.height());
        canvas.drawText("Score: " + (tickCount / 6), 0, (int)(0.06 * MainActivity.screenHeight()), paint);
    }

    private void gameOver() {
        GameView.setGameState(GameState.GameOver);
        GameView.resetGameplayHandler();
    }
}
