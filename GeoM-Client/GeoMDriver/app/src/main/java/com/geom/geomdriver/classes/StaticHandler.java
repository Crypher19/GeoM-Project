package com.geom.geomdriver.classes;

import android.os.Handler;

public class StaticHandler {
    private static Handler handler;
    public static final Object lock = new Object();

    public static synchronized Handler getHandler(){
        return handler;
    }

    public static synchronized void setHandler(Handler handler){
        StaticHandler.handler = handler;
    }
}