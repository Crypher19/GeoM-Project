import threading

class ThreadUser (threading.Thread):
    def send(mex):
        conn.send(mex + '\n'.encode('utf-8'))

    def __init__(self,ID,conn):
        self.conn = conn
        send("connected")
