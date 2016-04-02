from xml.dom import minidom
from Transport import Transport

class ParserXML:
    def toDOMObject(self, string):        
        return minidom.parseString(string) # ritorna oggetto tipo document

    def getTransportObj(self, doc):
        mezzi = doc.getElementsByTagName("mezzi")[0].firstChild.nodeValue
        mezzo = mezzi.getElementsByTagName("mezzo")[0].firstChild.nodeValue
        id = mezzo.getAttribute("id")
        tipo = mezzo.getElementsByTagName("tipo")[0].firstChild.nodeValue
        compagnia = mezzo.getElementsByTagName("compagnia")[0].firstChild.nodeValue
        nome = mezzo.getElementsByTagName("nome")[0].firstChild.nodeValue
        tratta = mezzo.getElementsByTagName("tratta")[0].nodeValue.firstChild.nodeValue
        attivo = mezzo.getElementsByTagName("attivo")[0].firstChild.nodeValue
        return Transport(id, tipo, compagnia, nome, tratta, attivo)

    def getUsernameAndPassword(self, doc):
        autenticazione = doc.getElementsByTagName("autenticazione")[0]
        username = autenticazione.getElementsByTagName("username")[0].firstChild.nodeValue
        password = autenticazione.getElementsByTagName("password")[0].firstChild.nodeValue
        print(username + ":" + password)
        return (username, password)

    def getDOMOfTransportsList(self, listaMezzi):      
        DOMimpl = minidom.getDOMImplementation()
        xmldoc = DOMimpl.createDocument(None, "mezzi", None)
       
        for mezzo in listaMezzi:
            self.buildXMLMezzo(xmldoc, mezzo)

        #print(xmldoc.toprettyxml())  
        return xmldoc

    def buildXMLMezzo(self, xmldoc, mezzo):
        rootMezzi = xmldoc.documentElement

        elMezzo = xmldoc.createElement("mezzo") # creo l'elemento "mezzo"
        elMezzo.setAttribute("id", str(mezzo.ID)) # imposto l'attributo "id"
            
        elTipo = xmldoc.createElement("tipo") # creo l'elemento "tipo"
        elTipo.appendChild(xmldoc.createTextNode(mezzo.tipoMezzo)) # aggiungo all'elemento un nodo di tipo testo contenente il valore della query
        elMezzo.appendChild(elTipo) # aggiungo l'elemento al mezzo

        elCompagnia = xmldoc.createElement("compagnia") # creo l'elemento "compagnia"
        elCompagnia.appendChild(xmldoc.createTextNode(mezzo.compagnia)) # aggiungo all'elemento un nodo di tipo testo contenente il valore della query
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