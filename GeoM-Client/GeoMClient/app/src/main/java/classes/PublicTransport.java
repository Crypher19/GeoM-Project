package classes;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.mattia.geom.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.Serializable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class PublicTransport implements Serializable, Parcelable{
    //tipi di default
    private final String pt_type_bus = "Bus";
    private final String pt_type_train = "Treno";
    private final String pt_type_genericPT = "publictransport";
    private final String pt_type_favourite = "favourite";

    protected String pt_type; //tipo
    protected String pt_name; //nome
    protected String pt_route; //tratta
    protected String pt_info; //informazioni generali (solo per pt generico)
    protected int pt_image_id; //icona

    //costruttore specifico
    public PublicTransport(String pt_type, String pt_name, String pt_route){
        this.pt_type = pt_type;
        this.pt_name = pt_name;
        this.pt_route = pt_route;

        if(pt_type.equals(pt_type_bus)){
            this.pt_image_id = R.mipmap.ic_material_bus_grey;//bus
        } else if(pt_type.equals(pt_type_train)){
            this.pt_image_id = R.mipmap.ic_material_train_grey;//treno
        } else this.pt_image_id = R.mipmap.ic_material_no_image_grey;//sconosciuto
    }

    //costruttore generico
    public PublicTransport(String pt_type, String pt_info, int pt_image_id){
        this.pt_type = pt_type;
        this.pt_info = pt_info;
        this.pt_image_id = pt_image_id;
    }

    /*public Document getDOMPT() throws ParserConfigurationException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();

        Element rootElement = doc.createElement("mezzi"); // creo la radice "posizione"

        Element elMezzo = doc.createElement("mezzo"); // creo l'elemento "mezzo"
        elMezzo.setAttribute("id", Integer.toString(PTID)); // imposto l'attributo "id"

        Element elTipo = doc.createElement("tipo"); // creo l'elemento "tipo"
        elTipo.appendChild(doc.createTextNode(PTType));// aggiungo all'elemento un nodo di tipo testo contenente il valore della query
        elMezzo.appendChild(elTipo); // aggiungo l'elemento al mezzo

        Element elCompagnia = doc.createElement("compagnia"); // creo l'elemento "compagnia"
        elCompagnia.appendChild(doc.createTextNode(Integer.toString(PTCompany))); //# aggiungo all'elemento un nodo di tipo testo contenente il valore della query
        elMezzo.appendChild(elCompagnia); // aggiungo l'elemento al mezzo

        Element elNome = doc.createElement("nome"); // creo l'elemento "nome"
        elNome.appendChild(doc.createTextNode(PTName)); // aggiungo all'elemento un nodo di tipo testo contenente il valore della query
        elMezzo.appendChild(elNome); // aggiungo l'elemento al mezzo

        Element elTratta = doc.createElement("tratta"); // creo l'elemento "tratta"
        elTratta.appendChild(doc.createTextNode(PTCity)); // aggiungo all'elemento un nodo di tipo testo contenente il valore della query
        elMezzo.appendChild(elTratta); // aggiungo l'elemento al mezzo

        Element elAttivo = doc.createElement("attivo"); // creo l'elemento "attivo"
        elAttivo.appendChild(doc.createTextNode(Boolean.toString(PTEnabled))); // aggiungo all'elemento un nodo di tipo testo contenente il valore della query
        elMezzo.appendChild(elAttivo); // aggiungo l'elemento al mezzo

        // se sono stati modificati, aggiungo l'XML delle coordinate
        if (coordX != 0.d && coordY != 0.d) {
            Element elCoordX = doc.createElement("coordX"); // creo l'elemento "coordX"
            elCoordX.appendChild(doc.createTextNode(Double.toString(coordX))); // aggiungo all'elemento un nodo di tipo testo contenente il valore della query
            elMezzo.appendChild(elCoordX); // aggiungo l'elemento al mezzo

            Element elCoordY = doc.createElement("coordY"); // creo l'elemento "coordY"
            elCoordY.appendChild(doc.createTextNode(Double.toString(coordY))); // aggiungo all'elemento un nodo di tipo testo contenente il valore della query
            elMezzo.appendChild(elCoordY); // aggiungo l'elemento al mezzo
        }
        rootElement.appendChild(elMezzo); // aggiungo l'oggetto "mezzo" all'oggetto radice
        return doc;
    }*/

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

    //implementazione Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pt_type);
        dest.writeString(pt_name);
        dest.writeString(pt_route);
        dest.writeString(pt_info);
        dest.writeInt(pt_image_id);
    }

    public static final Parcelable.Creator<PublicTransport> CREATOR = new Parcelable.Creator<PublicTransport>() {
        public PublicTransport createFromParcel(Parcel pc) {
            return new PublicTransport(pc);
        }
        public PublicTransport[] newArray(int size) {
            return new PublicTransport[size];
        }
    };

    public PublicTransport(Parcel pc){
        pt_type = pc.readString();
        pt_name = pc.readString();
        pt_route = pc.readString();
        pt_info = pc.readString();
        pt_image_id = pc.readInt();
    }

    //override equals
    public boolean equals(String type, PublicTransport pt){

        if(type.equals(pt_type_bus) || type.equals(pt_type_train)){//train, bus
            return this.pt_name.equals(pt.getPt_name())
                    &&this.pt_route.equals(pt.getPt_route());
        }else if(type.equals(pt_type_favourite)){ //favourites
            return this.pt_image_id == pt.getPt_image_id()
                    && this.pt_type.equals(pt.getPt_type())
                    && this.pt_name.equals(pt.getPt_name())
                    && this.pt_route.equals(pt.getPt_route());
        } else if(type.equals(pt_type_genericPT)){//generic pt
            return this.pt_image_id == pt.getPt_image_id()
                    && this.pt_type.equals(pt.getPt_type())
                    && this.pt_info.equals(pt.getPt_info());
        }
         return false;
    }
}

