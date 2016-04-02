import bcrypt
from xml.dom import minidom
from Database import Database
from UserThread import UserThread
from TransportThread import TransportThread
from ParserXML import ParserXML

class SharedData:
    def __init__(self):
        self.db = Database()
        self.listaMezzi = [] # Lista di QueryResult
        self.transportList = []
        self.fileMezziXML = "mezzi.xml"

    def addTransport(self, transport):        
        self.transportList.append(transport) # aggiungo un elemento ThreadTrasport nella lista
        self.transportList[-1].start() # parte il threadTransport | -1 -> ultimo elemento

    def getXMLTransportsList(self):
        self.listaMezzi = self.db.getTransports()
        pxml = ParserXML()
        doc = pxml.getDOMOfTransportsList(self.listaMezzi)
        msg = doc.toxml()
        msg = msg.replace("\n", "")
        return msg

    def getTransportI(self, nome, compagnia, tratta):
        for i, mezzo in enumerate(self.listaMezzi):
            if mezzo.nomeMezzo == nome and mezzo.compagnia == compagnia and mezzo.tratta == tratta:
                return i #posizione nella lista
        return False
        
    def checkLogin(self, username, password):
        user = self.db.getUser(username) # ricevo una tupla (username, password)
        if bcrypt.hashpw(password, user[1]) == user[1]:
            print("It matches")
        else:
            print("It does not match")

    