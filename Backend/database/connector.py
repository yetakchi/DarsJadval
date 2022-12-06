import mysql.connector
from dotenv import dotenv_values

env = dict(dotenv_values())


class MYSQLConnector:
    def __init__(self):
        self.connection = None
        self.db = None

    def connect(self):
        try:
            self.connection = mysql.connector.connect(
                host=env['DB_HOST'],
                user=env['DB_USERNAME'],
                password=env['DB_PASSWORD'],
                database=env['DB_DATABASE']
            )
            self.db = self.connection.cursor(dictionary=True)  # Set dictionary
            return True
        except ConnectionError:
            return False

    def disconnect(self):
        if self.connection:
            self.connection.disconnect()

    # Functions
    def select(self, query):
        if not self.connect():
            return

        self.db.execute(query)
        res = self.db.fetchall()
        self.disconnect()

        return res

    def select_one(self, query):
        if not self.connect():
            return

        self.db.execute(query)
        res = self.db.fetchone()
        self.disconnect()

        return res

    def select_table(self, table):
        return self.select(f"SELECT * FROM {table};")

    def insert(self, query, args=(), reply='/'):
        if not self.connect():
            return ''

        self.db.execute(query, args)
        self.connection.commit()
        self.disconnect()

        return reply

    def delete(self, table, n):
        if not self.connect():
            return ''

        self.db.execute(f"DELETE FROM {table} WHERE id = {n}")
        self.connection.commit()
        self.disconnect()

        return '/' + table

    def update(self, query, args=(), reply='/'):
        if not self.connect():
            return ''

        self.db.execute(query, args)
        self.connection.commit()
        self.disconnect()

        return reply
