package com.mulganov.lib.mg2d.graph.template;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.mulganov.lib.mg2d.graph.Graph;
import com.mulganov.lib.mg2d.graph.figure.Circle;
import com.mulganov.lib.mg2d.graph.figure.Line;

import java.util.ArrayList;

public class Joystick extends Graph {
    private Paint p;
    private int radius;
    private int range;
    private int activityRange;
    private Circle circle;
    private Paint border = new Paint();
    private JoystickEvent joystickEvent;
    private int cx, cy;
    private ArrayList<Line> lineList = new ArrayList<>();

    public static int ACTIVITY_RANGE_MAX = -1;

    public Joystick(String key, Circle circle){
        setKey(key);

        this.circle = circle;

        super.setX(circle.getX());
        super.setY(circle.getY());
        super.setW(circle.getW());
        super.setH(circle.getH());


        this.radius = circle.getRadius();
        this.p = circle.getPaint();

        setRange(radius*2);

        cx = getX();
        cy = getY();

        for (int i = 0; i < 360; i++){
            lineList.add(new Line("auto",
                    getCx() + getXn(i),
                    getCy() + getYn(i),
                    getCx() + getXn(i+1),
                    getCy() + getYn(i+1),
                    border));
        }

        super.setDraw(new Draw() {
            @Override
            public void draw(Canvas canvas) {
                    getCircle().draw(canvas);

                    for (Line line: lineList){
                        line.draw(canvas);
                    }
            }
        });

        super.setClickB(false);

        super.setClickUp(new ClickUp() {
            @Override
            public void onClick(Click.Event event) {
                    getCircle().setX(getCx());
                    getCircle().setY(getCy());
            }
        });

        super.setMove(new Move() {
            @Override
            public void onMove(int x, int y, long delta) {
                    float xx = 0;
                    float yy = 0;

                    if ( isClickCircleTemplate(new Click.Event(x, y)) ){
                        if ( Math.abs( getCx() - x ) < getRange() && Math.abs( getCy() - y ) < getRange()){
                            getCircle().setX(x);
                            getCircle().setY(y);
                            xx = ((float) x - cx) / (float)range;
                            yy = ((float) y - cy) / (float)range;

                            joystickEvent.Event(xx, yy, delta);

                            return;
                        }
                    }else
                    if (isClickCircleTemplate(new Click.Event(x, y), activityRange) ){
                        int c = getRange(getCx(), getCy(), x, y);
                        int b = Math.abs(y - getCy());
                        double sAlpha = (double)b/(double) c;
                        double alpha = Math.asin(sAlpha);
                        double angle = alpha * 180/Math.PI;

                        if ( x - getCx() >= 0 && y - getCy() <= 0){
                            getCircle().setX(getCx() + getXn((float) -angle));
                            getCircle().setY(getCy() + getYn((float) -angle));
                        }else

                        if ( x - getCx() >= 0 && y - getCy() >= 0){
                            getCircle().setX(getCx() + getXn((float) -angle));
                            getCircle().setY(getCy() + getYn((float)  angle));
                        }else

                        if ( x - getCx() <= 0 && y - getCy() <= 0){
                            getCircle().setX(getCx() + getXn((float) angle + 180));
                            getCircle().setY(getCy() + getYn((float) angle + 180));
                        }else

                        if ( x - getCx() <= 0 && y - getCy() >= 0){
                            getCircle().setX(getCx() + getXn((float)   angle + 180));
                            getCircle().setY(getCy() + getYn((float)  -angle + 180));
                        }

                        xx = ((float) x - cx) / (float)c;
                        yy = ((float) y - cy) / (float)c;


                        joystickEvent.Event(xx, yy, delta);

                        return;
                    }else{
                        getCircle().setX(getCx());
                        getCircle().setY(getCy());
                    }
            }
        });
    }

    public void reload(){
        setCx(getX());
        setCy(getY());

        getCircle().setX(getCx());
        getCircle().setY(getCy());

        for (int i = 0; i < 360; i++){
            Line line = lineList.get(i);

            line.setX0(getCx() + getXn(i));
            line.setY0(getCy() + getYn(i));
            line.setX1(getCx() + getXn(i+1));
            line.setY1(getCy() + getYn(i+1));
        }
    }

    private int getXn(float angle){
        return (int) (Math.cos(getRadian(angle)) * getRange());
    }
    private int getYn(float angle){
        return (int) (Math.sin(getRadian(angle)) * getRange());
    }

    private double getRadian(float angle){
        return angle * Math.PI/180;
    }

    private int getRange(int x0, int y0, int x1, int y1){
        return (int) Math.sqrt( (x0-x1)*(x0-x1) +  (y0-y1)*(y0-y1));
    }

    @Override
    public void setDrawB(boolean drawB){
        super.setDrawB(drawB);
        circle.setDrawB(drawB);
        for (Line line: lineList){
            line.setDrawB(drawB);
        }
    }

    @Override
    public void setX(int x){
        super.setX(x);
        circle.setX(x);
        reload();
    }
    @Override
    public void setY(int y){
        super.setY(y);
        circle.setY(y);
        reload();
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

    public boolean isClickCircleTemplate(Click.Event event, int radius){
        if (radius == ACTIVITY_RANGE_MAX) return true;

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

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public JoystickEvent getJoystickEvent() {
        return joystickEvent;
    }

    public void setJoystickEvent(JoystickEvent joystickEvent) {
        this.joystickEvent = joystickEvent;
    }

    public void joystickEvent(float x, float y, long delay){
        joystickEvent.Event(x, y, delay);
    }

    public int getCx() {
        return cx;
    }

    public void setCx(int cx) {
        this.cx = cx;
    }

    public int getCy() {
        return cy;
    }

    public void setCy(int cy) {
        this.cy = cy;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public Paint getBorder() {
        return border;
    }

    public void setBorder(Paint border) {
        this.border = border;
    }

    public int getActivityRange() {
        return activityRange;
    }

    public void setActivityRange(int activityRange) {
        this.activityRange = activityRange;
    }

    public interface JoystickEvent{
        void Event(float x, float y, long delta);
    }
}
