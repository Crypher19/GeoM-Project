package classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class SharedData implements Parcelable{

    private List<PublicTransport> PTList;
    private List<Bus> busList;
    private List<Train> trainList;
    private List<Favourite> favList;

    public SharedData(){
        PTList = new ArrayList<>();
        busList = new ArrayList<>();
        trainList = new ArrayList<>();
        favList = new ArrayList<>();
    }

    public List<PublicTransport> getPTList() {
        return this.PTList;
    }

    public void setPTList(List<PublicTransport> PTList) {
        this.PTList = PTList;
    }

    public List<Bus> getBusList() {
        return busList;
    }

    public void setBusList(List<Bus> busList) {
        this.busList = busList;
    }

    public List<Train> getTrainList() {
        return trainList;
    }

    public void setTrainList(List<Train> trainList) {
        this.trainList = trainList;
    }

    public List<Favourite> getFavList() {
        return favList;
    }

    public void setFavList(List<Favourite> favList) {
        this.favList = favList;
    }

    public PublicTransport getPTInPos(int i){
        return this.PTList.get(i);
    }

    public Bus getBusInPos(int i){
        return this.busList.get(i);
    }

    public Train getTrainInPos(int i){
        return this.trainList.get(i);
    }

    public Favourite getFavouriteInPos(int i){
        return this.favList.get(i);
    }

    public void addItemInPTList(PublicTransport p){
        this.PTList.add(p);
    }

    public void addItemInTrainList(Train t){
        this.trainList.add(t);
    }

    public void addItemInBusList(Bus b){
        this.busList.add(b);
    }

    public void addItemInFavouritesList(Favourite f){
        this.favList.add(f);
    }

    //implementazione Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(PTList);
        dest.writeTypedList(busList);
        dest.writeTypedList(trainList);
        dest.writeTypedList(favList);
    }

    public static final Parcelable.Creator<SharedData> CREATOR = new Parcelable.Creator<SharedData>() {
        public SharedData createFromParcel(Parcel pc) {
            return new SharedData(pc);
        }
        public SharedData[] newArray(int size) {
            return new SharedData[size];
        }
    };

    public SharedData(Parcel pc){
        this();//costruttore

        pc.readTypedList(PTList, PublicTransport.CREATOR);
        pc.readTypedList(busList, Bus.CREATOR);
        pc.readTypedList(trainList, Train.CREATOR);
        pc.readTypedList(favList, Favourite.CREATOR);
    }
}
