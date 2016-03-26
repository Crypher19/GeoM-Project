from xml.dom import minidom
from QueryResult import QueryResult

class ParserXML:
    def toDOMObject(self, string):        
        return minidom.parseString(string) # ritorna oggetto tipo doc

    def getDOMOfTransportsList(self, listaMezzi):      
        DOMimpl = minidom.getDOMImplementation()

        xmldoc = DOMimpl.createDocument(None, "mezzi", None)
        rootMezzi = xmldoc.documentElement

        for mezzo in listaMezzi:
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
            
            rootMezzi.appendChild(elMezzo)  # aggiungo l'oggetto "mezzo" all'oggetto radice

        print(xmldoc.toprettyxml())  
        return xmldoc