package classes;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.mattia.geom.R;

public class Bus extends PublicTransport implements Parcelable{

    private String PTName;
    private String PTCity;

    public Bus(String PTName, String PTCity){
        super("Bus", "Include ASF, Urbani e Internurbani", R.mipmap.ic_material_bus_grey);
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

    public static final Parcelable.Creator<Bus> CREATOR = new Parcelable.Creator<Bus>() {
        public Bus createFromParcel(Parcel pc) {
            return new Bus(pc);
        }
        public Bus[] newArray(int size) {
            return new Bus[size];
        }
    };

    public Bus(Parcel pc){
        super(pc.readString(), pc.readString(), pc.readInt());
        PTName = pc.readString();
        PTCity = pc.readString();
    }
}