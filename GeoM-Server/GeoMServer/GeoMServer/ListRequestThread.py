import threading
from xml.dom import minidom
from ParserXML import ParserXML
import time
import socket

class ListRequestThread(threading.Thread):
    
    def __init__(self, sd, ID, conn, addr):
        threading.Thread.__init__(self)
        self.sd = sd
        self.ID = ID
        self.conn = conn
        self.addr = addr
        self.time = 5
        
    def run(self):
        try:
            print("Connected by", self.addr)
            self.send("Connected")

            msg = self.sd.getXMLTransportsList() # Creo lista mezzi     
            self.send(msg)       
            self.conn.close()
        except ConnectionResetError:
            print("socked closed by client")
                

    def send(self, mex):
        # se il messaggio è di tipo Document, prima lo trasformo in una stringa XML
        if isinstance(mex, minidom.Document):
            mex = mex.toxml()
            mex = mex.replace("\n", "")
        mex += "\r\n"
        self.conn.send(mex.encode('utf-8'))