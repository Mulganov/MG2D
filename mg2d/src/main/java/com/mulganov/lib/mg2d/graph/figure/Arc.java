package com.mulganov.lib.mg2d.graph.figure;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.mulganov.lib.mg2d.graph.Graph;

public class Arc extends Graph {

    private Paint p;
    private float startAngle, sweepAngle;
    private boolean useCenter;

    public Arc(String key,  int x,  int y,  int w,  int h,  float startAngle,  float sweepAngle,  boolean useCenter,  Paint p){
        setKey(key);
        setX(x);
        setY(y);
        setW(w);
        setH(h);
        this.p = p;

        super.setDraw(new Draw() {
            @Override
            public void draw(Canvas canvas) {

                android.graphics.RectF r = new android.graphics.RectF();

                r.right = getX();
                r.left = getW();
                r.top = getY();
                r.bottom = getH();

                canvas.drawArc(r, getStartAngle(), getSweepAngle(), isUseCenter(), getPaint());
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

    public float getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(float startAngle) {
        this.startAngle = startAngle;
    }

    public float getSweepAngle() {
        return sweepAngle;
    }

    public void setSweepAngle(float sweepAngle) {
        this.sweepAngle = sweepAngle;
    }

    public boolean isUseCenter() {
        return useCenter;
    }

    public void setUseCenter(boolean useCenter) {
        this.useCenter = useCenter;
    }
}
