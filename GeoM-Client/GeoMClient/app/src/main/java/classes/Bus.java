package classes;

import com.example.mattia.geom.R;

public class Bus extends PublicTransport {

    private String PTName;
    private String PTCity;

    public Bus(String PTName, String PTCity){
        super("Bus", "Include ASF, Urbani e Internurbani", R.mipmap.ic_material_bus_grey);
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
