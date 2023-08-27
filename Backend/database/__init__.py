import mysql.connector

from config import DB_HOST, DB_USER, DB_PASSWORD, DB_NAME


class MYSQLConnector:
    def __init__(self):
        self.connection = None
        self.cursor = None

    def connect(self):
        try:
            self.connection = mysql.connector.connect(
                host=DB_HOST,
                user=DB_USER,
                password=DB_PASSWORD,
                database=DB_NAME
            )
            self.cursor = self.connection.cursor(dictionary=True)  # Set dictionary
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

        self.cursor.execute(query)
        res = self.cursor.fetchall()
        self.disconnect()

        return res

    def select_one(self, query):
        if not self.connect():
            return

        self.cursor.execute(query)
        res = self.cursor.fetchone()
        self.disconnect()

        return res

    def select_from_table(self, table):
        return self.select(f"SELECT * FROM {table};")

    def insert(self, query, args=()):
        self.connect()
        self.cursor.execute(query, args)
        self.connection.commit()
        self.disconnect()
        return args

    def update_in(self, query, args=()):
        self.connect()
        self.cursor.execute(query, args)
        self.connection.commit()
        self.disconnect()

        return args

    def delete_from(self, table, idx):
        if not self.connect():
            return

        self.cursor.execute(f"DELETE FROM {table} WHERE id = {idx}")
        self.connection.commit()
        self.disconnect()
