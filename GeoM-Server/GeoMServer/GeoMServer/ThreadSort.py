import threading
from xml.dom import minidom
from SharedData import SharedData
from ParserXML import ParserXML
from UserThread import UserThread
from TransportThread import TransportThread

class ThreadSort(threading.Thread):

    def __init__(self, sd, ID, conn, addr):
        threading.Thread.__init__(self)
        self.sd = sd;
        self.ID = ID
        self.conn = conn
        self.addr = addr
        

    def run(self):
        # da provare
        
        # Controlla tipo di thread
        msg = self.conn.recv(1024).decode('utf-8').strip()
        print(msg)
        pxml = ParserXML()
        messaggio = pxml.toDOMObject(msg) # converto il messaggio(xml) in un oggetto di tipo DOM
        tipo = messaggio.getElementsByTagName("tipo")[0] # ottengo il nodo del primo elemento "tipo"
        msg = tipo.firstChild.nodeValue # ottengo il valore dell'elemento tipo

        if(msg == "user"):
            print("utente connesso")
            ut = UserThread(self.sd, self.ID, self.conn, self.addr)
            ut.start()
        elif(msg == "transport"):
            print("trasporto connesso")
            tt = TransportThread(self.sd, self.ID, self.conn, self.addr)
            self.sd.addTransport(tt)