package classes;

import com.example.mattia.geom.R;

import java.io.Serializable;

public class PublicTransport implements Serializable {
    private String PTType;
    private String PTDescription;
    private int PTPhotoID;

    public PublicTransport(String PTType, String PTDescription, int PTPhotoID){
        this.PTType=PTType;
        this.PTDescription=PTDescription;
        this.PTPhotoID=PTPhotoID;
    }

    //PT without any image
    public PublicTransport(String PTType, String PTDescription){
        this.PTType=PTType;
        this.PTDescription=PTDescription;
        this.PTPhotoID= R.mipmap.ic_material_no_image_grey;
    }

    public String getPTType(){
        return this.PTType;
    }

    public String getPTDescription(){
        return this.PTDescription;
    }

    public int getPTPhotoID(){
        return PTPhotoID;
    }

    public void setPTType(String PTName){
        this.PTType=PTName;
    }

    public void setPTDescription(String PTDescription){
        this.PTDescription=PTDescription;
    }

    public void setPTPhotoID(int PTPhotoID){
        this.PTPhotoID = PTPhotoID;
    }
}

