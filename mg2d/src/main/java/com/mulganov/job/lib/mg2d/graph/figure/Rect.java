package com.mulganov.job.lib.mg2d.graph.figure;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.mulganov.job.lib.mg2d.graph.Graph;
import com.mulganov.job.lib.mg2d.graph.image.mg2dMatrix;

public class Rect extends Graph {
    private Paint p;

    public Rect(String key, final int x, final int y, final int w, final int h, final Paint p){
        setKey(key);
        setX(x);
        setY(y);
        setW(w);
        setH(h);
        this.p = p;

        super.setDraw(new Draw() {
            @Override
            public void draw(Canvas canvas) {

                android.graphics.Rect r = new android.graphics.Rect();

                r.right = x;
                r.left = w;
                r.top = y;
                r.bottom = h;

                canvas.drawRect(r, p);
            }
        });
    }

    @Override
    public boolean isClickRecTemplate(Click.Event event){
        int xx = event.x;
        int yy = event.y;

        int x = getX();
        int y = getY();

        if (x <= xx && xx <= x + getW()){
            if (y <= yy && yy <= y + getH()){
                return true;
            }
        }
        return false;
    }

    @Override
    public void setAlpha(int alpha){
        super.setAlpha(alpha);
        p.setAlpha(alpha);
    }
}
