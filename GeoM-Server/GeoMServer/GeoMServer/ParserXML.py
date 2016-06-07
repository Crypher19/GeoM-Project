from xml.dom import minidom
from Transport import Transport

class ParserXML:
    def toDOMObject(self, string):        
        return minidom.parseString(string) # ritorna oggetto tipo document

    def DOCtoString(self, doc):
        if isinstance(doc, minidom.Document):
            msg = mex.toxml()
            msg = mex.replace("\n", "")
            return msg
        return None

    def getTransportObj(self, doc):
        mezzi = doc.getElementsByTagName("mezzi")[0]
        mezzo = mezzi.getElementsByTagName("mezzo")[0]
        id = mezzo.getAttribute("id")
        tipo = mezzo.getElementsByTagName("tipo")[0].firstChild.nodeValue
        compagnia = mezzo.getElementsByTagName("compagnia")[0].firstChild.nodeValue
        nome = mezzo.getElementsByTagName("nome")[0].firstChild.nodeValue
        tratta = mezzo.getElementsByTagName("tratta")[0].firstChild.nodeValue
        attivo = mezzo.getElementsByTagName("attivo")[0].firstChild.nodeValue
        return Transport(id, tipo, nome, tratta, attivo, compagnia)

    def getUsernameAndPassword(self, doc):
        autenticazione = doc.getElementsByTagName("autenticazione")[0]
        username = autenticazione.getElementsByTagName("username")[0].firstChild.nodeValue
        password = autenticazione.getElementsByTagName("password")[0].firstChild.nodeValue
        return (username, password)

    def getLimitOffsetAndPTtype(self, doc):
        richiesta = doc.getElementsByTagName("richiesta")[0]
        tipo = richiesta.getElementsByTagName("tipo")[0].firstChild.nodeValue
        limit = richiesta.getElementsByTagName("limit")[0].firstChild.nodeValue
        offset = richiesta.getElementsByTagName("offset")[0].firstChild.nodeValue   
        return (tipo, limit, offset)

    def getDOMOfTransportsList(self, listaMezzi):      
        DOMimpl = minidom.getDOMImplementation()
        xmldoc = DOMimpl.createDocument(None, "mezzi", None)
       
        for mezzo in listaMezzi:
            self.buildXMLMezzo(xmldoc, mezzo)

        #print(xmldoc.toprettyxml())  
        return xmldoc

    def readDOMResponse(self, doc, element):
        try:
            contenuto = doc.getElementsByTagName(element)[0].firstChild.nodeValue
            return contenuto
        except IndexError:
            return None

    def getDOMResponse(self, msg="OK"):
        # creo un oggetto di tipo DOM (Documento XML)
        DOMimpl = minidom.getDOMImplementation()
        xmldoc = DOMimpl.createDocument(None, "messaggio", None)
        rootMezzi = xmldoc.documentElement
        rootMezzi.appendChild(xmldoc.createTextNode(msg))  # aggiungo all'elemento un nodo di tipo testo contenente il valore "OK"
        return xmldoc

    def buildXMLMezzo(self, xmldoc, mezzo):
        rootMezzi = xmldoc.documentElement

        elMezzo = xmldoc.createElement("mezzo") # creo l'elemento "mezzo"
        elMezzo.setAttribute("id", str(mezzo.ID)) # imposto l'attributo "id"
            
        elTipo = xmldoc.createElement("tipo") # creo l'elemento "tipo"
        elTipo.appendChild(xmldoc.createTextNode(mezzo.tipoMezzo)) # aggiungo all'elemento un nodo di tipo testo contenente il valore della query
        elMezzo.appendChild(elTipo) # aggiungo l'elemento al mezzo

        elCompagnia = xmldoc.createElement("compagnia") # creo l'elemento "compagnia"
        elCompagnia.appendChild(xmldoc.createTextNode(str(mezzo.compagnia))) # aggiungo all'elemento un nodo di tipo testo contenente il valore della query
        elMezzo.appendChild(elCompagnia) # aggiungo l'elemento al mezzo

        elNome = xmldoc.createElement("nome") # creo l'elemento "nome"
        elNome.appendChild(xmldoc.createTextNode(mezzo.nomeMezzo)) # aggiungo all'elemento un nodo di tipo testo contenente il valore della query
        elMezzo.appendChild(elNome) # aggiungo l'elemento al mezzo

        elTratta = xmldoc.createElement("tratta") # creo l'elemento "tratta"
        elTratta.appendChild(xmldoc.createTextNode(mezzo.tratta)) # aggiungo all'elemento un nodo di tipo testo contenente il valore della query
        elMezzo.appendChild(elTratta) # aggiungo l'elemento al mezzo

        elAttivo = xmldoc.createElement("attivo") # creo l'elemento "attivo"
        elAttivo.appendChild(xmldoc.createTextNode(mezzo.attivo)) # aggiungo all'elemento un nodo di tipo testo contenente il valore della query
        elMezzo.appendChild(elAttivo) # aggiungo l'elemento al mezzo

        # se sono stati modificati, aggiungo l'XML delle coordinate
        if mezzo.posX != None and mezzo.posY != None:
            elPosX = xmldoc.createElement("posX") # creo l'elemento "posX"
            elPosX.appendChild(xmldoc.createTextNode(mezzo.posX)) # aggiungo all'elemento un nodo di tipo testo contenente il valore della query
            elMezzo.appendChild(elPosX) # aggiungo l'elemento al mezzo
            
            elPosY = xmldoc.createElement("posY") # creo l'elemento "posY"
            elPosY.appendChild(xmldoc.createTextNode(mezzo.posY)) # aggiungo all'elemento un nodo di tipo testo contenente il valore della query
            elMezzo.appendChild(elPosY) # aggiungo l'elemento al mezzo
            
        rootMezzi.appendChild(elMezzo) # aggiungo l'oggetto "mezzo" all'oggetto radice

    def buildXMLcoord (self, xmldoc, coordX, coordY):
        rootPosizione = xmldoc.documentElement # ottengo la root "posizione" dal documento

        elCoordX = xmldoc.createElement("coordX") # creo l'elemento "coordX"
        elCoordX.appendChild(xmldoc.createTextNode(coordX)) # aggiungo all'elemento un nodo di tipo testo contenente la coordinata X
        rootPosizione.appendChild(elCoordX)

        elCoordY = xmldoc.createElement("coordY") # creo l'elemento "coordY"
        elCoordY.appendChild(xmldoc.createTextNode(coordY)) # aggiungo all'elemento un nodo di tipo testo contenente la coordinata Y
        rootPosizione.appendChild(elCoordY)

    def getDOMofCoord(self, coordX, coordY):
        DOMimpl = minidom.getDOMImplementation()
        xmldoc = DOMimpl.createDocument(None, "posizione", None)

        buildXMLcoord(self, xmldoc, coordX, coordY)

        return xmldocCoord

    def getCoordFromDOM(self, doc):
        try:
            posizione = doc.getElementsByTagName("posizione")[0]
            coordX = posizione.getElementsByTagName("coordX")[0].firstChild.nodeValue
            coordY = posizione.getElementsByTagName("coordY")[0].firstChild.nodeValue
            #print(coordX + ":" + coordY)
            return (coordX, coordY)         
        except IndexError:
            return False
        
        
        