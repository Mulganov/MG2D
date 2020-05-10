package com.mulganov.lib.mg2d;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.mulganov.lib.mg2d.control.ControlManager;
import com.mulganov.lib.mg2d.graph.figure.Circle;
import com.mulganov.lib.mg2d.graph.template.Joystick;
import com.mulganov.lib.mg2d.scena.Scena;
import com.mulganov.lib.mg2d.setting.Border;
import com.mulganov.lib.mg2d.camera.Camera;


public class MainActivity extends AppCompatActivity {

    ConstraintLayout mContentView;
    public static MG2D mg2d;

    private Camera camera;
    private Joystick joystick;

    private Scena s1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContentView = findViewById(R.id.main);

        hide();

        init();

    }

    private void init() {
        Assets.init(this);

//        mg2d = MG2D.getMG2D(this);
        mg2d = new MG2D(this);
        mg2d.getSetting().setSize(new Scena.Size(Scena.Size.Format.FORMAT_16X9, this));
        Border border = mg2d.getSetting().getBorder();
        border.setStatus(false);

        mg2d.getSetting().setBackground(Color.argb(255, 243, 192, 128));

        camera = Camera.getCamera(true);

        createS1();

        mg2d.setScena(s1);

        addContentView(mg2d.getView(), mContentView.getLayoutParams());
    }

    private void createS1() {
        s1= new Scena(this, mg2d);

        Scena.Size size = s1.getSize();

        Circle circle = new Circle("Joystick_circle", size.getW()/2, size.getH() - size.getH()/2, size.getH()/10, new Paint());
        joystick = new Joystick("Joystick", circle);
        joystick.setDrawB(false);
        joystick.setActivityRange(Joystick.ACTIVITY_RANGE_MAX);

        joystick.setJoystickEvent(new Joystick.JoystickEvent() {
            @Override
            public void Event(float x, float y, long delta) {
//                System.out.println(x + " " + y);
                camera.setRx(x);
                camera.setRy(y);
            }
        });

        MG2D.getMG2D().getSetting().getControlManager().getRegister().addClickDownWindow(new ControlManager.ClickEvent() {
            @Override
            public void Event(ControlManager.ClickEvent.Info clickInfo) {
                if ( clickInfo.getX() >= MG2D.getMG2D().getSetting().getSize().getX() + MG2D.getMG2D().getSetting().getSize().getW()/2 ) return;
                joystick.setDrawB(true);
                joystick.setX((int) clickInfo.getX());
                joystick.setY((int) clickInfo.getY());

                camera.setRx(0);
                camera.setRy(0);
            }
        });

        MG2D.getMG2D().getSetting().getControlManager().getRegister().addClickUpWindow(new ControlManager.ClickEvent() {
            @Override
            public void Event(ControlManager.ClickEvent.Info clickInfo) {
                joystick.setDrawB(false);

                camera.setRx(0);
                camera.setRy(0);
            }
        });

        s1.add(camera);
        s1.add(joystick);

    }


    public void hide(){
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }
}
