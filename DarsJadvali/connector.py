import mysql.connector


class MYSQLConnector:
    def __init__(self):
        self.connection = None
        self.db = None

    def Connect(self):
        try:
            self.connection = mysql.connector.connect(
                host="127.0.0.1",
                user="root",
                password='yashirin',
                database="schedule"
            )
            self.db = self.connection.cursor(dictionary=True)  # Set dictionary
            return True
        except ConnectionError:
            return False

    def Disconnect(self):
        if self.connection:
            self.connection.disconnect()

    # Functions
    def Select(self, query):
        if not self.Connect():
            return

        self.db.execute(query)
        res = self.db.fetchall()
        self.Disconnect()

        return res

    def selectOne(self, query):
        if not self.Connect():
            return

        self.db.execute(query)
        res = self.db.fetchone()
        self.Disconnect()

        return res

    def SelectTable(self, table):
        return self.Select(f"SELECT * FROM {table};")

    def Insert(self, query, args=(), reply='/'):
        if not self.Connect():
            return ''

        self.db.execute(query, args)
        self.connection.commit()
        self.Disconnect()

        return reply

    def Delete(self, table, n):
        if not self.Connect():
            return ''

        self.db.execute(f"DELETE FROM {table} WHERE id = {n}")
        self.connection.commit()
        self.Disconnect()

        return '/' + table

    def Update(self, query, args=(), reply='/'):
        if not self.Connect():
            return ''

        self.db.execute(query, args)
        self.connection.commit()
        self.Disconnect()

        return reply
