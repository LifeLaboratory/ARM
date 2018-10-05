# coding=utf-8
from auth.api.helpers.service import Gis as gs
from flask_restful import Resource, reqparse
from auth.api.src.ChoiceRegistration import choice

class Authentication(Resource):
    def __init__(self):
        self.__parser = reqparse.RequestParser()
        self.__parser.add_argument('data')
        self.__args = self.__parser.parse_args()
        self.data = None

    def parse_data(self):
        self.data = self.__args.get('data', None)
        self.data = gs.converter(self.data)
        return self.data
    def put(self):
        data = self.parse_data()
        answer = choice(data)
        print(answer)
        return answer, 200, {'Access-Control-Allow-Origin': '*'}

