package com.mulganov.lib.mg2d.graph;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Pixel extends Graph{
    private Paint p;

    public Pixel(String key,  int x,  int y,  Paint p){
        setKey(key);
        setX(x);
        setY(y);
        setW(1);
        setH(1);
        this.p = p;

        super.setDraw(new Graph.Draw() {
            @Override
            public void draw(Canvas canvas) {
                canvas.drawPoint(getX(), getY(), getPaint());
            }
        });
    }

    @Override
    public boolean isClickRectTemplate(Graph.Click.Event event){
        int xx = event.x;
        int yy = event.y;

        int x = getX();
        int y = getY();

        if (xx == x){
            if (yy == y){
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


    public void setPaint(Paint p){
        this.p = p;
    }

    public Paint getPaint(){
        return p;
    }
}
