import threading
from xml.dom import minidom
from ParserXML import ParserXML
from Transport import Transport

class TransportThread (threading.Thread):
    
    def __init__(self, sd, ID, conn, addr, index):
        threading.Thread.__init__(self)
        self.sd = sd
        self.ID = ID
        self.conn = conn
        self.addr = addr
        self.index = index

    def run(self):
        try:
            print("Thread" + str(self.ID))
            print("Connected by", self.addr)

            # conferma connessione
            pxml = ParserXML()
            ack = pxml.getDOMResponse()
            self.send(ack)
            #self.send(ack)

            # ricevi username e password
            msg = self.conn.recv(1024).decode('utf-8').strip()

            userdoc = pxml.toDOMObject(msg)

            auth = pxml.getUsernameAndPassword(userdoc) # ottengo una tupla contenente username e password
            # controllo username e password
            if self.sd.checkLogin(auth[0], auth[1]):
                #invio ack password corretta
                self.send(ack)

                # invio lista dei mezzi
                msg = self.sd.getDOMTransportsList()
                self.send(msg)

                # ricevo il mezzo dell'autista
                msg = self.conn.recv(1024).decode('utf-8').strip()
                #print(msg)
                doc = pxml.toDOMObject(msg)
                self.mezzo = pxml.getTransportObj(doc)

                # invio conferma di ricezione del mezzo           
                self.send(ack)
            
                # ricevo posizione di prova
                while pxml.readDOMResponse(doc, "messaggio") != "END":
                    msg = self.conn.recv(1024).decode('utf-8').strip()
                    doc = pxml.toDOMObject(msg)
                    pos = pxml.getCoordFromDOM(doc)
                    if pos != False:
                        self.coordX,self.coordY = pos[0],pos[1]     
                       

                # ricevi dati posizione (for/while)
            else:
                self.send(getDOMResponse(msg="Username o password errata"))

            # fine del programma
        except ConnectionResetError:
            print("socked closed by client")
        self.sd.delTransport(self.index)
        #print("transport deleted")

    def send(self, mex):
        # se il messaggio è di tipo Document, prima lo trasformo in una stringa XML
        if isinstance(mex, minidom.Document):
            mex = mex.toxml()
            mex = mex.replace("\n", "")
        mex += "\r\n"
        self.conn.send(mex.encode('utf-8'))