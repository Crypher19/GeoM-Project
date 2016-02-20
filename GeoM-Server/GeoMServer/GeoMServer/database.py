from mysql.connector import MySQLConnection, Error 


class database(object):
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

    if __name__ == '__main__':
        
        connect()
        execQuery('INSERT INTO transports_table VALUES(0,test,test,test,test,false);')
