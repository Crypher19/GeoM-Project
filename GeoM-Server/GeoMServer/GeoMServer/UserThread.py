import threading
from SharedData import SharedData

class UserThread (threading.Thread):
    
    def __init__(self, sd, ID, conn, addr):
        threading.Thread.__init__(self)
        self.sd = sd;
        self.ID = ID
        self.conn = conn
        self.addr = addr
        
    def run(self):
        print("Connected by", self.addr)
        self.send("Connected")
        self.sd.getTransportList()
        #sd.readXMLTable("mezzi.xml")

    def send(self, mex):
        mex += '\n'
        self.conn.send(mex.encode('utf-8'))