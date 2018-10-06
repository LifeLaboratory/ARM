# coding=utf-8
from auth.api.helpers.service import Gis as gs
from flask_restful import Resource, reqparse
from auth.api.src.ChoiceRegistration import choice
from auth.api.src.Authentication import auth
from auth.api.src.Operators import listOperators
from auth.api.src.sessionToId import convert

class Authentication(Resource):
    def __init__(self):
        self.__parser = reqparse.RequestParser()
        self.__parser.add_argument('data')
        self.__parser.add_argument('id_company')
        self.__args = self.__parser.parse_args()
        self.data = None
        self.id_company = None

    def parse_data(self):
        self.data = self.__args.get('data', None)
        self.data = gs.converter(self.data)
        return self.data

    def put(self):
        data = self.parse_data()
        condata = convert(data)
        print("condata", condata)
        answer = choice(data)
        return answer, 200, {'Access-Control-Allow-Origin': '*'}

    def post(self):
        data = self.parse_data()
        answer = auth(data)
        return answer, 200, {'Access-Control-Allow-Origin': '*'}

    def get(self):
        self.id_company = self.__args.get('id_company', None)
        print(self.id_company)
        answer = listOperators(self.id_company)
        return answer, 200, {'Access-Control-Allow-Origin': '*'}