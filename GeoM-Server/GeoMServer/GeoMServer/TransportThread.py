import threading

class TransportThread (threading.Thread):
    
    def __init__(self, ID, conn, addr):
        threading.Thread.__init__(self)
        self.ID = ID
        self.conn = conn
        self.addr = addr
        
    def run(self):
        print("Thread" + str(self.ID))
        print("Connected by", self.addr)
        self.send("Connected")
        #sd.readXMLTable("mezzi.xml")

    def send(self, mex):
        mex += '\n'
        self.conn.send(mex.encode('utf-8'))

