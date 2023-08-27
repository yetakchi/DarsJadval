from dotenv import dotenv_values

env = dict(dotenv_values())
SERVER_HOST = env['SERVER_HOST']
SERVER_PORT = env['SERVER_PORT']

DB_HOST = env['DB_HOST']
DB_USER = env['DB_USERNAME']
DB_PASSWORD = env['DB_PASSWORD']
DB_NAME = env['DB_NAME']
