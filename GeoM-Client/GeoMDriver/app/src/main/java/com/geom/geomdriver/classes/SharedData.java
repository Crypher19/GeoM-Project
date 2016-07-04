package com.geom.geomdriver.classes;

import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cryph on 07/06/2016.
 */
public class SharedData implements Parcelable {

    public List<PublicTransport> pt_list;

    public String username;
    public String password;
    private String response;
    private boolean PTChosen;
    public PublicTransport pt;
    public double coordX;
    public double coordY;
    public boolean refreshOnly;
    public boolean sendCoord;

    public SharedData() {
        this.pt_list = new ArrayList<>();
        this.response = "";
        this.PTChosen = false;
        this.pt = null;
        this.coordX = 0.d;
        this.coordY = 0.d;
        this.refreshOnly = false;
        this.sendCoord = true;
    }

    synchronized public String response() {
        return response;
    }
    synchronized public void response(String response) {
        this.response = response;
    }

    synchronized public boolean isPTChosen() {
        return PTChosen;
    }

    synchronized public void setPTChosen(boolean PTChosen) {
        this.PTChosen = PTChosen;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.pt_list);
        dest.writeString(this.username);
        dest.writeString(this.password);
        dest.writeString(this.response);
        dest.writeByte(this.PTChosen ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.pt, flags);
        dest.writeDouble(this.coordX);
        dest.writeDouble(this.coordY);
        dest.writeByte(this.refreshOnly ? (byte) 1 : (byte) 0);
        dest.writeByte(this.sendCoord ? (byte) 1 : (byte) 0);
    }

    protected SharedData(Parcel in) {
        this.pt_list = in.createTypedArrayList(PublicTransport.CREATOR);
        this.username = in.readString();
        this.password = in.readString();
        this.response = in.readString();
        this.PTChosen = in.readByte() != 0;
        this.pt = in.readParcelable(PublicTransport.class.getClassLoader());
        this.coordX = in.readDouble();
        this.coordY = in.readDouble();
        this.refreshOnly = in.readByte() != 0;
        this.sendCoord = in.readByte() != 0;
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
