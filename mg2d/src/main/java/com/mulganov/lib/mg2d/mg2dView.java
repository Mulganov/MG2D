package com.mulganov.lib.mg2d;


import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.mulganov.lib.mg2d.control.ControlManager;
import com.mulganov.lib.mg2d.graph.Graph;
import com.mulganov.lib.mg2d.scena.Scena;


public class mg2dView extends SurfaceView implements SurfaceHolder.Callback {
    private mg2dThread gameThread;

    public mg2dView(Context context, MG2D mg2D) {
        super(context);
        getHolder().addCallback(this);
        gameThread = new mg2dThread(getHolder(), mg2D);
    }

    public mg2dThread getThread(){
        return gameThread;
    }


    private int x, y;
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int idEvent = event.getActionMasked();

        int ex = (int) event.getX();
        int ey = (int) event.getY();

        if (gameThread.getScena() == null) return true;

        Scena scena = gameThread.getScena();


        if (idEvent == MotionEvent.ACTION_UP){
            for (int i = 0; true; i++){
                if (gameThread.isNewScena()){
                    gameThread.setNewScena(false);
                    break;
                }
                if (scena.get(i) != null)
                    scena.get(i).clickUp(new Graph.Click.Event(ex, ey));
                else
                    break;
            }

            for (ControlManager.ClickEvent e: MG2D.getMG2D().getSetting().getControlManager().getEvents().getClickUpWindow()){
                e.Event(new ControlManager.ClickEvent.Info(ex, ey, MG2D.getMG2D().getDelta()));
            }
        }
        if (idEvent == MotionEvent.ACTION_DOWN){
            for (int i = 0; true; i++){
                if (gameThread.isNewScena()){
                    gameThread.setNewScena(false);
                    break;
                }
                if (scena.get(i) != null)
                    scena.get(i).click(new Graph.Click.Event(ex, ey));
                else
                    break;
            }

            for (ControlManager.ClickEvent e: MG2D.getMG2D().getSetting().getControlManager().getEvents().getClickDownWindow()){
                e.Event(new ControlManager.ClickEvent.Info(ex, ey, MG2D.getMG2D().getDelta()));
            }

        }
        if (idEvent == MotionEvent.ACTION_MOVE){
            if (x == 0 && y == 0){
                x = ex;
                y = ey;
            }

            for (int i = 0; true; i++){
                if (gameThread.isNewScena()){
                    gameThread.setNewScena(false);
                    break;
                }
                if (scena.get(i) != null){
                    scena.get(i).move(new Graph.Click.Event(ex, ey), MG2D.getMG2D().getDelta());
                }
                else
                    break;
            }

            for (ControlManager.MoveEvent e: MG2D.getMG2D().getSetting().getControlManager().getEvents().getMoveWindow()){
                e.Event(new ControlManager.ClickEvent.Info(ex, ey, MG2D.getMG2D().getDelta()), new ControlManager.MoveEvent.Info(ex - x, ey - y, MG2D.getMG2D().getDelta()));
            }
            x = ex;
            y = ey;
        }else
        {
            x = 0;
            y = 0;
        }


        return true; //processed
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
//        gameThread = new mg2dThread(getHolder());
        gameThread.setRunning(true);
        gameThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        // завершаем работу потока
        gameThread.setRunning(false);
        while (retry) {
            try {
                gameThread.join();
                retry = false;
            } catch (InterruptedException e) {
                // если не получилось, то будем пытаться еще и еще
            }
        }
    }
}