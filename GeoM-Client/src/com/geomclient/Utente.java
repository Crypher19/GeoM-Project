package com.geomclient;

import android.os.Parcel;
import android.os.Parcelable;

//created using http://www.parcelabler.com/

public class Utente implements Parcelable {
	private String user;
	private String pass;
	private String name;
	private String surn;
	private String mail;
	
	public Utente() {
		user = "";
		pass = "";
		name = "";
		surn = "";
		mail = "";
	}
	
	public Utente(String user, String pass, String name, String surn, String mail) {
		this.user = user;
		this.pass = pass;
		this.name = name;
		this.surn = surn;
		this.mail = mail;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
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

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
	
	


    protected Utente(Parcel in) {
        user = in.readString();
        pass = in.readString();
        name = in.readString();
        surn = in.readString();
        mail = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user);
        dest.writeString(pass);
        dest.writeString(name);
        dest.writeString(surn);
        dest.writeString(mail);
    }

    //@SuppressWarnings("unused")
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

