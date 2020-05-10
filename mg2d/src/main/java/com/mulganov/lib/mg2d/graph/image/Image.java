package com.mulganov.lib.mg2d.graph.image;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.mulganov.lib.mg2d.graph.Graph;

public class Image extends Graph {

    private Bitmap bitmap;
    private mg2dMatrix matrix;

    private Paint p;

    public Image(String key, final Bitmap bitmap, mg2dMatrix matrix){
        this.matrix = matrix;
        setKey(key);
        setW(matrix.getW());
        setH(matrix.getH());
        setX(matrix.getTx());
        setY(matrix.getTy());
        this.bitmap = bitmap;
        super.setDraw(new Draw() {
            @Override
            public void draw(Canvas canvas) {
                if (p == null){
                    p = new Paint();
                    p.setAlpha(getAlpha());
                }

                canvas.drawBitmap(getBitmap(), getMatrix().create(bitmap), p);
            }
        });
    }

    @Override
    public boolean isClickRectTemplate(Click.Event event){
        int xx = event.x;
        int yy = event.y;

        int x = getX();
        int y = getY();

        if (x <= xx && xx <= x + matrix.getW()){
            if (y <= yy && yy <= y + matrix.getH()){
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

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public mg2dMatrix getMatrix() {
        return matrix;
    }

    public void setMatrix(mg2dMatrix matrix) {
        this.matrix = matrix;
    }

    @Override
    public void setX(int x){
        matrix.setTx(x);
        super.setX(x);
    }

    @Override
    public void setY(int y){
        matrix.setTy(y);
        super.setY(y);
    }

}
