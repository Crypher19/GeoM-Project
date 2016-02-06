import socket
from UserThread import *

# variabili globali
s = None

def connection():
    global s
    HOST = ''
    PORT = 3333
    s = socket.socket()
    s.bind((HOST, PORT))
    s.listen(1) # accetta massimo una coda da <1> prima di rifiutare le altre connessioni
    print('Ready')

if __name__ == "__main__":
    connection()
    while True:
        conn, addr = s.accept()
        ut = UserThread(conn, addr)
        ut.start()