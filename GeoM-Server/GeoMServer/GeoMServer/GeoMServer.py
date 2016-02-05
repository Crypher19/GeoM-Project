import socket

global s

def connection():
    global s
    HOST=''
    PORT=3333
    s = socket.socket()
    s.bind((HOST, PORT))
    s.listen(1)
    print('Pronto')

if __name__ == "__main__":
    global s
    connection()
    conn, addr = s.accept()
    print('Connected by', addr)
