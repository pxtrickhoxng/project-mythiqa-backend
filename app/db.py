import os
from psycopg2 import pool
from dotenv import load_dotenv
import psycopg2.extras

load_dotenv()
connection_string = os.getenv('DATABASE_URL')

connection_pool = pool.SimpleConnectionPool(
    1,
    10,
    connection_string
)

def get_user(user_id):
    conn = connection_pool.getconn()
    try:
        cur = conn.cursor(cursor_factory=psycopg2.extras.RealDictCursor)
        cur.execute('SELECT * FROM public."Users" WHERE user_id = %s', (user_id,))
        return cur.fetchone()
    finally:
        cur.close()
        connection_pool.putconn(conn)

