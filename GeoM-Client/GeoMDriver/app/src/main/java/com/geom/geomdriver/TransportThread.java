package com.geom.geomdriver;

import android.util.Log;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by cryph on 07/06/2016.
 */
public class TransportThread extends Thread {
    private SharedData sd;
    private Connection conn;

    public TransportThread(SharedData sd) {
        this.sd = sd;
        //this.conn = new Connection("51.254.127.27", 3333); // instanzio oggetto
        //this.conn = new Connection("172.22.109.93", 3333); // instanzio oggetto
        this.conn = new Connection("192.168.1.110", 3333); // instanzio oggetto
    }

    @Override
    public void run() {
        String msgRicevuto = null;

        conn.startConn(); // connessione con il server

        try {
            conn.sendMessage(conn.getDOMType("transport")); // invio il tipo di utente
            msgRicevuto = conn.readMessage(); // ricevo "Connected"
            Log.i("sMESSAGE RECEIVED", msgRicevuto);

            // invio username e password
            conn.sendMessage(conn.getDOMAutenticazione(sd.username, sd.password));
            msgRicevuto = conn.readMessage(); // ricevo la conferma del login
            Document docResp = Connection.convertStringToDocument(msgRicevuto);
            String msgResp = conn.readDOMResponse(docResp, "messaggio");
            Log.i("sMESSAGE RESP", msgResp);

            if (msgResp.equals("Username o password errata")) {

            } else {


                // TODO: Ottenere la lista dei mezzi di trasporto della sola compagnia di cui faccio parte
                // TODO: Da modificare anche la query server (magari aggiungendo qualche parametro)

                /*
                conn.sendMessage(conn.getDOMRichiesta("30", Integer.toString(sd.offset))); // invio il tipo di mezzo da caricare
                Log.i("sMESSAGE SENT", Connection.convertDocumentToString(conn.getDOMRichiesta("30", Integer.toString(sd.offset))));

                msgRicevuto = conn.readMessage(); // ricevo la lista dei trasporti
                Document listaPT = Connection.convertStringToDocument(msgRicevuto);
                Log.i("sMESSAGE RECEIVED", msgRicevuto);
                */
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
}
