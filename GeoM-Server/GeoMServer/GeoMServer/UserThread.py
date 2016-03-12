import threading
from SharedData import *

class UserThread (threading.Thread):
    
    def __init__(self, sd, ID, conn, addr):
        threading.Thread.__init__(self)
        self.sd = sd;
        self.ID = ID
        self.conn = conn
        self.addr = addr
        
    def run(self):
        print("Thread" + str(self.ID) + sd.prova)
        print("Thread" + str(self.ID) + sd.prova)
        print("Connected by", self.addr)
        self.send("Connected")
        #sd.readXMLTable("mezzi.xml")

    def send(self, mex):
        mex += '\n'
        self.conn.send(mex.encode('utf-8'))