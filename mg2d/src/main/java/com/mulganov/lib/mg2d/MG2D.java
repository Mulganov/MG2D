package com.mulganov.lib.mg2d;

import android.content.Context;
import android.view.View;

import com.mulganov.lib.mg2d.scena.Scena;
import com.mulganov.lib.mg2d.setting.Setting;


public class MG2D {
    private mg2dThread thread;
    private mg2dView view;

    private Setting setting = new Setting();

    private static MG2D mg2D;

    public static MG2D getMG2D(Context context){
        if (mg2D == null)
            mg2D = new MG2D(context);

        return mg2D;
    }

    public static MG2D getMG2D(){
        return mg2D;
    }


    public MG2D(Context context){
        mg2D = this;
        view = new mg2dView(context, this);
        thread = view.getThread();
    }

    public View getView(){
        return view;
    }

    public void setScena(Scena scena){
        thread.setScena(scena);
    }

    public Scena getScena() {
        return thread.getScena();
    }

    public Setting getSetting() {
        return setting;
    }

    public long getDelta(){ return thread.getDelta();}

}
