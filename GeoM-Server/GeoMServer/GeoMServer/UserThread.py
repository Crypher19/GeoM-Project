import threading
from xml.dom import minidom
from ParserXML import ParserXML

class UserThread (threading.Thread):
    
    def __init__(self, sd, ID, conn, addr):
        threading.Thread.__init__(self)
        self.sd = sd
        self.ID = ID
        self.conn = conn
        self.addr = addr
        
    def run(self):
        print("Connected by", self.addr)
        self.send("Connected")

        msg = self.sd.getXMLTransportsList()             
        self.send(msg)

        #ricevo mezzo dell'utente
        msg = self.conn.recv(1024).decode('utf-8').strip()
        pxml = ParserXML()
        doc = pxml.toDOMObject(msg)
        tobj = pxml.getTransportObj(doc) # ottengo il mezzo del client
        posI = self.sd.getTransportI(tobj.nomeMezzo, tobj.compagnia, tobj.tratta)
        
        # creo un oggetto di tipo DOM (Documento XML)
        DOMimpl = minidom.getDOMImplementation()
        xmldoc = DOMimpl.createDocument(None, "mezzi", None)

        pxml.buildXMLMezzo(xmldoc, self.sd.listaMezzi[posI])

        self.send(xmldoc)
        # self.sd.getTransportX(pos) + self.sd.getTransportY(pos)
        

    def send(self, mex):
        # se il messaggio è di tipo Document, prima lo trasformo in una stringa XML
        if isinstance(mex, minidom.Document):
            mex = mex.toxml()
            mex = mex.replace("\n", "")
        mex += "\r\n"
        self.conn.send(mex.encode('utf-8'))