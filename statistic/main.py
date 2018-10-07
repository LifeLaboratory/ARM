# coding=utf-8
import sys
import os
sys.path.append(os.getcwd()+'/../')
sys.path.append(os.getcwd()+'../')
import flask
import logging
from flask_restful import Api
from statistic.route.route_list import ROUTES

# logging.basicConfig(filename='logger.log',
#                         format='%(filename)-12s[LINE:%(lineno)d] %(levelname)-8s %(message)s %(asctime)s ',
#                         level=logging.DEBUG)

_app = flask.Flask(__name__)
_app.config['JSON_AS_ASCII'] = False
api = Api(_app)
HEADER = {'Access-Control-Allow-Origin': '*'}


@_app.errorhandler(404)
def not_found(error):
    return {'error': 'Not found'}, 404


if __name__ == '__main__':
    try:
        for route_class, route in ROUTES.items():
            api.add_resource(route_class, route)
        _app.run(host='0.0.0.0', port=13452, threaded=True)
    except Exception as e:
        print('Main except = ', e)
