#!/usr/bin/python           # This is client.py file

import socket               # Import socket module

s = socket.socket()         # Create a socket object
host = "localhost"          # Get local machine name
port = 3333                # Reserve a port for your service.

s.connect((host, port))
#print s.recv(1024)
s.send("transport".encode("utf-8"))
s.close            
