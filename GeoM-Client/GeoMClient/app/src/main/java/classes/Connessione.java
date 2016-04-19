package classes;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.Socket;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Connessione {
	private String serverIP;
	private int serverPort;
	
	private Socket connessione;

	private OutputStream out;
	private PrintWriter sOUT;
	
	private InputStreamReader in;
	private BufferedReader sIN;

	
	/*
	    // ottengo la lista di tutti gli elementi "username"
		nodelist = doc.getElementsByTagName("tipo"); 
		
		// prendo il primo elemento e modifico il testo in base al valore scelto dall'utente
		nodelist.item(0).setTextContent("transport");
	*/

	public Connessione(String serverIP, int serverPort) {
		this.serverIP = serverIP;
		this.serverPort = serverPort;
	}
	
	public void startConn() {
		try {
			// connessione con il server
			connessione = new Socket(serverIP, serverPort);
			
			// flusso in uscita su socket
			out = connessione.getOutputStream();
			sOUT = new PrintWriter(out);
			
			// flusso in ingresso su socket
			in = new InputStreamReader(connessione.getInputStream());
			sIN = new BufferedReader(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e("ERRORE", e.getMessage());
		}
	}
	
	public void closeConn() throws IOException {
		connessione.close();
	}
	
	public Document fileToDOMObject(String filename) throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false); // non controllo un eventuale DTD associato
		return factory.newDocumentBuilder().parse(new File(filename)); // ottengo il documento XML		
	}

    public Document getDOMType(String msg) throws ParserConfigurationException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();
        // root element
        Element rootElement = doc.createElement("messaggio");
        Element tipo = doc.createElement("tipo");
        tipo.appendChild(doc.createTextNode(msg));
        rootElement.appendChild(tipo);
        doc.appendChild(rootElement);
        return doc;
    }
	
	public Document getDOMResponse(String msg) throws ParserConfigurationException {
	    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();
        // root element
        Element rootElement = doc.createElement("messaggio");
        rootElement.appendChild(doc.createTextNode(msg));
        doc.appendChild(rootElement);
        return doc;
	}
	
	public Document getDOMAutenticazione(String user, String pass) throws ParserConfigurationException {
	    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();

        Element rootElement = doc.createElement("autenticazione"); // creo la radice "autenticazione"
       
        Element username = doc.createElement("username"); // creo l'elemento "username"
        username.appendChild(doc.createTextNode(user)); // creo e inserisco il valore dell'elemento "username"
        rootElement.appendChild(username); // aggiungo l'elemento alla radice

        Element password = doc.createElement("password"); // creo l'elemento "password"
        password.appendChild(doc.createTextNode(pass)); // creo e inserisco il valore dell'elemento "password"
        rootElement.appendChild(password); // aggiungo l'elemento alla radice
        
        doc.appendChild(rootElement); // aggiungo la radice al documento
        return doc;
	}
	
	public Document getDOMPosizione(String coordX, String coordY) throws ParserConfigurationException {
	    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();

        Element rootElement = doc.createElement("posizione"); // creo la radice "posizione"
       
        Element elCoordX = doc.createElement("coordX"); // creo l'elemento "coordX"
        elCoordX.appendChild(doc.createTextNode(coordX)); // creo e inserisco il valore dell'elemento "coordX"
        rootElement.appendChild(elCoordX); // aggiungo l'elemento alla radice

        Element elCoordY = doc.createElement("coordY"); // creo l'elemento "coordY"
        elCoordY.appendChild(doc.createTextNode(coordY)); // creo e inserisco il valore dell'elemento "coordY"
        rootElement.appendChild(elCoordY); // aggiungo l'elemento alla radice
        
        doc.appendChild(rootElement); // aggiungo la radice al documento
        return doc;
	}

	public void sendMessage(String str) {		
		sOUT.println(str);
		sOUT.flush();
	}
	
	public void sendMessage(Document doc) throws SAXException, IOException, ParserConfigurationException {			
		// converto il Document in String e lo invio
		sOUT.println(convertDocumentToString(doc));
		sOUT.flush();
	}
	
	public String readMessage() throws IOException {
		return sIN.readLine();
	}	

	// metodi statici per la conversione da Document a String e viceversa
	public static String convertDocumentToString(Document doc) {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = tf.newTransformer();
            // below code to remove XML declaration
            // transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            String output = writer.getBuffer().toString();
            return output;
        } catch (TransformerException e) {
            e.printStackTrace();
        }
         
        return null;
    }
 
    public static Document convertStringToDocument(String xmlStr) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
        DocumentBuilder builder;  
        try 
        {  
            builder = factory.newDocumentBuilder();  
            Document doc = builder.parse( new InputSource( new StringReader( xmlStr ) ) ); 
            return doc;
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
        return null;
    }

    public String getServerIP() {
        return serverIP;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }
}
