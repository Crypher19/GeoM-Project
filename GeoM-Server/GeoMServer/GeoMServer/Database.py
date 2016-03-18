from mysql.connector import MySQLConnection, Error 
from QueryResult import *


class Database(object):

    def __init__(self):

        """ Connect to MySQL database """
        global cursor
        global conn
      
        try:
            print('Connecting to MySQL database...')
            conn = MySQLConnection(user='root', database='geom')
            cursor = conn.cursor()
            
            if conn.is_connected():
                print('connection established.')
            else:
                print('connection failed.')
     
        except Error as error:
            print(error)

    def execQuery(self,query):
        """Execute query & give result"""
        
        try:
            ris = True
            print(query)
            ris = cursor.execute(query)
            return ris

        except Error as error:
            print("errore")
            print(error)
            return False

    def createUser(self, user, password): # da provare
            if(execQuery("insert into transport_users_table (username,pass) VALUES ('" + user + "','" + password + "');")):
                return True
            return False

    def addTransport(self, TransportType, Name, Company, Route): # da provare
        if(self.execQuery("INSERT INTO transports_table (TipoMezzo,Compagnia,NomeMezzo,tratta) VALUES ('" + TransportType + "','" + Company + "','" + Name + "','" + Route + "')")!=False):
            conn.commit()
            return True
        return False
        
    def getTransports(self):
        ris = self.execQuery("SELECT TipoMezzo,Compagnia,NomeMezzo,Tratta FROM transports_table")
        listResult = list()
        if ris != False:
            print("leggo mezzi")
            for (TipoMezzo,Compagnia,NomeMezzo,Tratta) in cursor:
                listResult.append(QueryResult(TipoMezzo, Compagnia, Tratta, NomeMezzo))
                print(listResult[-1].tipoMezzo + listResult[-1].nome)
            return listResult
        return False

    #def setPosXY(Company, Name):

    def getUser(self, user, password):
        if execQuery("SELECT username,pass FROM transport_users_table WHERE username = '" + user + "' AND pass = '" + password + "');"):
            return True
        return False