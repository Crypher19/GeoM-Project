from mysql.connector import MySQLConnection, Error 


class pydb:
    def connect():
        """ Connect to MySQL database """
        global query
      
        try:
            print('Connecting to MySQL database...')
            conn = MySQLConnection(user = 'root', database='test')
            query = conn.cursor()
            
            if conn.is_connected():
                print('connection established.')
            else:
                print('connection failed.')
     
        except Error as error:
            print(error)
     
        finally:
            conn.close()
            print('Connection closed.')

    def addTransport():
        """add new transport"""
        try:
            print(query)
            query.execute('INSERT INTO transports_table VALUES(0,test,test,test,test,false);')
            print("query executed")
            return True

        except Error as error:
            print(error)
            return False


    if __name__ == '__main__':
        
        connect()
        addTransport()
