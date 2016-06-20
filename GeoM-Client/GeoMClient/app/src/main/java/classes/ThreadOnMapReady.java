package classes;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by cryph on 19/06/2016.
 */
public class ThreadOnMapReady extends Thread {
    public static Handler mHandler;
    public Activity activity;
    public GoogleMap mMap;
    public SharedData sd;
    public String checkMyPT;

    public ThreadOnMapReady(Activity activity, GoogleMap mMap, SharedData sd) {
        this.activity = activity;
        this.mMap = mMap;
        this.sd = sd;
        this.checkMyPT = "true";
    }

    public void run() {

        while (CoordThread.ricezioneCoord) {
            Looper.prepare();
            mHandler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    if (msg.obj instanceof SharedData) {
                        sd = (SharedData) msg.obj; // object of PublicTransport

                        Double latitudine = sd.coordList.get(0);
                        Double longitudine = sd.coordList.get(1);

                        Log.i("sMESSAGE BEFORE UIGPS", latitudine + " ; " + longitudine);

                        UiGpsThread t = new UiGpsThread(activity.getApplicationContext(), mMap, latitudine, longitudine, 1, checkMyPT);
                        try {
                            activity.runOnUiThread(t);
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    else if (msg.obj instanceof String) {
                        checkMyPT = (String) msg.obj; // object of PublicTransport

                        Double latitudine = sd.coordList.get(0);
                        Double longitudine = sd.coordList.get(1);

                        Log.i("sMESSAGE UIGPS", latitudine + " ; " + longitudine + " ;;;;;; " + checkMyPT);

                        UiGpsThread t = new UiGpsThread(activity.getApplicationContext(), mMap, latitudine, longitudine, 1, checkMyPT);
                        try {
                            activity.runOnUiThread(t);
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

            };
            Looper.loop();
        }
    }
}
