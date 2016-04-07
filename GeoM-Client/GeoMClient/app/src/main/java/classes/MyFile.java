package classes;

import android.os.Environment;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Exception;import java.lang.String;import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class MyFile {
    private static final String FILE_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    private String folderName;
    private String folderPath;
    private String fileName;
    private String filePath;

    public MyFile(){
        folderPath = Environment.getExternalStorageDirectory().toString();
        folderName = "GeoM";
        filePath = folderPath + File.separator + folderName;
        fileName = "favourites.xml";

        checkFolderAndFile();
    }

    public MyFile(String folderPath, String folderName, String filePath, String fileName){
        this.folderPath = folderPath;
        this.folderName = folderName;
        this.filePath = filePath;
        this.fileName = fileName;

        checkFolderAndFile();
    }

    private int add(Document doc, Element root, Favourite r){
        //favourite
        Element favourite = doc.createElement("favourite");
        root.appendChild(favourite);

        //pt_type
        Element pt_type = doc.createElement("pt_type");
        pt_type.appendChild(doc.createTextNode(r.getPt_type()));
        favourite.appendChild(pt_type);

        //pt_name
        Element pt_name = doc.createElement("pt_name");
        pt_name.appendChild(doc.createTextNode(r.getPt_name()));
        favourite.appendChild(pt_name);

        //pt_city
        Element pt_city = doc.createElement("pt_city");
        pt_city.appendChild(doc.createTextNode(r.getPt_city()));
        favourite.appendChild(pt_city);

        //pt_image_id
        Element pt_image_id = doc.createElement("pt_image_id");
        pt_image_id.appendChild(doc.createTextNode(Integer.toString(r.getPt_image_id())));
        favourite.appendChild(pt_image_id);

        return toFile(doc);
    }

    public int addFavourite(Favourite favourite){
        try {
            if(folderExistsAndNotEmpty(this.folderPath, this.folderName) > -1){//cartella ok
                int i;
                if((i = fileExistsAndNotEmpty(this.filePath, this.fileName)) == 0){//file vuoto
                    Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

                    Element root = doc.createElement("favourites");
                    doc.appendChild(root);

                    return add(doc, root, favourite);
                } else if(i == 1) {//file pieno
                    if(isDuplicate(favourite) == 0){//non ho un doppione

                        Document doc = toDocument(this.filePath, this.fileName);
                        Element root = doc.getDocumentElement();

                        return add(doc, root, favourite);
                    }
                    else return -2;//duplicato
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;//errore
    }

    public int checkFolderAndFile(){
        File folder = new File(folderPath + File.separator + folderName);
        try {
            if (folderExistsAndNotEmpty(folderPath, folderName) > -1) { //cartella esistente
                if(fileExistsAndNotEmpty(filePath, fileName) == -1) { //file inesistente
                    new File(filePath + File.separator + fileName).createNewFile();
                    return 0;//file vuoto
                }
                return 1;//file pieno
            } else{ //cartella inesistente (creo file e cartella)
                folder.mkdir();
                new File(filePath + File.separator + fileName).createNewFile();
                return 0;//file vuoto
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1; //errore
    }

    private int fileExistsAndNotEmpty(String filePath, String fileName){
        BufferedReader br;
        String line;

        try {
            if (new File(filePath + File.separator + fileName).exists()) {//file esistente
                br = new BufferedReader(new FileReader(filePath + File.separator + fileName));
                line = br.readLine();
                if (line != null && !line.equals(FILE_HEADER)) {
                        br.close();
                        return 1; //file pieno
                    }
                br.close();
                return 0; //file vuoto
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1; //file inesistente
    }

    private int folderExistsAndNotEmpty(String folderPath, String folderName){
        File folder = new File(folderPath + File.separator + folderName);

        if(folder.exists()) {
            File[] children = folder.listFiles();
            if(children.length > 0) return 1; //cartella con file
            return 0; //cartella vuota
        }

        return -1; //cartella inesistente
    }

    private String getValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }

    private int isDuplicate(Favourite favourite){
        List<Favourite> rList = toFavouritesList(this.filePath, this.fileName);

        for(int i = 0; i < rList.size(); i++){
            if(favourite.getPt_type().equals(rList.get(i).getPt_type())
                    && favourite.getPt_name().equals(rList.get(i).getPt_name())
                    && favourite.getPt_city().equals(rList.get(i).getPt_city())
                    && favourite.getPt_image_id() == rList.get(i).getPt_image_id()) return -1; //duplicato
        }
        return 0;//non duplicato
    }

    public int removeAllFavourites(){
        try{
            if(folderExistsAndNotEmpty(this.folderPath, this.folderName) > -1) {//cartella ok
                if (fileExistsAndNotEmpty(this.filePath, this.fileName) > -1) {//file ok
                    Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
                    return toFile(doc);
                }
            }
        } catch (ParserConfigurationException ex){
            return -1;
        }

        return -1;
    }

    public int removeFavourite(Favourite f){
        try {
            int pos = -1;//preferito non trovato
            List<Favourite> favList = getFavouritesList();

            for(int i = 0; i < favList.size(); i++){
                if(favList.get(i).equals(f)){
                    pos = i;
                }
            }
            if(pos > -1){//preferito trovato
                if(folderExistsAndNotEmpty(this.folderPath, this.folderName) > -1) {//cartella ok
                    if (fileExistsAndNotEmpty(this.filePath, this.fileName) > -1) {//file ok
                        //rimuovo il preferito
                        favList.remove(pos);

                        //creo un nuovo documento
                        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

                        if(!favList.isEmpty()){//se ci sono altri preferiti
                            //creo l'elemento radice
                            Element root = doc.createElement("favourites");
                            doc.appendChild(root);
                            //aggiungo uno alla volta tutti gli elementi alla radice
                            for(int i = 0; i < favList.size(); i++){
                                if(add(doc, root, favList.get(i))!= 0){
                                    return -1;//preferito non aggiunto
                                }
                            }
                            return 0;//tutti i preferiti aggiunti
                        } else{//documento vuoto
                            return toFile(doc);
                        }
                    }
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private int toFile(Document doc){
        try {
            //scrivo il contenuto nel file xml
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes"); //indento il file
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filePath + File.separator + fileName));

            //trasformo il documento
            transformer.transform(source, result);

            return 0;//salvato
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;//erorre
    }

    private Document toDocument(String filePath, String fileName){
        try {
            File xmlFile = new File(filePath + File.separator + fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Favourite> toFavouritesList(String filePath, String fileName){
        try {
            Document doc = toDocument(filePath, fileName);

            NodeList nList = doc.getElementsByTagName("favourite");
            List<Favourite> fList = new ArrayList<>();

            for(int i = 0; i < nList.getLength(); i++){
                Node node = nList.item(i);//ottengo il nodo

                if (node.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element) node;//ottengo l'elemento nel nodo

                    //aggiungo un nuovo preferito
                    fList.add(new Favourite(
                            getValue("pt_type", element),
                            getValue("pt_name", element),
                            getValue("pt_city", element),
                            Integer.parseInt(getValue("pt_image_id", element))));
                }
            }
            return fList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Favourite> getFavouritesList(){
        List<Favourite> favouritesList = new ArrayList<>();

        if(fileExistsAndNotEmpty(this.filePath, this.fileName) > 0) {//file esistente e pieno
            return toFavouritesList(this.filePath, this.fileName);
        }
        return favouritesList;//file inesistente o vuoto
    }
}

