import threading
from SharedData import *
from UserThread import *

class ThreadSort(threading.Thread):

    def __init__(self, ID, conn, addr):
        threading.Thread.__init__(self)
        self.ID = ID
        self.conn = conn
        self.addr = addr

    def run(self):
        # da provare
        # Controlla tipo di thread
        ut = UserThread(self.ID, self.conn, self.addr)
        ut.start()
