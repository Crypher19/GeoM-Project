import threading

class UserThread (threading.Thread):
    
    def __init__(self, sd, ID, conn, addr):
        threading.Thread.__init__(self)
        self.sd = sd
        self.ID = ID
        self.conn = conn
        self.addr = addr
        
    def run(self):
        print("Connected by", self.addr)
        self.send("Connected")

        msg = self.sd.getXMLTransportsList()
               
        print(msg)
        self.send(msg)

    def send(self, mex):
        mex += "\r\n"
        self.conn.send(mex.encode('utf-8'))