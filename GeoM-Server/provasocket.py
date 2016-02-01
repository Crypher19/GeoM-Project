import socket

HOST = ''
PORT = 3333
s = socket.socket()
s.bind((HOST, PORT))
s.listen(1)
print('Pronto')
conn, addr = s.accept()
print('Connected by', addr)
conn.send('si\n'.encode('utf-8'))
