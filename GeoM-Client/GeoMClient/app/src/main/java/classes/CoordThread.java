package classes;

/**
 * Created by cryph on 19/04/2016.
 */
public class CoordThread extends Thread {
    private Connection conn;

    public CoordThread() {
        conn = new Connection("192.168.1.110", 3333);
    }

    public void run() {

    }
}
