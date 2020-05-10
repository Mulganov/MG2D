package com.mulganov.lib.mg2d.graph;

import android.graphics.Canvas;

import java.util.ArrayList;

public class GraphGroup {
    private ArrayList<Graph> list = new ArrayList<>();

    public void add(Graph graph){
        list.add(graph);
    }

    public Graph get(int i){
        return list.get(i);
    }

    public ArrayList<Graph> getList(){
        return list;
    }

    public void draw(Canvas canvas){
        for (Graph g: list){
            g.draw(canvas);
        }
    }
}
