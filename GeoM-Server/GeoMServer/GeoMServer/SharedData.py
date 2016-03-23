from xml.dom import minidom
from Database import *
from UserThread import *
from TransportThread import *
from XMLclass import *

class SharedData(object):
    def __init__(self):
        self.prova = "ciao"
        global db
        db = Database()
        # lista trasporti e relativo indice

    def cambiaProva(self):
        self.prova = "cambiato"

    def getTransportList(self):
        listaMezzi = list()
        listaMezzi = db.getTransports()

    def addTransport(self, transport):
        global transportList
        transportList = list()
        transportList.append(transport) # aggiungo un elemento ThreadTrasport nella lista
        transportList[-1].start() # parte il threadTransport | -1 -> ultimo elemento