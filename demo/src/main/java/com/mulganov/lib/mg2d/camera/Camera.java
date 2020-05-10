package com.mulganov.lib.mg2d.camera;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.mulganov.lib.mg2d.MG2D;
import com.mulganov.lib.mg2d.control.ControlManager;
import com.mulganov.lib.mg2d.graph.Graph;
import com.mulganov.lib.mg2d.graph.figure.Circle;
import com.mulganov.lib.mg2d.graph.figure.Line;
import com.mulganov.lib.mg2d.scena.Scena;

import java.util.ArrayList;

public class Camera extends Graph {

    private static Camera camera;
    public static Camera getCamera(boolean reload){
        if (camera == null)
            camera = new Camera();

        if (reload)
            camera.reload();

        return camera;
    }

    private void reload() {

        MG2D.getMG2D().getSetting().getControlManager().getRegister().addMoveWindow(new ControlManager.MoveEvent() {
            @Override
            public void Event(ControlManager.ClickEvent.Info clickInfo, Info moveInfo) {
                setAngle((float) (getAngle()-(double)moveInfo.getX()*15 * moveInfo.getDelta()/1000));
            }
        });

        size = MG2D.getMG2D().getSetting().getSize();

        super.setX(size.getW()/2);
        super.setY(size.getH()/2);
        super.setW(size.getH()/30);
        super.setH(size.getH()/30);

        circle = new Circle("camera_circle", getX(), getY(), getW(), pc);

        ln = new Line("camera_ln", getX(), getY(), getX()+ getXn(angle), getY() + getYn(angle), pln);
        ll = new Line("camera_ln", getX(), getY(), getX()+ getXn(angle-35), getY() + getYn(angle-35), pln);
        lr = new Line("camera_ln", getX(), getY(), getX()+ getXn(angle+35), getY() + getYn(angle+35), pln);

        for (int i = 0; i < lineList.size()-1; i++){
            lineList.get(i).setX0(getX()+ getXn(angle-35 + i));
            lineList.get(i).setY0(getY()+ getYn(angle-35 + i));

            lineList.get(i).setX1(getX()+ getXn(angle-35 + i+1));
            lineList.get(i).setY1(getY()+ getYn(angle-35 + i+1));
        }

        super.setDraw(new Draw() {
            @Override
            public void draw(Canvas canvas) {

                if (rx != 0 && ry != 0){
                    setX((int) (getX() + rx * 1000 * MG2D.getMG2D().getDelta()/1000));
                    setY((int) (getY() + ry * 1000 * MG2D.getMG2D().getDelta()/1000));
                    reloadAttributes();
                }

                circle.draw(canvas);

                ln.draw(canvas);
                ll.draw(canvas);
                lr.draw(canvas);

                for (int i = 0; i < lineList.size()-1; i++){
                    lineList.get(i).draw(canvas);
                }
            }
        });


    }

    private ArrayList<Line> lineList = new ArrayList<>();
    private float angle;
    private int range;
    private Circle circle;
    private Line ll, lr, ln;
    private Scena.Size size;
    private Paint pc, pln, px;

    private float rx, ry;

    private Camera(){
        setKey("camera");

        pc = new Paint();
        pc.setColor(Color.YELLOW);

        px = new Paint();
        px.setColor(Color.YELLOW);
        px.setStrokeWidth(2);

        pln = new Paint();
        pln.setColor(Color.YELLOW);
        pln.setStrokeWidth(2);

        size = MG2D.getMG2D().getSetting().getSize();

        range = size.getH()/8;
//        angle = 90;

        for (int i = 0; i < 35*2 + 1; i++){
            lineList.add(new Line("camera_line_" + i, 0, 0, 100, 100, px));
        }

        reload();


    }

    @Override
    public void setX(int x){
        super.setX(x);
        reloadAttributes();
    }

    @Override
    public void setY(int y){
        super.setY(y);
        reloadAttributes();
    }

    private void reloadAttributes() {
        circle.setX(getX());
        circle.setY(getY());

        ln.setX0(getX());
        ln.setY0(getY());
        ln.setX1(getX()+ getXn(angle));
        ln.setY1(getY()+ getYn(angle));

        ll.setX0(getX());
        ll.setY0(getY());
        ll.setX1(getX()+ getXn(angle-35));
        ll.setY1(getY()+ getYn(angle-35));

        lr.setX0(getX());
        lr.setY0(getY());
        lr.setX1(getX()+ getXn(angle+35));
        lr.setY1(getY()+ getYn(angle+35));

        for (int i = 0; i < lineList.size()-1; i++){
            lineList.get(i).setX0(getX()+ getXn(angle-35 + i));
            lineList.get(i).setY0(getY()+ getYn(angle-35 + i));

            lineList.get(i).setX1(getX()+ getXn(angle-35 + i+1));
            lineList.get(i).setY1(getY()+ getYn(angle-35 + i+1));
        }
    }


    public int getXn(float angle){
        return (int) (Math.cos(getRadian(angle)) * range);
    }
    public int getYn(float angle){
        return (int) (Math.sin(getRadian(angle)) * range);
    }

    public double getRadian(float angle){
        return angle * Math.PI/180;
    }

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public Line getLl() {
        return ll;
    }

    public void setLl(Line ll) {
        this.ll = ll;
    }

    public Line getLr() {
        return lr;
    }

    public void setLr(Line lr) {
        this.lr = lr;
    }

    public Line getLn() {
        return ln;
    }

    public void setLn(Line ln) {
        this.ln = ln;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        if (angle >= 360)
            angle -= 360;
        if (angle <= -360)
            angle += 360;
        this.angle = angle;
        reloadAttributes();
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public ArrayList<Line> getLineList() {
        return lineList;
    }

    public void setLineList(ArrayList<Line> lineList) {
        this.lineList = lineList;
    }

    public float getRx() {
        return rx;
    }

    public void setRx(float rx) {
        this.rx = rx;
    }

    public float getRy() {
        return ry;
    }

    public void setRy(float ry) {
        this.ry = ry;
    }
}
