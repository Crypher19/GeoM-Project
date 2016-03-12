import threading
from xml.dom import minidom
from SharedData import *
from UserThread import *
from TransportThread import *

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
        self.sd.toDOCObject(msg)

        if(msg == "user"):
            print("utente connesso")
            ut = UserThread(self.sd, self.ID,self.conn,self.addr)
            ut.start();
        else:
            print("trasporto connesso")
            tt = TransportThread(self.sd, self.ID, self.conn, self.addr)
            self.sd.addTransport(tt)
