import threading
from xml.dom import minidom
from SharedData import *
from UserThread import *
from TransportThread import *

class ThreadSort(threading.Thread):

    def __init__(self, ID, conn, addr):
        threading.Thread.__init__(self)
        self.ID = ID
        self.conn = conn
        self.addr = addr
        global sd
        sd = SharedData()

    def run(self):
        # da provare
        
        # Controlla tipo di thread
        msg = self.conn.recv(1024).decode("utf-8").strip()
        print(msg)
        if(msg=="user"):
            print("utente connesso")
            ut = UserThread(self.ID,self.conn,self.addr)
            ut.start();
        else:
            print("trasporto connesso")
            tt = TransportThread(self.ID,self.conn,self.addr)
            tt.start();
        
