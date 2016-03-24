import threading
#from SharedData import SharedData
from ParserXML import ParserXML

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
        pxml = ParserXML()
        doc = pxml.getDOMOfTransportsList("..\mezzi.xml")
        msg = doc.toxml().replace("\n", "")
        print(msg)
        self.send(msg)

    def send(self, mex):
        mex += "\r\n"
        self.conn.send(mex.encode('utf-8'))