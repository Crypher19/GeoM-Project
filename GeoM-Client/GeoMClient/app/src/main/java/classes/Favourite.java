package classes;

import java.lang.String;

public class Favourite {
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
}
