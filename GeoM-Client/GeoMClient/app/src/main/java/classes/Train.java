package classes;

import com.example.mattia.geom.R;

public class Train extends PublicTransport {

    private String PTName;
    private String PTCity;

    public Train(String PTName, String PTCity){
        super("Train", "Include Trennord, Trenitalia e Italo", R.mipmap.ic_material_train_grey);
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
}
