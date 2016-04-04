import threading
from xml.dom import minidom
from ParserXML import ParserXML
import time

class UserThread (threading.Thread):
    
    def __init__(self, sd, ID, conn, addr):
        threading.Thread.__init__(self)
        self.sd = sd
        self.ID = ID
        self.conn = conn
        self.addr = addr
        self.time = 5
        
    def run(self):
        print("Connected by", self.addr)
        self.send("Connected")

        msg = self.sd.getXMLTransportsList() # Creo lista mezzi     
        self.send(msg)       
        pxml = ParserXML()
        
        while True:
            #rimuovo timeout per ricezione mezzo
            self.conn.settimeout( None )

            # ricevo mezzo dell'utente
            msg = self.conn.recv(1024).decode('utf-8').strip()
            doc = pxml.toDOMObject(msg)
            tobj = pxml.getTransportObj(doc) # ottengo il mezzo del client
            posI = self.sd.getTransportI(tobj.nomeMezzo, tobj.compagnia, tobj.tratta)

            #imposto timeout per invio coordinate
            self.conn.settimeout( 1 )
            msg = "vuoto"
            while True :
                time.sleep(self.time)
                print("invio messaggio....")
                msg = self.conn.recv(1024).decode('utf-8').strip()
                print(msg);

            

    def send(self, mex):
        # se il messaggio è di tipo Document, prima lo trasformo in una stringa XML
        if isinstance(mex, minidom.Document):
            mex = mex.toxml()
            mex = mex.replace("\n", "")
        mex += "\r\n"
        self.conn.send(mex.encode('utf-8'))