package com.nathanfei.laststand.framework;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.nathanfei.laststand.objects.Button;
import com.nathanfei.laststand.objects.Shooter;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    public static final int MAX_FPS = 30;

    private Thread thread = null;
    private boolean running = false;
    private SurfaceHolder holder;
    private static GameState gameState = GameState.Menu;

    private static ObjHandler objHandler, menuHandler, gameplayHandler, gameOverHandler;

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

        menuHandler = new ObjHandler(new Button(0.5, 0.2, 0.8, 0.2, "PLAY", GameState.GamePlay),
                new Button(0.5, 0.7, 0.8, 0.2, "QUIT", GameState.Quit));
        gameplayHandler = new ObjHandler(new Button(0.90, 0.05, 0.2, 0.05, "MENU", GameState.Menu),
                new Shooter(0.5, 0.95));
        gameOverHandler = new ObjHandler(new Button(0.5, 0.075, 1, 0.15, "GAME", GameState.GameOver),
                new Button(0.5, 0.225, 1, 0.15, "OVER", GameState.GameOver),
                new Button(0.5, 0.7, 0.8, 0.2, "MENú", GameState.Menu));

        setHandler();
    }

    private static void setHandler() {
        switch (gameState) {
            case Menu:
                objHandler = menuHandler;
                break;
            case GamePlay:
                objHandler = gameplayHandler;
                break;
            case GameOver:
                objHandler = gameOverHandler;
                break;
            case Quit:
                System.exit(1);
            default:
                break;
        }
    }

    public static void setGameState(GameState gs) {
        gameState = gs;
        setHandler();
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
                    for (GameObject object : objHandler.list()) {
                        if (object instanceof Shooter) {
                            object.setX((double)motionEvent.getX() / (double)getWidth());
                        } else if (object instanceof Button && object.getRect()
                                .contains((int)motionEvent.getX(), (int)motionEvent.getY())) {
                            ((Button)object).tap();
                        }
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    for (GameObject object : objHandler.list()) {
                        if (object instanceof Shooter) {
                            object.setX((double)motionEvent.getX() / (double)getWidth());
                        }
                    }
                    break;
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
