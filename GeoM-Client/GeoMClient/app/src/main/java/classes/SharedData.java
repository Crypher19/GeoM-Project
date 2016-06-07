package classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class SharedData implements Parcelable {

    public boolean check;
    public boolean ricezioneCoord;

    public List<PublicTransport> PTList;
    public List<PublicTransport> busList;
    public List<PublicTransport> trainList;
    public List<PublicTransport> favList;

    public String pt_type;//lista mezzi da visualizzare
    public int offset; // id del prossimo mezzo di trasporto da chiedere

    //variabili per goBack()
    public boolean goToHomeActivity;
    public boolean goToChoosePTActivity;
    public boolean goToFavouritesActivity;

    public SharedData() {
        check = true;
        ricezioneCoord = true;

        PTList = new ArrayList<>();
        busList = new ArrayList<>();
        trainList = new ArrayList<>();
        favList = new ArrayList<>();

        pt_type = null;

        offset = 0;

        goToHomeActivity = false;
        goToChoosePTActivity = false;
        goToFavouritesActivity = false;
    }

    public List<PublicTransport> getListType(String pt_type) {
        if (pt_type.equals(PublicTransport.pt_type_bus)) {//cardview di Bus
            return busList;
        } else if (pt_type.equals(PublicTransport.pt_type_train)) {//cardview di Train
            return trainList;
        }
        return null;
    }

    //rimuovo il preferito dalla lista e dal file xml
    public boolean removeFav(PublicTransport fav){
        if(fav != null) {
            MyFile f = new MyFile();

            if (f.removeFavourite(fav) == 0) {
                favList.remove(fav);//aggiorno la lista dei preferiti
                return true;
            }
        }
        return false;
    }

    //rimuovo tutti i preferiti
    public boolean removeAllFavs(){
        MyFile f = new MyFile();

        if(f.removeAllFavourites() == 0){
            favList = new ArrayList<>();
            return true;
        }
        return false;
    }

    //aggiungo un preferito
    public boolean addFav(PublicTransport fav){
        if(fav != null) {
            MyFile f = new MyFile();

            if (f.addFavourite(fav) == 0) {
                favList.add(fav);//aggiorno la lista dei preferiti
                return true;
            }
        }
        return false;
    }

    //ottengo la lista dei preferiti direttamente dal file
    public List<PublicTransport> getFavsListFromFile(){
        return new MyFile().getFavouritesList();
    }

    //ottengo la lista visualizzata in questo momento
    public List<PublicTransport> getCurrentPTList(){
        if(pt_type != null){
            return getListType(pt_type);
        } else return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.check ? (byte) 1 : (byte) 0);
        dest.writeByte(this.ricezioneCoord ? (byte) 1 : (byte) 0);
        dest.writeTypedList(this.PTList);
        dest.writeTypedList(this.busList);
        dest.writeTypedList(this.trainList);
        dest.writeTypedList(this.favList);
        dest.writeString(this.pt_type);
        dest.writeInt(this.offset);
        dest.writeByte(this.goToHomeActivity ? (byte) 1 : (byte) 0);
        dest.writeByte(this.goToChoosePTActivity ? (byte) 1 : (byte) 0);
        dest.writeByte(this.goToFavouritesActivity ? (byte) 1 : (byte) 0);
    }

    protected SharedData(Parcel in) {
        this.check = in.readByte() != 0;
        this.ricezioneCoord = in.readByte() != 0;
        this.PTList = in.createTypedArrayList(PublicTransport.CREATOR);
        this.busList = in.createTypedArrayList(PublicTransport.CREATOR);
        this.trainList = in.createTypedArrayList(PublicTransport.CREATOR);
        this.favList = in.createTypedArrayList(PublicTransport.CREATOR);
        this.pt_type = in.readString();
        this.offset = in.readInt();
        this.goToHomeActivity = in.readByte() != 0;
        this.goToChoosePTActivity = in.readByte() != 0;
        this.goToFavouritesActivity = in.readByte() != 0;
    }

    public static final Creator<SharedData> CREATOR = new Creator<SharedData>() {
        @Override
        public SharedData createFromParcel(Parcel source) {
            return new SharedData(source);
        }

        @Override
        public SharedData[] newArray(int size) {
            return new SharedData[size];
        }
    };
}