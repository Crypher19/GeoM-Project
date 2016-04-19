package classes;

/**
 * Created by cryph on 19/04/2016.
 */
public class ThreadSocket extends Thread {
    private Connessione conn;

    public ThreadSocket() {
        conn = new Connessione("127.0.0.1", 3333); // instanzio oggetto
    }

    public void run() {
        conn.startConn(); // connessione con il server
    }
}
