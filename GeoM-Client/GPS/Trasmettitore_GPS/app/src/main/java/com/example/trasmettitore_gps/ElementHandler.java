package com.example.trasmettitore_gps;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

//estrapola il testo contenuto tra due tag all'interno di un messaggio XML
public class ElementHandler extends DefaultHandler {

	private boolean bool;
	private String element;
	private String returnElement;
	
	public ElementHandler(String element){
		this.element = element;
		bool = false;
		returnElement = null;
	}
	
	//metodo eseguito dal parser ogni volta che trova un tag di apertura
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

		if (qName.equalsIgnoreCase(element)) {//quando trovo il tag <element>
			bool = true;
		}
	}

	//metodo eseguito dal parser ogni volta che trova del testo tra due tag
	@Override
	public void characters(char ch[], int start, int length) throws SAXException {

		if (bool) {//quando trovo del testo tra <element> e </element>
			returnElement = new String(ch, start, length);
			bool = false;
		}
	}

	public String getElement(){
		return returnElement;
	}
}