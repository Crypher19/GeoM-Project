from xml.dom import minidom

class ParserXML:
    """description of class"""

    def toDOMObject(self, string):        
        return minidom.parseString(string) # Ritorna oggetto tipo doc

    def readXMLTable(self, filename):        
        doc = minidom.parse(filename)

        # doc.getElementsByTagName returns NodeList
        buses = doc.getElementsByTagName("buses")
        for bus in buses:
            line = bus.getElementsByTagName("line")[0] # obtain the bus line
            print(line.getAttribute("code") + " " + line.firstChild.nodeValue) # print bus code and value of the line element

    def getDOMOfTransportsList(self, filename):
        doc = minidom.parse(filename)

        # doc.getElementsByTagName returns NodeList
        buses = doc.getElementsByTagName("mezzi")
        bus = buses.item(0)
        linea = bus.getElementsByTagName("linea")[0]
       
        linea.firstChild.nodeValue = "pippo"
        print(linea.firstChild.nodeValue)
        tratta = bus.getElementsByTagName("tratta")
        
        #modificare linea e anche tratta

        #buses.appendChild(bus)
        return doc
        


