from xml.dom import minidom
from Database import *
from UserThread import *
from TransportThread import *

class SharedData(object):
    def __init__(self):
        self.prova = "ciao"
        global db
        db = Database()
        # lista trasporti e relativo indice
        global lastT
        lastT =0

    def cambiaProva(self):
        self.prova = "cambiato"

    def dbConnect(self):
        """da provare"""
        db.connect()

    def toDOCObject(self, string):        
        return minidom.parseString(string) # Ritorna oggetto tipo doc

    def readXMLTable(self, filename):        
        doc = minidom.parse(filename)

        # doc.getElementsByTagName returns NodeList
        buses = doc.getElementsByTagName("buses")
        for bus in buses:
            line = bus.getElementsByTagName("line")[0] # obtain the bus line
            print(line.getAttribute("code") + " " + line.firstChild.nodeValue) # print bus code and value of the line element

    def addTransport(self,transport):
        global TransportsList
        TransportList[lastT] = transport
        TransportList[lastT].start()
        lastT+=1
