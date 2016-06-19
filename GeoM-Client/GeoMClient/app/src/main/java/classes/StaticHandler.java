package classes;

import android.os.Handler;

/**
 * Created by cryph on 19/06/2016.
 */
public class StaticHandler {
    private static Handler hMapReady;
    private static Handler hCoordThread;

    public static synchronized Handler gethMapReady(){
        return hMapReady;
    }

    public static synchronized void sethMapReady(Handler hMapReady){
        StaticHandler.hMapReady = hMapReady;
    }

    public static synchronized Handler gethCoordThread(){
        return hCoordThread;
    }

    public static synchronized void sethCoordThread(Handler hCoordThread){
        StaticHandler.hCoordThread = hCoordThread;
    }
}