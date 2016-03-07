from xml.dom import minidom
from Database import *
from UserThread import *
from TransportThread import *

class SharedData(object):
    def __init__(self):
        self.prova = "ciao"
        global db
        db = Database()
        global TransportsList

    def cambiaProva(self):
        self.prova = "cambiato"

    def dbConnect(self):
        """da provare"""
        db.connect()

    def toDOCObject(self, string):        
        doc = minidom.parseString(string)

        # doc.getElementsByTagName returns NodeList
        msgs = doc.getElementsByTagName("messaggio")
        for msg in msgs:
            tipo = msg.getElementsByTagName("tipo")[0] # obtain the msg type
            print(tipo.firstChild.nodeValue) # print tipo
        

    def readXMLTable(self, filename):        
        doc = minidom.parse(filename)

        # doc.getElementsByTagName returns NodeList
        buses = doc.getElementsByTagName("buses")
        for bus in buses:
            line = bus.getElementsByTagName("line")[0] # obtain the bus line
            print(line.getAttribute("code") + " " + line.firstChild.nodeValue) # print bus code and value of the line element
