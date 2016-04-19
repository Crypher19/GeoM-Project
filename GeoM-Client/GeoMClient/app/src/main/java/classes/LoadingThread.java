package classes;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by cryph on 19/04/2016.
 */
public class LoadingThread extends Thread {
    private SharedData sd;
    private Connessione conn;

    public LoadingThread(SharedData sd) {
        this.sd = sd;
        conn = new Connessione("87.9.118.203", 3333); // instanzio oggetto
    }

    public void run() {
        String msgRicevuto = null;

        conn.startConn(); // connessione con il server

        /*try {
            msgRicevuto = conn.readMessage(); // ricevo "Connected"
            conn.sendMessage(conn.getDOMType("user")); // invio il tipo di utente
            msgRicevuto = conn.readMessage(); // ricevo
            System.out.println("Risposta: " + msgRicevuto);

            msgRicevuto = conn.readMessage(); // ricevo la lista dei trasporti
            Document listaPT = Connessione.convertStringToDocument(msgRicevuto);
            System.out.println("Risposta: " + msgRicevuto);

            NodeList mezzi = null;
            NodeList chMezzo = null;
            Node mezzo = null;
            String tipo, nome, tratta, attivo;
            int ID, compagnia;
            // ottengo la lista di tutti gli elementi "mezzo"
            mezzi = listaPT.getElementsByTagName("mezzo");


            // per ogni mezzo
            for (int i=0; i < mezzi.getLength(); i++) {
                mezzo = mezzi.item(i);
                chMezzo = mezzo.getChildNodes(); // ottengo la lista di tutti gli elementi figli
                tipo = chMezzo.item(0).getTextContent(); // ottengo il tipo del mezzo (es. pullman o treno)
                if ("pullman".equals(tipo)) {
                    //PublicTransport pt = new PublicTransport(,);
                    //sd.busList.add();
                    Log.i("FUNZIONA", "tipo = " + tipo);
                } else if ("treno".equals(tipo)) {

                } else {
                    Log.e("LoadingThread", "Tipo non corrispondente! tipo = " + tipo);
                }
            }



        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }*/
    }
}
