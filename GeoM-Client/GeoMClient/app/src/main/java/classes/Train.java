package classes;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.mattia.geom.R;

public class Train extends PublicTransport implements Parcelable{

    private String PTName;
    private String PTCity;

    public Train(String PTName, String PTCity){
        super("Train", "Include Trennord, Trenitalia e Italo", R.mipmap.ic_material_train_grey);
        setPTName(PTName);
        setPTCity(PTCity);
    }

    public String getPTName() {
        return PTName;
    }

    public void setPTName(String PTName) {
        this.PTName = PTName;
    }

    public String getPTCity() {
        return PTCity;
    }

    public void setPTCity(String PTCity) {
        this.PTCity = PTCity;
    }

    //implementazione Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);//Public Transport
        dest.writeString(PTName);
        dest.writeString(PTCity);
    }

    public static final Parcelable.Creator<Train> CREATOR = new Parcelable.Creator<Train>() {
        public Train createFromParcel(Parcel pc) {
            return new Train(pc);
        }
        public Train[] newArray(int size) {
            return new Train[size];
        }
    };

    public Train(Parcel pc){
        super(pc.readString(), pc.readString(), pc.readInt());
        PTName = pc.readString();
        PTCity = pc.readString();
    }
}
