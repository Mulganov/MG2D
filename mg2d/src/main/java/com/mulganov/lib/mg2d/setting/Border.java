package com.mulganov.lib.mg2d.setting;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;

import com.mulganov.lib.mg2d.MG2D;
import com.mulganov.lib.mg2d.graph.GraphGroup;
import com.mulganov.lib.mg2d.graph.figure.Rect;
import com.mulganov.lib.mg2d.scena.Scena;

public class Border {

    private boolean status = true;
    private int color = Color.BLACK;
    // todo
//    private BitmapBorder bitmapBorder;

    public GraphGroup getGraphGroup(MG2D mg2D){

        GraphGroup gg = new GraphGroup();

//        if (bitmapBorder != null){
//            if (bitmapBorder.up != null){
//                Image up;
//                if (bitmapBorder.full.equalsIgnoreCase(BitmapBorder.FULL_LEFT_AND_RIGHT)){
//                    up = new Image("border_up", bitmapBorder.up, new mg2dMatrix(mg2D.getSetting().getSize().getX(), 0, mg2D.getSetting().getSize().getX() + mg2D.getSetting().getSize().getW(), mg2D.getSetting().getSize().getY()));
//                }else{
//                    up = new Image("border_up", bitmapBorder.up, new mg2dMatrix(0, 0, mg2D.getSetting().getSize().getOriginalW()));
//                }
//                gg.add(up);
//            }else{
//                Rect up = new Rect("border_up", 0, 0, mg2D.getSetting().getSize().getOriginalW(), paint);
//            }
//        }

        Scena.Size size = mg2D.getSetting().getSize();

        Paint paint = new Paint();

        paint.setColor(color);

        Rect up = new Rect("border_up", 0, 0, size.getOriginalW(), size.getY(), paint);
        Rect down = new Rect("border_down", 0, size.getH() + size.getY(), size.getOriginalW(), size.getOriginalH(), paint);

        Rect left = new Rect("border_left", 0, 0, size.getX(), size.getOriginalH(), paint);
        Rect right = new Rect("border_right", size.getX() + size.getW(), 0, size.getOriginalW(), size.getOriginalH(), paint);


        gg.add(up);
        gg.add(left);
        gg.add(right);
        gg.add(down);
        return gg;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }


    public static class BitmapBorder{
        public static String FULL_TOP_AND_DOWN = "FULL_TOP_AND_DOWN";
        public static String FULL_LEFT_AND_RIGHT = "FULL_LEFT_AND_RIGHT";

        private String full;
        private Bitmap up;
        private Bitmap down;
        private Bitmap left;
        private Bitmap right;

        public Bitmap getUp() {
            return up;
        }

        public void setUp(Bitmap up) {
            this.up = up;
        }

        public Bitmap getDown() {
            return down;
        }

        public void setDown(Bitmap down) {
            this.down = down;
        }

        public Bitmap getLeft() {
            return left;
        }

        public void setLeft(Bitmap left) {
            this.left = left;
        }

        public Bitmap getRight() {
            return right;
        }

        public void setRight(Bitmap right) {
            this.right = right;
        }

        public String getFull() {
            return full;
        }

        public void setFull(String full) {
            this.full = full;
        }
    }
}
