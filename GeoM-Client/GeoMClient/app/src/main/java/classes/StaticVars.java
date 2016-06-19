package classes;

import java.util.ArrayList;
import java.util.List;

public class StaticVars {
    private static boolean ricezioneCoord = true;
    private static List<Double> listCoord;

    public static final Object lock = new Object();

    public static synchronized boolean isRicezioneCoord(){
        return ricezioneCoord;
    }

    public static synchronized void setRicezioneCoord(boolean ricezioneCoord){
        StaticVars.ricezioneCoord = ricezioneCoord;
    }

    public static synchronized List<Double> getListCoord(){
        return listCoord;
    }

    public static synchronized void setListCoord(List<Double> listCoord){
        StaticVars.listCoord = listCoord;
    }
}