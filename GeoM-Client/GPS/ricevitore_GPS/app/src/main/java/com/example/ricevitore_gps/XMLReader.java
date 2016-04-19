package com.example.ricevitore_gps;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class XMLReader {
	
	private SAXParser parser;
	
	public XMLReader(){
		//inizializzo il parser
		try {
			parser = SAXParserFactory.newInstance().newSAXParser();
		} catch (ParserConfigurationException | SAXException e) {
			e.printStackTrace();
		}
	}
	
	//estrapola il testo contenuto tra due tag all'interno di un messaggio XML
	public String getElement(String message, String element){
		try {
			//inizializzo il mio handler (override dei metodi startElement() e characters())
			ElementHandler elementHandler = new ElementHandler(element);

			//il parser analizza il messaggio specificato
			parser.parse(new InputSource(new ByteArrayInputStream(message.getBytes("utf-8"))), elementHandler);

			//alla fine dell'analisi ottengo l'elemento richiesto
			return elementHandler.getElement();
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}

		return "1";//errore
	}
	
	//estrapolo un attributo da un tag di un messaggio XML
	public String getAttribute(String message, String element, String attribute){
		//inizializzo il mio handler (override del metodo startElement())
		AttributeHandler attrHandler = new AttributeHandler(element, attribute);
		
		try {
			//il parser analizza il messaggio specificato
			parser.parse(new InputSource(new ByteArrayInputStream(message.getBytes("utf-8"))), attrHandler);
			//alla fine dell'analisi ottengo l'attributo richiesto
			return attrHandler.getAttribute();
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}

		return "1";//errore
	}
}
