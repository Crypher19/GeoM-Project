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
        self.mezzo = None

    def run(self):
        try:
            print("Thread" + str(self.ID))
            print("Connected by", self.addr)

            # conferma connessione
            pxml = ParserXML()
            ack = pxml.getDOMResponse() # messaggio di default con "OK"
            self.send(ack)
            #self.send(ack)

            # ricevi username e password
            msg = self.conn.recv(1024).decode('utf-8').strip()

            userdoc = pxml.toDOMObject(msg)

            auth = pxml.getUsernameAndPassword(userdoc) # ottengo una tupla contenente username e password
            # controllo username e password
            ris = self.sd.checkLogin(auth[0], auth[1])

            # se username e password non sono errati
            if  ris != -1 and ris != -2:
                #invio ack password corretta
                self.send(ack)

                # invio lista dei mezzi
                msg = self.sd.getDOMTransportsList(IDCompagnia=ris, mezzoAttivo="false")
                self.send(msg)

                # ricevo il mezzo dell'autista
                msg = self.conn.recv(1024).decode('utf-8').strip()
                print(msg)
                doc = pxml.toDOMObject(msg)
                self.mezzo = pxml.getTransportObj(doc)
                posI = self.sd.getTransportI(self.mezzo.nomeMezzo, self.mezzo.compagnia, self.mezzo.tratta) # cerco il mezzo nella lista dei mezzi già attivi
            
                # se il mezzo è già attivo
                if posI == -1:
                    self.send(pxml.getDOMResponse(msg="NOTOK"))
                    print("Mezzo di trasporto gia' attivo")
                # se il mezzo non è ancora attivo
                else:
                    self.send(ack) # invio conferma di ricezione del mezzo           

                    # abilito il mezzo nel DB
                    self.sd.enableTransport(self.mezzo.ID)
                
                    # ricevo posizione (X e Y)
                    while pxml.readDOMResponse(doc, "messaggio") != "END":
                        msg = self.conn.recv(1024).decode('utf-8').strip()
                        #print(msg)
                        doc = pxml.toDOMObject(msg)
                        pos = pxml.getCoordFromDOM(doc)
                        if pos != False:
                            self.coordX,self.coordY = pos[0],pos[1]
                            print("RICEVO: " + self.coordX + " ; " + self.coordY)  
                
            # username errato
            elif ris == -2:
                self.send(pxml.getDOMResponse(msg="-2"))
            # password errata
            elif ris == -1:
                self.send(pxml.getDOMResponse(msg="-1"))


            # fine del programma
        except ConnectionResetError:
            print("socked closed by client")

        # cancello il trasporto dalla lista e lo disattivo nel DB
        self.sd.disableTransport(self.mezzo.ID)
        self.sd.delTransport(self.index)
        #print("transport deleted")

    def send(self, mex):
        # se il messaggio è di tipo Document, prima lo trasformo in una stringa XML
        if isinstance(mex, minidom.Document):
            mex = mex.toxml()
            mex = mex.replace("\n", "")
        mex += "\r\n"
        self.conn.send(mex.encode('utf-8'))