# coding=utf-8
from auth.api.helpers.service import Gis as gs
from flask_restful import Resource, reqparse
from auth.api.src.ChoiceRegistration import choice
from auth.api.src.Authentication import auth
from auth.api.src.Operators import listOperators
from auth.api.src.sessionToId import convert
import auth.api.helpers.base_name as names
from auth.api.src.ProfileOperator import profile

class Authentication(Resource):
    def __init__(self):
        self.__parser = reqparse.RequestParser()
        self.__parser.add_argument('data')
        self.__parser.add_argument('Session')
        self.__args = self.__parser.parse_args()
        self.data = None
        self.session = None

    def parse_data(self):
        self.data = self.__args.get('data', None)
        self.data = gs.converter(self.data)
        return self.data

    def selectid(self, data):
        if data.get(names.SESSION, None) is not None:
            condata = convert(data)
            if condata[names.DATA].get(names.ID_USER, None) is None and condata[names.DATA].get(names.ID_COMPANY,
                                                                                                None) is not None:
                data[names.ID_COMPANY] = condata[names.DATA][names.ID_COMPANY]
            elif condata[names.DATA].get(names.ID_USER, None) is not None and condata[names.DATA].get(names.ID_COMPANY,
                                                                                                      None) is not None:
                data[names.ID_COMPANY] = condata[names.DATA][names.ID_COMPANY]
                data[names.ID_USER] = condata[names.DATA][names.ID_USER]
            print("DATA", data)
        return data

    def put(self):
        data = self.parse_data()
        condata = self.selectid(data)
        answer = choice(condata)
        return answer, 200, {'Access-Control-Allow-Origin': '*'}

    def post(self):
        data = self.parse_data()
        answer = auth(data)
        return answer, 200, {'Access-Control-Allow-Origin': '*'}

    def get(self):
        self.session = self.__args.get('Session', None)
        data = dict()
        data[names.SESSION] = self.session
        condata = self.selectid(data)
        answer = profile(condata)
        return answer, 200, {'Access-Control-Allow-Origin': '*'}