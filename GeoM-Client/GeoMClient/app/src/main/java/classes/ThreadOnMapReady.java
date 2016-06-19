package classes;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by cryph on 19/06/2016.
 */
public class ThreadOnMapReady extends Thread {
    Activity activity;
    GoogleMap mMap;
    public SharedData sd;

    public ThreadOnMapReady(Activity activity, GoogleMap mMap, SharedData sd) {
        this.activity = activity;
        this.mMap = mMap;
        this.sd = sd;
    }

    public void run() {
        int i = 0;
        do {

            Handler handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    sd = (SharedData) msg.obj; // object of PublicTransport
                }
            };
            StaticHandler.sethMapReady(handler);

            Double latitudine = sd.coordList.get(0);
            Double longitudine = sd.coordList.get(1);

            Log.i("sMESSAGE BEFORE UIGPS", latitudine + " ;w " + longitudine);

            UiGpsThread t = new UiGpsThread(activity.getApplicationContext(), mMap, latitudine, longitudine, i);
            try {
                activity.runOnUiThread(t);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (sd.ricezioneCoord);
    }
}
