import threading
from xml.dom import minidom
from ParserXML import ParserXML
import time
import socket

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
        
        while True: # TODO: esco quando il client si disconnette
            # rimuovo timeout per ricezione mezzo
            self.conn.settimeout( None ) 

            # ricevo mezzo dell'utente
            msg = self.conn.recv(1024).decode('utf-8').strip()
            print(msg)
            doc = pxml.toDOMObject(msg)
            tobj = pxml.getTransportObj(doc) # ottengo il mezzo del client
            posI = self.sd.getTransportI(tobj.nomeMezzo, tobj.compagnia, tobj.tratta)
            
            #invio risposta se il mezzo è attivo
            loop = False
            if posI == -1:
                self.send(pxml.getDOMResponse(msg="Mezzo di trasporto non attivo"))
                print("mezzo di trasporto NON trovato")
            else:
                self.send(pxml.getDOMResponse())
                print("mezzo di trasporto trovato")
                loop = True

            #imposto timeout per invio coordinate
            self.conn.settimeout( 1 )
            
            while loop :
                time.sleep(self.time)
                print("invio messaggio....") 
                # lettura posizioni xy del mezzo interessato
                #print(self.sd.transportList[posI].posX)
                #print(self.sd.transportList[posI].posY)
                # TODO: invio posizioni X-Y al client

                try: # provo a ricevere un messaggio
                    msg = self.conn.recv(1024).decode('utf-8').strip()
                    loop = False
                    #print(type())

                except socket.timeout: # ricevo timeout dalla socket
                    print("Timeout")
                

    def send(self, mex):
        # se il messaggio è di tipo Document, prima lo trasformo in una stringa XML
        if isinstance(mex, minidom.Document):
            mex = mex.toxml()
            mex = mex.replace("\n", "")
        mex += "\r\n"
        self.conn.send(mex.encode('utf-8'))