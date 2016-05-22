package classes;

/**
 * Created by cryph on 19/04/2016.
 */
public class CoordThread extends Thread {
    private Connection conn;

    public CoordThread() {
        conn = new Connection("51.254.127.27", 3333);
    }

    @Override
    public void run() {

    }
}
