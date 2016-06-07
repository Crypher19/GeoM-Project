package classes;

import android.util.Log;

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
    private PublicTransport pt;
    private Connection conn;

    public CoordThread(SharedData sd, PublicTransport pt) {
        this.sd = sd;
        this.pt = pt;
        //conn = new Connection("51.254.127.27", 3333); // instanzio oggetto
        //conn = new Connection("172.22.109.93", 3333); // instanzio oggetto
        conn = new Connection("192.168.1.110", 3333); // instanzio oggetto
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

            while (sd.ricezioneCoord) {
                msgRicevuto = conn.readMessage(); // ricevo se il mezzo è attivo o no
                Document docResp = Connection.convertStringToDocument(msgRicevuto);
                String msgResp = conn.readDOMResponse(docResp, "messaggio");
                Log.i("sMESSAGE RESP", msgResp);

                if (msgResp.equals("Mezzo di trasporto non attivo")) {

                } else {
                    msgRicevuto = conn.readMessage(); // ricevo le coordinate del mezzo
                    Document docCoord = Connection.convertStringToDocument(msgRicevuto);
                    List<Double> listCoord = conn.readDOMofCoord(docCoord);
                    Log.i("sMESSAGE coordX", listCoord.get(0).toString());
                    Log.i("sMESSAGE coordY", listCoord.get(1).toString());
                }
            }
            if (!sd.ricezioneCoord) {
                conn.sendMessage(conn.getDOMResponse("STOP")); // invio messaggio di STOP
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }
}
