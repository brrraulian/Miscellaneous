"""
This script runs the application using a development server.
It contains the definition of routes and views for the application.
"""

from flask import Flask, jsonify
from pymongo import MongoClient
from bson.json_util import dumps

app = Flask(__name__)

client = MongoClient('mongodb://user:pass@ds021694.mlab.com:21694/test_watermelon', 27017)
database = client['test_watermelon']

@app.route('/', methods=['GET'])
def get_tasks():
    return jsonify(dumps(database.cadastro.find()))

if __name__ == '__main__':
    import os
    HOST = os.environ.get('SERVER_HOST', 'localhost')
    try:
        PORT = int(os.environ.get('SERVER_PORT', '5555'))
    except ValueError:
        PORT = 5555
    app.run(HOST, PORT)
