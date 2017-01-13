package com.nathanfei.laststand.framework;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable {

    Thread thread = null;
    SurfaceHolder holder;
    volatile boolean running;
    Canvas canvas;
    Paint paint;

    public GameView(Context context) {
        super(context);
        holder = getHolder();
        paint = new Paint();
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60d;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        while (running) {
            long currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / ns;
            lastTime = currentTime;
            while (delta >= 1) {
                tick();
                delta--;
            }
            render();
        }
    }

    private void tick() {

    }

    private void render() {
        if (holder.getSurface().isValid()) {
            canvas = holder.lockCanvas();
            // draw stuff
            holder.unlockCanvasAndPost(canvas);
        }
    }

    public void pause() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        running = true;
        thread = new Thread(this);
        thread.start();
    }
}
