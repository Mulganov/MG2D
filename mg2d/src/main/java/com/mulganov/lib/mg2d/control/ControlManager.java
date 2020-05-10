package com.mulganov.lib.mg2d.control;

import java.util.ArrayList;

public class ControlManager {

    private Register register = new Register();
    private UnRegister unRegister = new UnRegister();
    private Events events = new Events();

    private ArrayList<MoveEvent> onMoveInWindow = new ArrayList<>();
    private ArrayList<ClickEvent> onClickDownInWindow = new ArrayList<>();
    private ArrayList<ClickEvent> onClickUpInWindow = new ArrayList<>();

    public Register getRegister() {
        return register;
    }

    public void setRegister(Register register) {
        this.register = register;
    }

    public UnRegister getUnRegister() {
        return unRegister;
    }

    public void setUnRegister(UnRegister unregister) {
        this.unRegister = unregister;
    }

    public Events getEvents() {
        return events;
    }

    public void setEvents(Events events) {
        this.events = events;
    }

    public class Register{
        public void addMoveWindow(MoveEvent event){
            onMoveInWindow.add(event);
        }
        public void addClickDownWindow(ClickEvent event){
            onClickDownInWindow.add(event);
        }
        public void addClickUpWindow(ClickEvent event){
            onClickUpInWindow.add(event);
        }
    }

    public class UnRegister{
        public void removeMoveWindow(MoveEvent event){
            onMoveInWindow.remove(event);
        }
        public void removeClickDownWindow(ClickEvent event){
            onClickDownInWindow.remove(event);
        }
        public void removeClickUpWindow(ClickEvent event){
            onClickUpInWindow.remove(event);
        }
    }

    public class Events{
        public ArrayList<MoveEvent> getMoveWindow(){
            return onMoveInWindow;
        }
        public ArrayList<ClickEvent> getClickDownWindow(){
            return onClickDownInWindow;
        }
        public ArrayList<ClickEvent> getClickUpWindow(){
            return onClickUpInWindow;
        }
    }

    public interface ClickEvent{
        void Event(Info info);

        class Info{
            private float x;
            private float y;
            private long delta;

            public Info(float x, float y, long delta){
                this.x = x;
                this.y = y;
                this.delta = delta;
            }

            public float getX() {
                return x;
            }

            public void setX(float x) {
                this.x = x;
            }

            public long getDelta() {
                return delta;
            }

            public void setDelta(long delta) {
                this.delta = delta;
            }

            public float getY() {
                return y;
            }

            public void setY(float y) {
                this.y = y;
            }
        }
    }

    public interface MoveEvent{
        void Event(ClickEvent.Info clickInfo, Info moveInfo);

        class Info{
            private float x;
            private float y;
            private long delta;

            public Info(float x, float y, long delta){
                this.x = x;
                this.y = y;
                this.delta = delta;
            }


            public float getX() {
                return x;
            }

            public void setX(float x) {
                this.x = x;
            }

            public float getY() {
                return y;
            }

            public void setY(float y) {
                this.y = y;
            }

            public long getDelta() {
                return delta;
            }

            public void setDelta(long delta) {
                this.delta = delta;
            }
        }
    }

}
