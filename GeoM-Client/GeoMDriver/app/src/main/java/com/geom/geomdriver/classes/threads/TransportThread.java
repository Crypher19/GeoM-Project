package com.geom.geomdriver.classes.threads;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;

import android.os.Handler;

import com.geom.geomdriver.ChoosePTActivity;
import com.geom.geomdriver.CoordActivity;
import com.geom.geomdriver.HomeActivity;
import com.geom.geomdriver.classes.Connection;
import com.geom.geomdriver.classes.PublicTransport;
import com.geom.geomdriver.classes.SharedData;
import com.geom.geomdriver.classes.StaticHandler;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by cryph on 07/06/2016.
 */
public class TransportThread extends Thread {
    private SharedData sd;
    private View v;
    private Connection conn;
    public Handler handler;

    public TransportThread(SharedData sd, View v, Handler handler) {
        this.sd = sd;
        this.v = v;
        //this.conn = new Connection("51.254.127.27", 3333); // instanzio oggetto
        //this.conn = new Connection("172.22.109.93", 3333); // instanzio oggetto
        this.conn = new Connection("192.168.1.110", 3333); // instanzio oggetto
        this.handler = handler;
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

            Message msg = new Message();
            msg.obj = msgResp;
            handler.sendMessage(msg);

            if ("OK".equals(msgResp)) {
                // ricevo la lista dei mezzi
                msgRicevuto = conn.readMessage();
                Document listaPT = Connection.convertStringToDocument(msgRicevuto);
                Log.i("sMESSAGE RECEIVED", msgRicevuto);

                // carico la lista in SharedData
                NodeList mezzi = null;
                NodeList chMezzo = null;
                Node mezzo = null;
                String tipo, nome, compagnia, tratta;
                boolean attivo;
                int id;
                // ottengo la lista di tutti gli elementi "mezzo"
                mezzi = listaPT.getElementsByTagName("mezzo");

                // per ogni mezzo
                for (int i=0; i < mezzi.getLength(); i++) {
                    mezzo = mezzi.item(i);
                    Element elMezzo = (Element) mezzo;
                    id = Integer.parseInt(elMezzo.getAttribute("id"));
                    tipo = elMezzo.getElementsByTagName("tipo").item(0).getTextContent(); // ottengo il tipo del mezzo (es. bus o treno)
                    compagnia = elMezzo.getElementsByTagName("compagnia").item(0).getTextContent(); // ottengo la compagnia
                    nome = elMezzo.getElementsByTagName("nome").item(0).getTextContent(); // ottengo il nome del mezzo
                    tratta = elMezzo.getElementsByTagName("tratta").item(0).getTextContent(); // ottengo la tratta del mezzo
                    attivo = Boolean.parseBoolean(elMezzo.getElementsByTagName("attivo").item(0).getTextContent()); // ottengo se il mezzo è attivo

                    sd.pt_list.add(new PublicTransport(id, tipo, nome, compagnia, tratta, attivo));
                    Log.i("sMESSAGE FUNZIONA", "tipo = " + tipo);
                }

                Log.i("sMESSAGE FUNZIONA", "FINEFOR");


                // START ChoosePTActivity
                Intent intent = new Intent(v.getContext(), ChoosePTActivity.class);
                Bundle b = new Bundle();

                b.putParcelable("SharedData", sd);
                intent.putExtra("bundle", b);
                v.getRootView().getContext().startActivity(intent);
                ((Activity) v.getRootView().getContext()).finish();

                synchronized (StaticHandler.lock) {
                    while (!sd.isPTChosen()) {
                        try {
                            Log.i("sMESSAGE SYNC", "PTChosen = " + sd.isPTChosen());
                            StaticHandler.lock.wait();
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                }

                Log.i("sMESSAGE FUNZIONA", "DOPO LA WAIT");
                conn.sendMessage(sd.pt.getDOMPT()); // invio mezzo desiderato

                msgRicevuto = conn.readMessage(); // ricevo la conferma del login
                docResp = Connection.convertStringToDocument(msgRicevuto);
                msgResp = conn.readDOMResponse(docResp, "messaggio");
                Log.i("sMESSAGE RESP", msgResp);

                // se il server conferma
                if ("OK".equals(msgResp)) {
                    // invio posizione
                    // TODO: cambiare eventualmente la condizione del while
                    while(true) {
                        conn.sendMessage(conn.getDOMPosizione(Double.toString(sd.coordX), Double.toString(sd.coordY)));
                        try {
                            Thread.sleep(1500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                // TODO: alla chiusura dell'app devo inviare messaggio di "END"
                // TODO: controllare funzionamento timeout lato server
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }


    }

    public void setSharedData(SharedData sd) {
        this.sd = sd;
    }
}
