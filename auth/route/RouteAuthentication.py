# coding=utf-8
from api.helpers.service import Gis as gs
from flask_restful import Resource, reqparse
from src.registration import registration

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
    """
    def switch(self):
        if self.filter_rec == "get_user_name" and self.data is not None:
            self.data["id_user"] = auth.session_verification(self.data["UUID"])
            answer = gs.converter(auth.get_user_name(self.data["id_user"]))
        else:
            answer = gs.converter(gs.converter(auth.login_verification(self.data)))
        return answer
    """
    def put(self):
        data = self.parse_data()
        answer = registration(data)
        print(answer)
        return answer, 200, {'Access-Control-Allow-Origin': '*'}

