from mysql.connector import MySQLConnection, Error, RefreshOption 
from Transport import Transport


class Database:

    def __init__(self):       
        global cursor
        global conn
      
        try:
            print('Connecting to MySQL database...')
            conn = MySQLConnection(user='root', password='New9835', database='geom') # Connect to MySQL database
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
            ris = cursor.execute(query)
            return True
        except Error as error:
            print(error)
            return False

        
    def getTransports(self, IDcompagnia=None, tipoMezzo=None, limit=None, offset=None):
        sql = """SELECT transports_table.ID,transports_table.TipoMezzo,transports_table.NomeMezzo,
                        transports_table.Tratta,transports_table.Attivo,company_table.Nome
                     FROM transports_table, company_table
                     WHERE company_table.ID = transports_table.Compagnia"""

        if IDcompagnia != None:
            sql += " AND transports_table.Compagnia='"+str(IDcompagnia)+"'"
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
        if self.execQuery("SELECT Username,Password,Compagnia FROM transport_users_table WHERE Username='"+username+"';"):
            res = cursor.fetchall()
            print("len(res) = " + str(len(res)))
            if len(res) > 0:
                return (res[0][0], res[0][1], res[0][2])                      
        return False

    def getNumTransports(self, tipoMezzo=None):
        sql = "SELECT COUNT(*) FROM transports_table"

        if tipoMezzo != None:
            sql += " WHERE TipoMezzo='"+tipoMezzo+"'"

        if self.execQuery(sql):
            res = cursor.fetchall()
            return res[0] # restituisco il numero dei trasporti presenti nel DB
        return False

    def createUser(self, username, password):
            if(self.execQuery("INSERT INTO transport_users_table (Username,Password) VALUES ('" + username + "','" + password + "');")):
                return True
            return False

    def addTransport(self, TransportType, Name, Company, Route, Enabled):
        if(self.execQuery("INSERT INTO transports_table (TipoMezzo,NomeMezzo,Tratta,Attivo, Compagnia) VALUES ('" + TransportType + "','" + Name + "','" + Route + "','" + Enabled + "','" + Company+ "')")!=False):
            conn.commit()
            return True
        return False

    def setTransportAttivo(self, id, isAttivo):
        if (self.execQuery("UPDATE transports_table SET Attivo='"+isAttivo+"' WHERE ID="+id+";")):
            conn.commit()
            return True
        return False
