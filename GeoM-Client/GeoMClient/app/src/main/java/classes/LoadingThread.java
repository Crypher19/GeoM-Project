package classes;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by cryph on 19/04/2016.
 */
public class LoadingThread extends Thread {
    private SharedData sd;
    private Connection conn;

    public LoadingThread(SharedData sd) {
        this.sd = sd;
        conn = new Connection("192.168.1.110", 3333); // instanzio oggetto
    }

    @Override
    public void run() {
        Log.e("THREAD", "Prima della connessione");
        String msgRicevuto = null;

        try {
            Socket s = new Socket("192.168.1.110", 3333);
            OutputStream out = s.getOutputStream();
            PrintWriter sOUT = new PrintWriter(out);

            // connessioni input del socket
            InputStreamReader in = new InputStreamReader(s.getInputStream());
            BufferedReader sIN = new BufferedReader(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //conn.startConn(); // connessione con il server
        Log.e("THREAD", "Dopo la connessione");
       /* try {
            conn.readMessage();
            conn.closeConn();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        /*try {
            msgRicevuto = conn.readMessage(); // ricevo "Connected"
            conn.sendMessage(conn.getDOMType("user")); // invio il tipo di utente
            msgRicevuto = conn.readMessage(); // ricevo
            System.out.println("Risposta: " + msgRicevuto);

            msgRicevuto = conn.readMessage(); // ricevo la lista dei trasporti
            Document listaPT = Connection.convertStringToDocument(msgRicevuto);
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
