import socket
from SharedData import SharedData
from UserThread import UserThread
from ThreadSort import ThreadSort

def connection():
    global s
    HOST = ''
    PORT = 3333
    s = socket.socket()
    s.bind((HOST, PORT))
    s.listen(1) # accetta massimo una coda da <1> prima di rifiutare le altre connessioni
   

if __name__ == "__main__":
    connection()
    ID = 0
    sd = SharedData()
    print('Ready')
    while True:
        conn, addr = s.accept()
        print("connessione ricevuta")
        ID += 1
        ut = ThreadSort(sd, ID, conn, addr)
        #avvio thread con: controllo tipo thread, autenticazione, aggiunta thread alla lista [HREAD SMISTATORE]
        ut.start()
