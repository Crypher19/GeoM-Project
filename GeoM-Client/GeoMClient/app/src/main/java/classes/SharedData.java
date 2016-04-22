package classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class SharedData implements Parcelable{

    public List<PublicTransport> PTList;
    public List<PublicTransport> busList;
    public List<PublicTransport> trainList;
    public List<PublicTransport> favList;

    public SharedData(){
        PTList = new ArrayList<>();
        busList = new ArrayList<>();
        trainList = new ArrayList<>();
        favList = new ArrayList<>();
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
        pc.readTypedList(busList, PublicTransport.CREATOR);
        pc.readTypedList(trainList, PublicTransport.CREATOR);
        pc.readTypedList(favList, PublicTransport.CREATOR);
    }

    public List<PublicTransport> getListType(String pt_type){
        if(pt_type.equals(PublicTransport.pt_type_bus)){//cardview di Bus
            return busList;
        } else if(pt_type.equals(PublicTransport.pt_type_train)){//cardview di Train
            return trainList;
        }
        return null;
    }
}
