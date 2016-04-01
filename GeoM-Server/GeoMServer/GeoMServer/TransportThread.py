import threading

class TransportThread (threading.Thread):
    
    def __init__(self, sd, ID, conn, addr):
        threading.Thread.__init__(self)
        self.sd = sd
        self.ID = ID
        self.conn = conn
        self.addr = addr
        
    def run(self):
        print("Thread" + str(self.ID))
        print("Connected by", self.addr)

        #conferma connessione
        self.send("Connected")

        # ricevi username e password
        msg = self.conn.recv(1024).decode('utf-8').strip()
        print(msg)

        #if (self.sd.checkPassword(username, password)): ...
        #controlla username e password
        #ricevi - invia dati posizione
        

    def send(self, mex):
        mex += '\n'
        self.conn.send(mex.encode('utf-8'))