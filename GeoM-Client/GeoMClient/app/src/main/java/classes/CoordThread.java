package classes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.geom.HomeActivity;
import com.geom.MapsActivity;
import com.geom.R;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.security.acl.LastOwnerException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import classes.layout_classes.PublicTransportListAdapter;

/**
 * Created by cryph on 19/04/2016.
 */
public class CoordThread extends Thread {
    private SharedData sd;
    private View v;
    private PublicTransport pt;
    private Connection conn;
    public static boolean ricezioneCoord = true;
    //public static Handler mHandler;

    public CoordThread(SharedData sd, View v, PublicTransport pt) {
        this.sd = sd;
        this.v = v;
        this.pt = pt;
        //conn = new Connection("51.254.127.27", 3333); // instanzio oggetto
        this.conn = new Connection("51.254.127.27", 3333); // instanzio oggetto
    }

    @Override
    public void run() {
        String msgRicevuto = null;


        conn.startConn(); // connessione con il server

        try {
            conn.sendMessage(conn.getDOMType("user")); // invio il tipo di utente
            msgRicevuto = conn.readMessage(); // ricevo "Connected"
            Log.i("sMESSAGE RESP", msgRicevuto);
            conn.sendMessage(pt.getDOMPT()); // invio mezzo desiderato (per le coordinate)

            msgRicevuto = conn.readMessage(); // ricevo se il mezzo è attivo o no
            Document docResp = Connection.convertStringToDocument(msgRicevuto);
            String msgResp = conn.readDOMResponse(docResp, "messaggio");
            Log.i("sMESSAGE RESP", msgResp);

            if (msgResp.equals("OK")){

                //lancio MapActivity
                Intent i = new Intent(v.getContext(), MapsActivity.class);
                Bundle b = new Bundle();

                sd.goToChoosePTActivity = true;//devo tornare a ChoosePTActivity
                b.putParcelable("PublicTransport", pt);
                b.putParcelable("SharedData", sd);
                i.putExtra("bundle", b);
                ((Activity) v.getRootView().getContext()).setResult(Activity.RESULT_OK);
                ((Activity) v.getRootView().getContext()).startActivityForResult(i, 4);

                Log.i("sMESSAGE ricCoord bef", Boolean.toString(CoordThread.ricezioneCoord));

                while (CoordThread.ricezioneCoord) {
                   /* mHandler = new Handler(){
                        @Override
                        public void handleMessage(Message msg) {
                            sd = (SharedData) msg.obj; // object of PublicTransport
                        }
                    };*/

                    msgRicevuto = conn.readMessage(); // ricevo le coordinate del mezzo
                    Document docCoord = Connection.convertStringToDocument(msgRicevuto);
                    sd.coordList = conn.readDOMPosizione(docCoord);
                    Log.i("sMESSAGE coordX", sd.coordList.get(0).toString());
                    Log.i("sMESSAGE coordY", sd.coordList.get(1).toString());
                    Message msg = new Message();
                    msg.obj = sd;
                    ThreadOnMapReady.mHandler.sendMessage(msg); // refresh SharedData in ThreadOnMapReady
                }
                Log.i("sMESSAGE ricCoord after", Boolean.toString(CoordThread.ricezioneCoord));

                if (!CoordThread.ricezioneCoord) {
                    Log.i("sMESSAGE", "msgResp: " + msgResp);
                    conn.sendMessage(conn.getDOMResponse("STOP")); // invio messaggio di STOP
                }
            } else {

                Log.i("sMESSAGE", "msgResp: " + msgResp);
                showAlertDialog(v.getContext().getString(R.string.pt_nondisponibile_title), v.getContext().getString(R.string.pt_nondisponibile_message));
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    private void showAlertDialog(final String title, final String message){
        Handler mHandler = new Handler(Looper.getMainLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(),
                        R.style.AppCompatAlertDialogStyleLight);
                if(title != null && !title.isEmpty())
                    builder.setTitle(Html.fromHtml("<b>" + title + "<b>"));
                if(message != null && !message.isEmpty())
                    builder.setMessage(message);

                builder.setPositiveButton(Html.fromHtml(
                        "<b>" + v.getContext().getString(R.string.ok_string) + "<b>"), null);
                builder.show();
            }
        });
    }
}