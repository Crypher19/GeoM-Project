from mysql.connector import MySQLConnection, Error 


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

    def addTransport(self, TransportType, Name, Company): # da provare
        if(self.execQuery("INSERT INTO transports_table (TipoMezzo,Compagnia,NomeMezzo) VALUES ('" + TransportType + "','" + Company + "','" + Name + "')")!=False):
            conn.commit()
            return True
        return False
        
    def getTransports(self):
        ris = self.execQuery("SELECT TipoMezzo,Compagnia,NomeMezzo FROM transports_table")
        if(ris!=False):
            print("leggo mezzi")
            for(TipoMezzo,Compagnia,NomeMezzo) in cursor:
                print("mezzo: " + TipoMezzo)
            return True
        return False

    #def setPosXY(Company, Name):

    def getUser(self, user, password):
        if(execQuery("SELECT username,pass FROM transport_users_table WHERE username = '" + user + "' AND pass = '" + password + "');")):
            return True
        return False