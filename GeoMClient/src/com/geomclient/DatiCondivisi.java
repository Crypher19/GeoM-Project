package com.geomclient;

import android.os.Parcel;
import android.os.Parcelable;

// created using http://www.parcelabler.com/

public class DatiCondivisi implements Parcelable {
	private Autista utente;
	private String serverAddress;
	private int serverPort;
	private boolean autenticato;
	private String scelta;

	
	public DatiCondivisi() {
		utente = new Autista();
		serverAddress = "172.22.108.108";
		serverPort = 4444;
		autenticato = false;
	}
	
	public DatiCondivisi(String sa, int sp) {
		utente = new Autista();
		serverAddress = sa;
		serverPort = sp;
		autenticato = false;
	}

	public String getServerAddress() {
		return serverAddress;
	}

	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public Autista getUtente() {
		return utente;
	}

	public void setUtente(Autista utente) {
		this.utente = utente;
	}

	public boolean isAutenticato() {
		return autenticato;
	}

	public void setAutenticato(boolean autenticato) {
		this.autenticato = autenticato;
	}

	public String getScelta() {
		return scelta;
	}

	public void setScelta(String scelta) {
		this.scelta = scelta;
	}


   protected DatiCondivisi(Parcel in) {
       utente = (Autista) in.readValue(Autista.class.getClassLoader());
       serverAddress = in.readString();
       serverPort = in.readInt();
       autenticato = in.readByte() != 0x00;
       scelta = in.readString();
   }

   @Override
   public int describeContents() {
       return 0;
   }

   @Override
   public void writeToParcel(Parcel dest, int flags) {
       dest.writeValue(utente);
       dest.writeString(serverAddress);
       dest.writeInt(serverPort);
       dest.writeByte((byte) (autenticato ? 0x01 : 0x00));
       dest.writeString(scelta);
   }

   //@SuppressWarnings("unused")
   public static final Parcelable.Creator<DatiCondivisi> CREATOR = new Parcelable.Creator<DatiCondivisi>() {
       @Override
       public DatiCondivisi createFromParcel(Parcel in) {
           return new DatiCondivisi(in);
       }

       @Override
       public DatiCondivisi[] newArray(int size) {
           return new DatiCondivisi[size];
       }
   };
}

