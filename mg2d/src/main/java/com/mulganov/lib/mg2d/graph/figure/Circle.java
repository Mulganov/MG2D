package com.mulganov.lib.mg2d.graph.figure;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.mulganov.lib.mg2d.graph.Graph;

public class Circle extends Graph {
    private Paint p;
    private int radius;

    public Circle(String key, final int x, final int y, final int r, final Paint p){
        setKey(key);
        setX(x);
        setY(y);
        setW(r);
        setH(r);
        this.radius = r;
        this.p = p;

        super.setDraw(new Draw() {
            @Override
            public void draw(Canvas canvas) {
                canvas.drawCircle(getX(), getY(), getRadius(), getPaint());
            }
        });
    }

    public void setPaint(Paint p){
        this.p = p;
    }

    public Paint getPaint(){
        return p;
    }


    @Override
    public boolean isClickCircleTemplate(Click.Event event){
        int xx = event.x;
        int yy = event.y;

        int x = getX();
        int y = getY();

        if ( x-radius <= xx && xx <= x+radius ){
            if ( y-radius <= yy && yy <= y+radius ){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isClickRectTemplate(Click.Event event){
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

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
