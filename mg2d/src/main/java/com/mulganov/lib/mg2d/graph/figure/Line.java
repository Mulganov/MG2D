package com.mulganov.lib.mg2d.graph.figure;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.mulganov.lib.mg2d.graph.Graph;

public class Line extends Graph{

    private Paint p;
    private int x0, y0;
    private int x1, y1;

    public Line(String key, int x0,  int y0,  int x1,  int y1,  Paint p){
        setKey(key);
        setX(x0);
        setY(y0);
        setW(x1);
        setH(y1);

        this.x0 = x0;
        this.y0 = y0;

        this.x1 = x1;
        this.y1 = y1;

        this.p = p;

        super.setDraw(new Graph.Draw() {
            @Override
            public void draw(Canvas canvas) {
                canvas.drawLine(getX0(), getY0(), getX1(), getY1(), getPaint());
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
    public void setAlpha(int alpha){
        super.setAlpha(alpha);
        p.setAlpha(alpha);
    }

    public int getX0() {
        return x0;
    }

    public void setX0(int x0) {
        this.x0 = x0;
    }

    public int getY0() {
        return y0;
    }

    public void setY0(int y0) {
        this.y0 = y0;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }
}
