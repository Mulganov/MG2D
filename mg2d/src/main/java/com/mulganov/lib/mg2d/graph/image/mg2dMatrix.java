package com.mulganov.lib.mg2d.graph.image;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class mg2dMatrix {

    public static class Flag{
        public final static String WIDTH = "WIDTH";
        public final static String HEIGHT = "HEIGHT";
    }

    private int tx, ty;
    private int w, h;
    private int size = 0;
    private String flag;

    public Matrix create(Bitmap bitmap){
        Matrix matrix = new Matrix();

        if (flag != null){
            if (flag.equalsIgnoreCase(Flag.WIDTH)){

                this.w = size;
                this.h = (int) (((float)size * bitmap.getHeight()) / bitmap.getWidth());
                matrix.postScale((float)this.w  / bitmap.getWidth(), (float)this.h  / bitmap.getHeight());
            }
            else
            if (flag.equalsIgnoreCase(Flag.HEIGHT)){

                this.w = (int) (((float)size * bitmap.getWidth()) / bitmap.getHeight());
                this.h = size;
                matrix.postScale((float)this.w  / bitmap.getWidth(), (float)this.h  / bitmap.getHeight());
            }
        }else
        if (size == 0)
            matrix.postScale((float) w / bitmap.getWidth(), (float)h / bitmap.getHeight());
        else
            matrix.postScale((float) size / bitmap.getWidth(), (float)size / bitmap.getHeight());
        matrix.postTranslate(tx, ty);

        return matrix;
    }

    public mg2dMatrix(int tx, int ty, int w, int h){
        this.tx = tx;
        this.ty = ty;
        this.w = w;
        this.h = h;
    }

    public mg2dMatrix(int tx, int ty, int size){
        this.tx = tx;
        this.ty = ty;
        this.size = size;

        this.w = size;
        this.h = size;
    }

    public mg2dMatrix(int tx, int ty, String flag, int size){
        this.tx = tx;
        this.ty = ty;
        this.flag = flag;
        this.size = size;
    }


//    public mg2dMatrix(float sx, float sy, float tx, float ty){
//        this.sx = sx;
//        this.sy = sy;
//        this.tx = tx;
//        this.ty = ty;
//    }


    public int getTx() {
        return tx;
    }

    public void setTx(int tx) {
        this.tx = tx;
    }

    public int getTy() {
        return ty;
    }

    public void setTy(int ty) {
        this.ty = ty;
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
}
