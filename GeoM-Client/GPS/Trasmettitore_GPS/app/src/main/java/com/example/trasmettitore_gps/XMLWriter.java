package com.example.trasmettitore_gps;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

//import javax.xml.transform.OutputKeys;

public class XMLWriter {
	private DocumentBuilder docBuilder;
	private Document doc;//documento xml

	public XMLWriter(){
		//costruisco un nuovo documento
		try {
			docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		doc = docBuilder.newDocument();
	}

	public String toXMLMessage(Coordinate c){

		//radice 
		Element root = doc.createElement("messaggio");
		//lo aggiungo al Document
		doc.appendChild(root);

		//coordinate
		Element coord = doc.createElement("coordinate");
		root.appendChild(coord);

		//latitudine
		Element lat = doc.createElement("latitudine");
		lat.appendChild(doc.createTextNode(Double.toString(c.getLat())));
		coord.appendChild(lat);
				
		//longitudine
		Element lng = doc.createElement("longitudine");
		lng.appendChild(doc.createTextNode(Double.toString(c.getLng())));
		coord.appendChild(lng);


		try {
			//istanzio il transformer
			Transformer transformer = TransformerFactory.newInstance().newTransformer();

			//istanzio sorgente e destinazione
			DOMSource source = new DOMSource(doc);
			StringWriter strWriter = new StringWriter();
			StreamResult result = new StreamResult(strWriter);

			//trasformo il documento
			transformer.transform(source, result);

			//converto il risultato in String
			StringBuffer sb = strWriter.getBuffer(); 
			String str = sb.toString();
			
			return str;
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return null;//errore
	}
}
