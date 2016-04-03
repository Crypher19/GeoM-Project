import threading
from xml.dom import minidom
from ParserXML import ParserXML

class TransportThread (threading.Thread):
    
    def __init__(self, sd, ID, conn, addr):
        threading.Thread.__init__(self)
        self.sd = sd
        self.ID = ID
        self.conn = conn
        self.addr = addr
        
    def run(self):
        print("Thread" + str(self.ID))
        print("Connected by", self.addr)

        # conferma connessione
        pxml = ParserXML()
        ack = pxml.getDOMConferma()
        self.send("Connected")
        #self.send(ack)

        # ricevi username e password
        msg = self.conn.recv(1024).decode('utf-8').strip()
        print(msg)

        userdoc = pxml.toDOMObject(msg)

        auth = pxml.getUsernameAndPassword(userdoc) # ottengo una tupla contenente username e password
        # controllo username e password
        if self.sd.checkLogin(auth[0], auth[1]):
            # ricevo il mezzo 
            msg = self.conn.recv(1024).decode('utf-8').strip()
            print(msg)
            mezzodoc = pxml.toDOMObject(msg)   
               
            # invio conferma di ricezione del mezzo           
            self.send(ack)
            
            # ricevo posizione di prova
            msg = self.conn.recv(1024).decode('utf-8').strip()
            print(msg)                

            #ricevi - invia dati posizione (for/while)
            

    def send(self, mex):
        # se il messaggio è di tipo Document, prima lo trasformo in una stringa XML
        if isinstance(mex, minidom.Document):
            mex = mex.toxml()
            mex = mex.replace("\n", "")
        mex += "\r\n"
        self.conn.send(mex.encode('utf-8'))