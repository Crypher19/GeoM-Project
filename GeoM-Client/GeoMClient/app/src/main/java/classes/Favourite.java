package classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.String;

public class Favourite implements Parcelable{
    private String pt_type;
    private String pt_name;
    private String pt_city;
    private int pt_image_id;

    public Favourite(String pt_type, String pt_name, String pt_city, int pt_image_id){
        setPt_type(pt_type);
        setPt_name(pt_name);
        setPt_city(pt_city);
        setPt_image_id(pt_image_id);
    }

    public Favourite(Bus b){
        setPt_type(b.getPTType());
        setPt_name(b.getPTName());
        setPt_city(b.getPTCity());
        setPt_image_id(b.getPTPhotoID());
    }

    public Favourite(Train t){
        setPt_type(t.getPTType());
        setPt_name(t.getPTName());
        setPt_city(t.getPTCity());
        setPt_image_id(t.getPTPhotoID());
    }

    public String getPt_type() {
        return pt_type;
    }

    public void setPt_type(String pt_type) {
        this.pt_type = pt_type;
    }

    public String getPt_name() {
        return pt_name;
    }

    public void setPt_name(String pt_name) {
        this.pt_name = pt_name;
    }

    public String getPt_city() {
        return pt_city;
    }

    public void setPt_city(String pt_city) {
        this.pt_city = pt_city;
    }

    public int getPt_image_id() {
        return pt_image_id;
    }

    public void setPt_image_id(int pt_image_id) {
        this.pt_image_id = pt_image_id;
    }

    //implementazione Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pt_type);
        dest.writeString(pt_name);
        dest.writeString(pt_city);
        dest.writeInt(pt_image_id);
    }

    public static final Parcelable.Creator<Favourite> CREATOR = new Parcelable.Creator<Favourite>() {
        public Favourite createFromParcel(Parcel pc) {
            return new Favourite(pc);
        }
        public Favourite[] newArray(int size) {
            return new Favourite[size];
        }
    };

    public Favourite(Parcel pc){
        pt_type = pc.readString();
        pt_name = pc.readString();
        pt_city = pc.readString();
        pt_image_id = pc.readInt();
    }

    //override equals
    public boolean equals(Favourite f){
        return this.pt_type.equals(f.getPt_type()) &&
                this.pt_name.equals(f.getPt_name()) &&
                this.pt_city.equals(f.getPt_city()) &&
                this.pt_image_id == f.getPt_image_id();
    }
}

