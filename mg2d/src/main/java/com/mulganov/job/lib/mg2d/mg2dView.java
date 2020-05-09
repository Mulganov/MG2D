package com.mulganov.job.lib.mg2d;


import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.mulganov.job.lib.mg2d.graph.Graph;
import com.mulganov.job.lib.mg2d.scena.Scena;


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

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int idEvent = event.getActionMasked();

        int ex = (int) event.getX();
        int ey = (int) event.getY();

        if (gameThread.getScena() == null) return true;

        Scena scena = gameThread.getScena();

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