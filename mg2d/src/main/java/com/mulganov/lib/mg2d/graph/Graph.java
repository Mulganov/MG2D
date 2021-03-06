package com.mulganov.lib.mg2d.graph;

import android.graphics.Canvas;

import com.mulganov.lib.mg2d.control.ControlManager;

public class Graph {
    private String key;
    private int index;

    private int w;
    private int h;

    private int alpha = 255;

    private int x;
    private int y;

    private Draw draw;
    private Click click;
    private Move move;
    private ClickUp clickUp;

    private boolean drawB = true;
    private boolean clickB;
    private boolean clickUpB;
    private boolean moveB;


    public String getKey() {
        return key;
    }

    public void draw(Canvas canvas){
        if (draw != null && drawB)
            draw.draw(canvas);
    }
    public void click(Click.Event event){
        if (click != null && clickB && drawB)
            click.onClick(event);
    }
    public void clickUp(Click.Event event){
        if (clickUp != null && clickUpB && drawB)
            clickUp.onClick(event);
    }
    public void move(Click.Event event, long d){
        if (move != null && moveB && drawB)
            move.onMove(event.x, event.y, d);
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Draw getDraw() {
        return draw;
    }

    public void setDraw(Draw draw) {
        this.draw = draw;
    }

    public Click getClick() {
        return click;
    }

    public void setClick(Click click) {
        this.click = click;
        this.clickB = true;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isClickB() {
        return clickB;
    }

    public void setClickB(boolean clickB) {
        this.clickB = clickB;
    }

    public boolean isDrawB() {
        return drawB;
    }

    public void setDrawB(boolean drawB) {
        this.drawB = drawB;
    }

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public boolean isMoveB() {
        return moveB;
    }

    public void setMoveB(boolean moveB) {
        this.moveB = moveB;
    }

    public void setMove(Move move) {
        this.move = move;
        this.moveB = true;
    }

    public boolean isClickUpB() {
        return clickUpB;
    }

    public void setClickUpB(boolean clickUpB) {
        this.clickUpB = clickUpB;
    }

    public ClickUp getClickUp() {
        return clickUp;
    }

    public void setClickUp(ClickUp clickUp) {
        this.clickUp = clickUp;
        clickUpB = true;
    }

    public interface Click {
        void onClick(Event event);
        public static class Event{
            public int x, y;

            public Event(int x, int y){
                this.x = x;
                this.y = y;
            }
        }
    }

    public interface Move{
        void onMove(int x, int y, long delta);
    }

    public interface ClickUp{
        void onClick(Click.Event event);
    }

    public interface Draw {
        void draw(Canvas canvas);
    }

    public String toString(){
        return new com.google.gson.GsonBuilder().setPrettyPrinting().create().toJson(Info.getInfo(this));
    }

    public static class Info{
        private String key;
        private int index;

        private int alpha;

        private int x;
        private int y;

        public static Info getInfo(Graph graph){
            Info info = new Info();

            info.setIndex(graph.getIndex());
            info.setKey(graph.getKey());


            info.setX(graph.getX());
            info.setY(graph.getY());

            info.setAlpha(graph.getAlpha());

            return info;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getAlpha() {
            return alpha;
        }

        public void setAlpha(int alpha) {
            this.alpha = alpha;
        }
    }

    public boolean isClickRectTemplate(Click.Event event){
        int xx = event.x;
        int yy = event.y;

        int x = getX();
        int y = getY();

        return false;
    }

    public boolean isClickCircleTemplate(Click.Event event){
        int xx = event.x;
        int yy = event.y;

        int x = getX();
        int y = getY();

        return false;
    }

}
