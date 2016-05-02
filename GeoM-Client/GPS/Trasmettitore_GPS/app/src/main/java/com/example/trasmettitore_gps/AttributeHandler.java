package com.example.trasmettitore_gps;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

//estrapola un attributo da un tag di un messaggio XML
public class AttributeHandler extends DefaultHandler {

	private String returnAttribute;
	private String attribute;
	private String element;

	public AttributeHandler(String element, String attribute){
		returnAttribute = null;
		this.attribute = attribute;
		this.element = element;
	}

	//metodo eseguito dal parser ogni volta che trova un tag di apertura
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

		if (qName.equalsIgnoreCase(element)) {//quando trovo il tag <element>
			//prendo l'attributo del tag
			returnAttribute = attributes.getValue(attribute);
		}
	}

	public String getAttribute() {
		return returnAttribute;
	}
}