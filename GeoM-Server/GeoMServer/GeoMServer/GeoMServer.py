import socket
from UserThread import *

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
    ID = 0
    while True:
        conn, addr = s.accept()
        ID += 1
        ut = UserThread(ID, conn, addr)
        ut.start()