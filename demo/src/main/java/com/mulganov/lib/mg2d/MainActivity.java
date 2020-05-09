package com.mulganov.lib.mg2d;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.mulganov.job.lib.mg2d.MG2D;
import com.mulganov.job.lib.mg2d.graph.Graph;
import com.mulganov.job.lib.mg2d.graph.image.Image;
import com.mulganov.job.lib.mg2d.graph.image.mg2dMatrix;
import com.mulganov.job.lib.mg2d.scena.Scena;
import com.mulganov.job.lib.mg2d.setting.Border;


public class MainActivity extends AppCompatActivity {

    ConstraintLayout mContentView;
    public static MG2D mg2d;


    private Scena s1;
    private Scena s2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContentView = findViewById(R.id.main);

        hide();

        Assets.init(this);


        mg2d = new MG2D(this);
        mg2d.getSetting().setSize(new Scena.Size(Scena.Size.Format.FORMAT_16X9, this));
        Border border = mg2d.getSetting().getBorder();
        border.setColor(Color.RED);
        border.setStatus(true);

        mg2d.getSetting().setBackground(Color.YELLOW);

        createS1();
        createS2();


        mg2d.setScena(s1);

        addContentView(mg2d.getView(), mContentView.getLayoutParams());

    }

    private void createS1() {
        s1= new Scena(this, mg2d);

        Scena.Size size = s1.getSize();

        final Image image = new Image("auto", Assets.getBitmap("0.png"), new mg2dMatrix(size.getX(), size.getY(), mg2dMatrix.Flag.WIDTH, size.getW()/2));

        image.setClick(new Graph.Click() {
            @Override
            public void onClick(Graph.Click.Event event) {
                if (!image.isClickRecTemplate(event)) return;

                mg2d.setScena(s2);
            }
        });


        s1.add(image);

    }

    private void createS2() {
        s2= new Scena(this, mg2d);

        final Image image = new Image("auto", Assets.getBitmap("1.png"), new mg2dMatrix(1000, 1, 1000));

        image.setClick(new Graph.Click() {
            @Override
            public void onClick(Event event) {
                if (!image.isClickRecTemplate(event)) return;

                mg2d.setScena(s1);
            }
        });

        s2.add(image);
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
