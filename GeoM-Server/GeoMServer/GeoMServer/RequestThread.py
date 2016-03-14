import threading

class RequestThread (threading.Thread):
    
    def __init__(self, sd, ID, conn, addr):
        threading.Thread.__init__(self)
        self.sd = sd;
        self.ID = ID
        self.conn = conn
        self.addr = addr
        
    def run(self):
        print("Thread" + str(self.ID))
        print("Connected by", self.addr)

        #invia dati richiesti
        self.send("Connected")
        
    def send(self, mex):
        mex += '\n'
        self.conn.send(mex.encode('utf-8'))