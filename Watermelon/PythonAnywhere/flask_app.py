
# A very simple Flask Hello World app for you to get started with...

from flask import Flask, jsonify
from pymongo import MongoClient
from bson.json_util import dumps

app = Flask(__name__)

client = MongoClient('mongodb://user:pass@ds021694.mlab.com:21694/test_watermelon', 27017)
database = client['test_watermelon']

@app.route('/', methods=['GET'])
def get_tasks():
    return jsonify(dumps(database.cadastro.find()))

json = ({ 'nome' : 'NOME1' })

@app.route('/hello')
def hello_world():
    return dumps(json)



if __name__ == '__main__':
    app.run(debug = True)


