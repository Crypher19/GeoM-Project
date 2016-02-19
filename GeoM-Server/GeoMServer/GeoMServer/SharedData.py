from xml.dom import minidom
from database import *


class SharedData(object):
    def __init__(self):
        self.prova = "ciao"
        global db
        db = database()

    def cambiaProva(self):
        self.prova = "cambiato"

    def startServer(self):
        """da provare"""
        db.connect()

    def readXMLTable(self, filename):        
        doc = minidom.parse(filename)

        # doc.getElementsByTagName returns NodeList
        buses = doc.getElementsByTagName("buses")
        for bus in buses:
            line = bus.getElementsByTagName("line")[0] # obtain the bus line
            print(line.getAttribute("code") + " " + line.firstChild.nodeValue) # print bus code and value of the line element
