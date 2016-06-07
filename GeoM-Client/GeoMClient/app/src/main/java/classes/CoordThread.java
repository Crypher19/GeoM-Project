package classes;

import android.util.Log;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.security.acl.LastOwnerException;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by cryph on 19/04/2016.
 */
public class CoordThread extends Thread {
    private PublicTransport pt;
    private Connection conn;

    public CoordThread(PublicTransport pt) {
        this.pt = pt;
        //conn = new Connection("51.254.127.27", 3333); // instanzio oggetto
        conn = new Connection("127.0.0.1", 3333); // instanzio oggetto
    }

    @Override
    public void run() {
        String msgRicevuto = null;

        conn.startConn(); // connessione con il server

        try {
            conn.sendMessage(conn.getDOMType("user")); // invio il tipo di utente
            msgRicevuto = conn.readMessage(); // ricevo "Connected"

            conn.sendMessage(pt.getDOMPT()); // invio mezzo desiderato (per le coordinate)
            msgRicevuto = conn.readMessage(); // ricevo se il mezzo è attivo o no

            Document docResp = Connection.convertStringToDocument(msgRicevuto);
            String msgResp = conn.readDOMResponse(docResp, "messaggio");
            Log.i("sMESSAGE RESP", msgResp);

            if (msgResp == "Mezzo di trasporto non attivo") {

            } else {

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
