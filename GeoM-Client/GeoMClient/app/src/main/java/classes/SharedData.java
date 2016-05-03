package classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class SharedData implements Parcelable {

    public List<PublicTransport> PTList;
    public List<PublicTransport> busList;
    public List<PublicTransport> trainList;
    public List<PublicTransport> favList;
    public boolean firstTimeQueryBus;
    public boolean firstTimeQueryTrain;
    public String pt_type;//lista mezzi da visualizzare
    public int offset; // id del prossimo mezzo di trasporto da chiedere

    public SharedData() {
        PTList = new ArrayList<>();
        busList = new ArrayList<>();
        trainList = new ArrayList<>();
        favList = new ArrayList<>();
        firstTimeQueryBus = true;
        firstTimeQueryTrain = true;
        pt_type = null;
        offset = 0;
    }

    public List<PublicTransport> getListType(String pt_type) {
        if (pt_type.equals(PublicTransport.pt_type_bus)) {//cardview di Bus
            return busList;
        } else if (pt_type.equals(PublicTransport.pt_type_train)) {//cardview di Train
            return trainList;
        }
        return null;
    }

    public void clearList(String pt_type) {
        if (pt_type.equals(PublicTransport.pt_type_bus)) {//cardview di Bus
            busList.clear();
        } else if (pt_type.equals(PublicTransport.pt_type_train)) {//cardview di Train
            trainList.clear();
        }
    }

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
        dest.writeByte(firstTimeQueryBus ? (byte) 1 : (byte) 0);
        dest.writeByte(firstTimeQueryTrain ? (byte) 1 : (byte) 0);
        dest.writeString(this.pt_type);
        dest.writeInt(this.offset);
    }

    protected SharedData(Parcel in) {
        this.PTList = in.createTypedArrayList(PublicTransport.CREATOR);
        this.busList = in.createTypedArrayList(PublicTransport.CREATOR);
        this.trainList = in.createTypedArrayList(PublicTransport.CREATOR);
        this.favList = in.createTypedArrayList(PublicTransport.CREATOR);
        this.firstTimeQueryBus = in.readByte() != 0;
        this.firstTimeQueryTrain = in.readByte() != 0;
        this.pt_type = in.readString();
        this.offset = in.readInt();
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