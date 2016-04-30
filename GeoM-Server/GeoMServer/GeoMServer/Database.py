from mysql.connector import MySQLConnection, Error, RefreshOption 
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
            refresh = RefreshOption.LOG | RefreshOption.THREADS
            conn.cmd_refresh(refresh) # This method flushes tables or caches, or resets replication server information. 
            #print(query)
            ris = cursor.execute(query)
            return True
        except Error as error:
            #print("errore")
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
        
    def getTransports(self, tipoMezzo=None, limit=None, offset=None):
        sql = """SELECT transports_table.ID,transports_table.TipoMezzo,transports_table.NomeMezzo,
                        transports_table.Tratta,transports_table.Attivo,company_table.Nome
                     FROM transports_table, company_table
                     WHERE company_table.ID = transports_table.Compagnia"""
        if tipoMezzo != None:
            sql += " AND transports_table.TipoMezzo='"+tipoMezzo+"'"
        if offset != None and limit != None:
            sql += " LIMIT "+str(offset)+", "+str(limit)
        elif limit != None:
            sql += " LIMIT "+str(limit)

        ris = self.execQuery(sql)
        listResult = [] # Lista di Transport
        if ris != False:
            #print("leggo mezzi")
            for (ID,TipoMezzo,NomeMezzo,Tratta,Attivo,Compagnia) in cursor:
                listResult.append(Transport(ID, TipoMezzo, NomeMezzo, Tratta, Attivo, Compagnia))
                #print(listResult[-1].tipoMezzo + listResult[-1].nomeMezzo)
            return listResult
        return False

    def getUser(self, username):
        if self.execQuery("SELECT Username,Password FROM transport_users_table WHERE Username='"+username+"';"):
            res = cursor.fetchall()
            return (res[0], res[1])                      
        return False

    def getNumTransports(self, tipoMezzo=None):
        sql = "SELECT COUNT(*) FROM transports_table"

        if tipoMezzo != None:
            sql += " WHERE TipoMezzo='"+tipoMezzo+"'"

        if self.execQuery(sql):
            res = cursor.fetchall()
            return res[0] # restituisco il numero dei trasporti presenti nel DB
        return False