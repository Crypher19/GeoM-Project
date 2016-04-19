package classes;

/**
 * Created by cryph on 19/04/2016.
 */
public class CoordThread extends Thread {
    private Connessione conn;

    public CoordThread() {
        conn = new Connessione("127.0.0.1", 3333);
    }

    public void run() {

    }
}
