package com.geom.geomdriver;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class PublicTransport implements Serializable, Parcelable {
    //tipi di default (non final per problemi con Parcelable)
    public static String pt_type_bus = "bus";
    public static String pt_type_train = "treno";
    public static String pt_type_genericPT = "publictransport";
    public static String pt_type_favourite = "favourite";

    private int pt_id;
    private String pt_type; //tipo
    private String pt_name; //nome
    private String pt_company; //compagnia
    private String pt_route; //tratta
    private String pt_info; //informazioni generali (solo per pt generico)
    private boolean pt_enabled; //attivo
    private int pt_image_id; //icona
    private double pt_coordX;
    private double pt_coordY;

    public PublicTransport(int pt_id, String pt_type, String pt_name, String pt_company,
                           String pt_route, boolean pt_enabled, double pt_coordX, double pt_coordY,
                           String pt_info){
        this.pt_id = pt_id;
        this.pt_type = pt_type;
        this.pt_name = pt_name;
        this.pt_company = pt_company;
        this.pt_route = pt_route;
        this.pt_enabled = pt_enabled;
        this.pt_coordX = pt_coordX;
        this.pt_coordY = pt_coordY;
        this.pt_info = pt_info;
    }

    public String getPt_type() {
        return pt_type;
    }

    public void setPt_type(String pt_type) {
        this.pt_type = pt_type;
    }

    public int getPt_image_id() {
        return pt_image_id;
    }

    public void setPt_image_id(int pt_image_id) {
        this.pt_image_id = pt_image_id;
    }

    public String getPt_info() {
        return pt_info;
    }

    public void setPt_info(String pt_info) {
        this.pt_info = pt_info;
    }

    public String getPt_route() {
        return pt_route;
    }

    public void setPt_route(String pt_route) {
        this.pt_route = pt_route;
    }

    public String getPt_name() {
        return pt_name;
    }

    public void setPt_name(String pt_name) {
        this.pt_name = pt_name;
    }

    public int getPt_id() {
        return pt_id;
    }

    public void setPt_id(int pt_id) {
        this.pt_id = pt_id;
    }

    public String getPt_company() {
        return pt_company;
    }

    public void setPt_company(String pt_company) {
        this.pt_company = pt_company;
    }

    public boolean isPt_enabled() {
        return pt_enabled;
    }

    public void setPt_enabled(boolean pt_enabled) {
        this.pt_enabled = pt_enabled;
    }

    public double getPt_coordX() {
        return pt_coordX;
    }

    public void setPt_coordX(double pt_coordX) {
        this.pt_coordX = pt_coordX;
    }

    public double getPt_coordY() {
        return pt_coordY;
    }

    public void setPt_coordY(double pt_coordY) {
        this.pt_coordY = pt_coordY;
    }

    //override equals
    public boolean equals(PublicTransport pt){
        if(pt != null) {
            return (this.pt_id == pt.getPt_id()
                    && this.pt_type.equals(pt.getPt_type())
                    && this.pt_name.equals(pt.getPt_name())
                    && this.pt_company.equals(pt.getPt_company())
                    && this.pt_route.equals(pt.getPt_route())
                    && this.pt_enabled == pt.isPt_enabled()
                    && this.pt_coordX == pt.getPt_coordX()
                    && this.pt_coordY == pt.getPt_coordY()
                    && this.pt_info.equals(pt.getPt_info()));
        }
        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pt_type_bus);
        dest.writeString(pt_type_train);
        dest.writeString(pt_type_genericPT);
        dest.writeString(pt_type_favourite);
        dest.writeInt(this.pt_id);
        dest.writeString(this.pt_type);
        dest.writeString(this.pt_name);
        dest.writeString(this.pt_company);
        dest.writeString(this.pt_route);
        dest.writeString(this.pt_info);
        dest.writeByte(pt_enabled ? (byte) 1 : (byte) 0);
        dest.writeInt(this.pt_image_id);
        dest.writeDouble(this.pt_coordX);
        dest.writeDouble(this.pt_coordY);
    }

    protected PublicTransport(Parcel in) {
        pt_type_bus = in.readString();
        pt_type_train = in.readString();
        pt_type_genericPT = in.readString();
        pt_type_favourite = in.readString();
        this.pt_id = in.readInt();
        this.pt_type = in.readString();
        this.pt_name = in.readString();
        this.pt_company = in.readString();
        this.pt_route = in.readString();
        this.pt_info = in.readString();
        this.pt_enabled = in.readByte() != 0;
        this.pt_image_id = in.readInt();
        this.pt_coordX = in.readDouble();
        this.pt_coordY = in.readDouble();
    }

    public static final Creator<PublicTransport> CREATOR = new Creator<PublicTransport>() {
        @Override
        public PublicTransport createFromParcel(Parcel source) {
            return new PublicTransport(source);
        }

        @Override
        public PublicTransport[] newArray(int size) {
            return new PublicTransport[size];
        }
    };
}

