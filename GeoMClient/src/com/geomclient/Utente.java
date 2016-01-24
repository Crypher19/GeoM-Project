package com.geomclient;

import android.os.Parcel;
import android.os.Parcelable;

public class Utente implements Parcelable {
	private String name;
	private String surn;
	private String numTel;
	
	public Utente() {
		name = "";
		surn = "";
		numTel = "";
	}
	
	public Utente(String name, String surn, String numTel) {
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

    protected Utente(Parcel in) {
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
    public static final Parcelable.Creator<Utente> CREATOR = new Parcelable.Creator<Utente>() {
        @Override
        public Utente createFromParcel(Parcel in) {
            return new Utente(in);
        }

        @Override
        public Utente[] newArray(int size) {
            return new Utente[size];
        }
    };
}