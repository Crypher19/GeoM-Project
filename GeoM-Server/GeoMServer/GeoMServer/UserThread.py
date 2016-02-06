import threading

class UserThread (threading.Thread):

    def __init__(self, ID, conn, addr):
        threading.Thread.__init__(self)
        self.conn = conn
        self.addr = addr
        
    def run(self):
        print('Connected by', addr)
        send("Connected")

    def send(mex):
        conn.send(mex + '\n'.encode('utf-8'))

