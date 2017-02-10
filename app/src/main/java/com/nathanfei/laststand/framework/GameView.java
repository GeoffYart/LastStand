package com.nathanfei.laststand.framework;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;

import com.nathanfei.laststand.objects.Bob;
import com.nathanfei.laststand.objects.Shooter;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    public static final int MAX_FPS = 30;

    private Thread thread = null;
    private boolean running = false;
    private SurfaceHolder holder;

    private static ObjHandler objHandler;
    private ObjHandler menuHandler, gameplayHandler;

    public static ObjHandler getObjHandler() {
        return objHandler;
    }

    public GameView(Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback(this);
        setFocusable(true);
        thread = new Thread(this);
        thread.start();

        menuHandler = new ObjHandler(new Shooter(MainActivity.screenWidth()/2, MainActivity.screenHeight() - 200));
        gameplayHandler = new ObjHandler();

        objHandler = menuHandler;
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        long frameTime;
        long waitTime;
        long millis = 1000 / MAX_FPS;

        Canvas canvas;

        while (running) {

            canvas = holder.lockCanvas();
            synchronized (holder) {
                tick();
                render(canvas);
            }

            holder.unlockCanvasAndPost(canvas);

            frameTime = (System.nanoTime() - lastTime) / 1000000;
            waitTime = millis - frameTime;
            if (waitTime > 0) {
                try {
                    Thread.sleep(waitTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void tick() {
        objHandler.tick();
    }

    public void render(Canvas canvas) {
        super.draw(canvas);
        objHandler.render(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        synchronized (holder) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                    for (GameObject object : objHandler.list()) {
                        if (object instanceof Shooter) {
                            object.setX((int) motionEvent.getX());
                        }
                    }
            }
        }
        return true;
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new Thread(this);
        running = true;
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        while (true) {
            try {
                running = false;
                thread.join();
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
