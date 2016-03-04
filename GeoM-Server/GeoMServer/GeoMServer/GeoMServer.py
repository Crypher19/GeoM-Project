import socket
from UserThread import *
from ThreadSort import *

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
        print("connessione ricevuta")
        ID += 1
        ut = ThreadSort(ID, conn, addr)
        #avvio thread con: controllo tipo thread, autenticazione, aggiunta thread alla lista [HREAD SMISTATORE]
        ut.start()
