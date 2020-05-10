package com.mulganov.lib.mg2d;


import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.mulganov.lib.mg2d.scena.Scena;

public class mg2dThread extends java.lang.Thread {

    private MG2D mg2D;

    private boolean runFlag = true;
    private SurfaceHolder surfaceHolder;

    private long deltaNew;

    private Scena scena;
    private boolean newScena;

    public mg2dThread(SurfaceHolder surfaceHolder, MG2D mg2D) {
        this.surfaceHolder = surfaceHolder;
        this.mg2D = mg2D;
    }


    public void setRunning(boolean run) {
        runFlag = run;
    }

    @Override
    public void run() {
        Canvas canvas;


        while (runFlag) {
            deltaNew = System.currentTimeMillis();
            canvas = null;
            try {
                // получаем объект Canvas и выполняем отрисовку
                canvas = surfaceHolder.lockCanvas();
                if (canvas == null) return;
                synchronized (surfaceHolder) {
                    canvas.drawColor(mg2D.getSetting().getBackground());

                    if (scena == null) continue;

                    for (int i = 0; true; i++){
                        if (newScena){
                            newScena = false;
                            break;
                        }
                        if (scena.get(i) != null)
                            scena.get(i).draw(canvas);
                        else
                            break;
                    }

                    if (mg2D.getSetting().getBorder().isStatus()){
                        mg2D.getSetting().getBorder().getGraphGroup(mg2D).draw(canvas);
                    }
                }

            } finally {
                if (canvas != null) {
                    // отрисовка выполнена. выводим результат на экран
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }

        }
    }

    public Scena getScena() {
        return scena;
    }

    public void setScena(Scena scena) {
        this.scena = scena;
        newScena = true;
    }

    public boolean isNewScena(){
        return newScena;
    }

    public void setNewScena(boolean b) {
        this.newScena = b;
    }

    public long getDelta(){return System.currentTimeMillis() - deltaNew;}

}


