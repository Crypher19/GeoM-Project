from mysql.connector import MySQLConnection, Error 


class Database(object):
    def connect():
        """ Connect to MySQL database """
        global cursor
        global conn
      
        try:
            print('Connecting to MySQL database...')
            conn = MySQLConnection(user='root', database='Geom')
            cursor = conn.cursor()
            
            if conn.is_connected():
                print('connection established.')
            else:
                print('connection failed.')
     
        except Error as error:
            print(error)


    def execQuery(query):
        """Execute query & give result"""
        try:
            print(query)
            ris = cursor.execute(query)
            return ris

        except Error as error:
            print(error)
            return False

    def createUser(self,user,password): # da provare
            if(execQuery("insert into transport_users_table (username,pass) VALUES ('" + user + "','" + password + "')")):
                return true
            return false

    def addTransport(self,TransportType,Name,Company): # da provare
            if(execQuery("insert into transports_table (TipoMezzo,Compagnia,NomeMezzo) VALUES ('" + TransportType + "','" + Company + "','" + Name + "')")):
                return true
            return false

    if __name__ == '__main__':
        
        connect()
        execQuery('INSERT INTO transports_table VALUES(0,test,test,test,test,false);')

    
