package classes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.View;

import com.geom.HomeActivity;
import com.geom.MapsActivity;
import com.geom.R;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.security.acl.LastOwnerException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by cryph on 19/04/2016.
 */
public class CoordThread extends Thread {
    private SharedData sd;
    private View v;
    private PublicTransport pt;
    private Connection conn;

    public CoordThread(SharedData sd, View v, PublicTransport pt) {
        this.sd = sd;
        this.v = v;
        this.pt = pt;
        //conn = new Connection("51.254.127.27", 3333); // instanzio oggetto
        //conn = new Connection("172.22.109.93", 3333); // instanzio oggetto
        conn = new Connection("local.tegamino.net", 3333); // instanzio oggetto
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

                while (sd.ricezioneCoord) {
                    msgRicevuto = conn.readMessage(); // ricevo le coordinate del mezzo
                    Document docCoord = Connection.convertStringToDocument(msgRicevuto);
                    sd.listCoord = conn.readDOMPosizione(docCoord);
                    Log.i("sMESSAGE coordX", sd.listCoord.get(0).toString());
                    Log.i("sMESSAGE coordY", sd.listCoord.get(1).toString());
                }

                if (!sd.ricezioneCoord) {
                    conn.sendMessage(conn.getDOMResponse("STOP")); // invio messaggio di STOP
                }

            } else {
                Log.i("sMESSAGE", "msgResp: " + msgResp);
                // TODO: fixare showAlertDialog (magari spostandolo nell'activity)
                //showAlertDialog(v.getContext().getString(R.string.pt_nondisponibile_title), v.getContext().getString(R.string.pt_nondisponibile_message));
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    private void showAlertDialog(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(),
                R.style.AppCompatAlertDialogStyleLight);
        if(title != null && !title.isEmpty())
            builder.setTitle(Html.fromHtml("<b>" + title + "<b>"));
        if(message != null && !message.isEmpty())
            builder.setMessage(message);

        builder.setPositiveButton(Html.fromHtml("<b>" + v.getContext().getString(R.string.ok_string) + "<b>"), null);
        builder.show();
    }
}
