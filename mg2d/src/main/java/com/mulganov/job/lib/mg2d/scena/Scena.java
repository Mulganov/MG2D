package com.mulganov.job.lib.mg2d.scena;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.view.Display;
import android.widget.Toast;

import com.mulganov.job.lib.mg2d.MG2D;
import com.mulganov.job.lib.mg2d.graph.Graph;

import java.util.ArrayList;
import java.util.HashMap;

public class Scena {
    private HashMap<String, Graph> map = new HashMap<>();

    private Context context;
    private Size size;

    private int keyInt;

//    public Scena(Context context, Size size){
//        this.context = context;
//        this.size = size;
//    }

    public Scena(Context context, MG2D mg2D){
        this.context = context;
        if (mg2D.getSetting().getSize() == null){
            System.out.println("[ERROR] -> [Scena] -> [constucter] -> mg2D.getSetting().getSize() == null" );
            Toast.makeText(context, "[ERROR] -> [Scena] -> [constucter] -> mg2D.getSetting().getSize() == null " , Toast.LENGTH_LONG).show();
        }
        this.size = mg2D.getSetting().getSize();
    }

    public void add(Graph graph){
        if (graph.getKey().equalsIgnoreCase("auto")){
            graph.setKey(keyInt + "");
            keyInt++;
        }
        if (map.containsKey(graph.getKey())){
            System.out.println("[ERROR] -> [Scena] -> [add] -> графика с таким ключов уже существует -> key: " + graph.getKey());
            Toast.makeText(context, "[ERROR] -> [Scena] -> [add] -> графика с таким ключов уже существует -> key: " + graph.getKey(), Toast.LENGTH_LONG).show();
        }
        graph.setIndex(map.size());
        map.put(graph.getKey(), graph);
    }

    public void delete(Graph graph){
        map.remove(graph.getKey());
    }


    public Graph get(int index){
        for (String key: map.keySet()){
            Graph graph = map.get(key);
            if (graph.getIndex() == index){
                return graph;
            }
        }
        return null;
    }

    public Graph get(String key){
        return map.get(key);
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public static class Size{

        private Format format;
        private int w;
        private int h;
        private int x;
        private int y;
        private int originalW;
        private int originalH;

        public Size(Format format, Activity activity){
            this.format = format;
            Display display = activity.getWindowManager().getDefaultDisplay();
            originalH = display.getHeight();
            originalW = display.getWidth();


            if (format.equals(Format.FORMAT_9X16)){
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                final float wx = originalW;
                final float wy = originalH;

                float bx = 1080;
                float by = 1920;

                final float nx, ny;

                bx /= 9;
                by /= 16;

                nx = (int)( wx / bx);

                ny = 16 * nx / 9 ;

                w =  (int)(nx * bx);
                h =  (int)(ny * by);

                x = (int) ((wx - w)/2);
                y = (int) ((wy - h)/2);

            }else
            if (format.equals(Format.FORMAT_16X9)){
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

                final float wx = originalW;
                final float wy = originalH;

                float bx = 1920;
                float by = 1080;

                float nx, ny;

                bx /= 16;
                by /= 9;

                ny = (int)( wy / by);

                nx = 16 * ny / 9;

                w =  (int)(nx * bx);
                h =  (int)(ny * by);

                x = (int) ((wx - w)/2);
                y = (int) ((wy - h)/2);
            }

            System.out.println();

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

        public int getOriginalH() {
            return originalH;
        }

        public void setOriginalH(int originalH) {
            this.originalH = originalH;
        }

        public int getOriginalW() {
            return originalW;
        }

        public void setOriginalW(int originalW) {
            this.originalW = originalW;
        }

        public static class Format{
            private String value;

            public static final Format FORMAT_16X9 = new Format("FORMAT_16X9");
            public static final Format FORMAT_9X16 = new Format("FORMAT_9X16");

            public static final Format FORMAT_ORIGINAL = new Format("FORMAT_ORIGINAL");

            public String getValue(){
                return value;
            }

            private Format(String format){
                value = format;
            }

            public boolean equals(Format format){
                if (value.equalsIgnoreCase(format.getValue()))
                    return true;
                else
                    return false;
            }
        }
    }
}
