from xml.dom import minidom
from Database import Database
from UserThread import UserThread
from TransportThread import TransportThread
from ParserXML import ParserXML

class SharedData:
    def __init__(self):
        self.db = Database()
        self.listaMezzi = []
        self.fileMezziXML = "mezzi.xml"

    def addTransport(self, transport):
        transportList = []
        transportList.append(transport) # aggiungo un elemento ThreadTrasport nella lista
        transportList[-1].start() # parte il threadTransport | -1 -> ultimo elemento

    def getXMLTransportsList(self):
        self.listaMezzi = self.db.getTransports()
        pxml = ParserXML()
        doc = pxml.getDOMOfTransportsList(self.listaMezzi)
        msg = doc.toxml()
        msg = msg.replace("\n", "")
        return msg