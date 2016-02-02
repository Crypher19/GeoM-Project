package com.geomclient;

import android.os.Parcel;
import android.os.Parcelable;

public class Autista implements Parcelable {
	private String name;
	private String surn;
	private String numTel;
	
	public Autista() {
		name = "";
		surn = "";
		numTel = "";
	}
	
	public Autista(String name, String surn, String numTel) {
		this.name = name;
		this.surn = surn;
		this.numTel = numTel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurn() {
		return surn;
	}

	public void setSurn(String surn) {
		this.surn = surn;
	}

	public String getNumTel() {
		return numTel;
	}

	public void setNumTel(String numTel) {
		this.numTel = numTel;
	}

    protected Autista(Parcel in) {
        name = in.readString();
        surn = in.readString();
        numTel = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(surn);
        dest.writeString(numTel);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Autista> CREATOR = new Parcelable.Creator<Autista>() {
        @Override
        public Autista createFromParcel(Parcel in) {
            return new Autista(in);
        }

        @Override
        public Autista[] newArray(int size) {
            return new Autista[size];
        }
    };
}