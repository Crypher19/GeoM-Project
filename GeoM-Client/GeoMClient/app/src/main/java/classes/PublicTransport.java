package classes;

import android.os.Parcel;
import android.os.Parcelable;

import com.geom.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.Serializable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class PublicTransport implements Serializable, Parcelable{
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
                           String pt_route, boolean pt_enabled, double pt_coordX, double pt_coordY){
        this.pt_id = pt_id;
        this.pt_type = pt_type;
        this.pt_name = pt_name;
        this.pt_company = pt_company;
        this.pt_route = pt_route;
        this.pt_enabled = pt_enabled;
        this.pt_coordX = pt_coordX;
        this.pt_coordY = pt_coordY;

        if(pt_type.equals(pt_type_bus)){
            this.pt_image_id = R.drawable.ic_material_bus_grey;//bus
        } else if(pt_type.equals(pt_type_train)){
            this.pt_image_id = R.drawable.ic_material_train_grey;//treno
        } else this.pt_image_id = R.drawable.ic_material_no_image_grey;//sconosciuto

        this.pt_info = null;//evito errori in fase di cancellazione preferito
    }

    //costruttore specifico
    public PublicTransport(int pt_id, String pt_type, String pt_name, String pt_company,
                           String pt_route, boolean pt_enabled){
        this.pt_id = pt_id;
        this.pt_type = pt_type;
        this.pt_name = pt_name;
        this.pt_company = pt_company;
        this.pt_route = pt_route;
        this.pt_enabled = pt_enabled;
        this.pt_coordX = 0.d;
        this.pt_coordY = 0.d;

        if(pt_type.equals(pt_type_bus)){
            this.pt_image_id = R.drawable.ic_material_bus_grey;//bus
        } else if(pt_type.equals(pt_type_train)){
            this.pt_image_id = R.drawable.ic_material_train_grey;//treno
        } else this.pt_image_id = R.drawable.ic_material_no_image_grey;//sconosciuto

        this.pt_info = null;//evito errori in fase di cancellazione preferito
    }

    //costruttore generico
    public PublicTransport(String pt_type, String pt_info, int pt_image_id){
        this.pt_type = pt_type;
        this.pt_info = pt_info;
        this.pt_image_id = pt_image_id;
    }

    public Document getDOMPT() throws ParserConfigurationException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();

        Element rootElement = doc.createElement("mezzi"); // creo la radice "mezzi"
        Element elMezzo = doc.createElement("mezzo"); // creo l'elemento "mezzo"
        elMezzo.setAttribute("id", Integer.toString(pt_id)); // imposto l'attributo "id"

        Element elTipo = doc.createElement("tipo"); // creo l'elemento "tipo"
        elTipo.appendChild(doc.createTextNode(pt_type));// aggiungo all'elemento un nodo di tipo testo contenente il valore della query
        elMezzo.appendChild(elTipo); // aggiungo l'elemento al mezzo

        Element elCompagnia = doc.createElement("compagnia"); // creo l'elemento "compagnia"
        elCompagnia.appendChild(doc.createTextNode(pt_company)); //# aggiungo all'elemento un nodo di tipo testo contenente il valore della query
        elMezzo.appendChild(elCompagnia); // aggiungo l'elemento al mezzo

        Element elNome = doc.createElement("nome"); // creo l'elemento "nome"
        elNome.appendChild(doc.createTextNode(pt_name)); // aggiungo all'elemento un nodo di tipo testo contenente il valore della query
        elMezzo.appendChild(elNome); // aggiungo l'elemento al mezzo

        Element elTratta = doc.createElement("tratta"); // creo l'elemento "tratta"
        elTratta.appendChild(doc.createTextNode(pt_route)); // aggiungo all'elemento un nodo di tipo testo contenente il valore della query
        elMezzo.appendChild(elTratta); // aggiungo l'elemento al mezzo

        Element elAttivo = doc.createElement("attivo"); // creo l'elemento "attivo"
        elAttivo.appendChild(doc.createTextNode(Boolean.toString(pt_enabled))); // aggiungo all'elemento un nodo di tipo testo contenente il valore della query
        elMezzo.appendChild(elAttivo); // aggiungo l'elemento al mezzo

        // se sono stati modificati, aggiungo l'XML delle coordinate
       /* if (pt_coordX != 0.d && pt_coordY != 0.d) {
            Element elCoordX = doc.createElement("coordX"); // creo l'elemento "coordX"
            elCoordX.appendChild(doc.createTextNode(Double.toString(pt_coordX))); // aggiungo all'elemento un nodo di tipo testo contenente il valore della query
            elMezzo.appendChild(elCoordX); // aggiungo l'elemento al mezzo
            Element elCoordY = doc.createElement("coordY"); // creo l'elemento "coordY"
            elCoordY.appendChild(doc.createTextNode(Double.toString(pt_coordY))); // aggiungo all'elemento un nodo di tipo testo contenente il valore della query
            elMezzo.appendChild(elCoordY); // aggiungo l'elemento al mezzo
        }*/
        rootElement.appendChild(elMezzo); // aggiungo l'oggetto "mezzo" all'oggetto radice
        doc.appendChild(rootElement); // aggiungo la radice al documento
        return doc;
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
            if (pt_info == null) {//train, bus, favorite
                return this.pt_id == pt.getPt_id()
                        && this.pt_type.equals(pt.getPt_type())
                        && this.pt_name.equals(pt.getPt_name())
                        && this.pt_company.equals(pt.getPt_company())
                        && this.pt_route.equals(pt.getPt_route())
                        && this.pt_enabled == pt.isPt_enabled();
            } else {//generic pt
                return this.pt_image_id == pt.getPt_image_id()
                        && this.pt_type.equals(pt.getPt_type())
                        && this.pt_info.equals(pt.getPt_info());
            }
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

