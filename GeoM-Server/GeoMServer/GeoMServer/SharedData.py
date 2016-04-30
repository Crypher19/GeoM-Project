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
        self.transportList = [] # lista di thread transport attivi
        self.fileMezziXML = "mezzi.xml"     

    def addTransport(self, transport):        
        self.transportList.append(transport) # aggiungo un elemento ThreadTrasport nella lista
        self.transportList[-1].start() # parte il threadTransport | -1 -> ultimo elemento
        
    def delTransport(self, index):
        del self.transportList[index]

    def getDOMTransportsList(self, tipoMezzo=None, limit=None, offset=None):
        self.listaMezzi = self.db.getTransports(tipoMezzo, limit, offset)
        pxml = ParserXML()
        doc = pxml.getDOMOfTransportsList(self.listaMezzi)
        return doc

    def getTransportI(self, nome, compagnia, tratta):
        for i, thMezzo in enumerate(self.transportList):
            if thMezzo.mezzo.nomeMezzo == nome and thMezzo.mezzo.compagnia == compagnia and thMezzo.mezzo.tratta == tratta:
                return i  # posizione nella lista
        return -1
        
    def checkLogin(self, username, password):
        auth = self.db.getUser(username) # ricevo una tupla (username, password)

        if auth:
            # controllo se la password è corretta
            if bcrypt.hashpw(password.encode('utf-8'), auth[1].encode('utf-8')) == auth[1].encode('utf-8'):
                print("Password corretta.")
                return True
            else:
                print("Password errata.")
                return False
        else:
            print("Username errato.")
            return False

    