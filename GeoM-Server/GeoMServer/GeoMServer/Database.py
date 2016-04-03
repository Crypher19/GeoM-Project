from mysql.connector import MySQLConnection, Error 
from Transport import Transport


class Database:

    def __init__(self):
        # Connect to MySQL database
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
        # Execute query & give result    
        try:
            print(query)
            ris = cursor.execute(query)
            return True
        except Error as error:
            print("errore")
            print(error)
            return False

    def createUser(self, username, password): # da provare
            if(self.execQuery("INSERT INTO transport_users_table (Username,Password) VALUES ('" + username + "','" + password + "');")):
                return True
            return False

    def addTransport(self, TransportType, Name, Company, Route, Enabled): # da provare
        if(self.execQuery("INSERT INTO transports_table (TipoMezzo,NomeMezzo,Tratta,Attivo, Compagnia) VALUES ('" + TransportType + "','" + Name + "','" + Route + "','" + Enabled + "','" + Company+ "')")!=False):
            conn.commit()
            return True
        return False
        
    def getTransports(self):
        ris = self.execQuery("SELECT ID,TipoMezzo,NomeMezzo,Tratta,Attivo,Compagnia FROM transports_table")
        listResult = [] # Lista di Transport
        if ris != False:
            print("leggo mezzi")
            for (ID,TipoMezzo,NomeMezzo,Tratta,Attivo,Compagnia) in cursor:
                listResult.append(Transport(ID, TipoMezzo, NomeMezzo, Tratta, Attivo, Compagnia))
                #print(listResult[-1].tipoMezzo + listResult[-1].nome)
            return listResult
        return False

    #def setPosXY(Company, Name):

    def getUser(self, username):
        if self.execQuery("SELECT Username,Password FROM transport_users_table WHERE Username='"+username+"';"):
            for u, p in cursor:
                return (u, p)            
        return False