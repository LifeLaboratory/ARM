# coding=utf-8
from classification.api.helpers.service import Gis as gs
from flask_restful import Resource, reqparse
from classification.api.src.classificator import *


class Classificator(Resource):
    def __init__(self):
        self.__parser = reqparse.RequestParser()
        self.__parser.add_argument('data')
        self.__parser.add_argument('id_company')
        self.__parser.add_argument('id_user')
        self.__parser.add_argument('id_client')
        self.__parser.add_argument('Message')
        self.__parser.add_argument('Answer')
        self.__args = self.__parser.parse_args()
        self.data = None
        self.id_company = None
        self.id_client = None
        self.classification = Answer()

    def parse_data(self):
        self.data = self.__args.get('data', None)
        self.data = gs.converter(self.data)
        return self.data

    def put(self):
        """
        Метод принимает добавление чата/сообщения
        :return:
        """
        data = self.parse_data()
        Message = self.data.get('Message', None)
        Answer = self.data.get('Answer', None)
        answer = {'Answer': self.classification.add(Message, Answer)}
        return answer, 200, {'Access-Control-Allow-Origin': '*'}

    def post(self):
        data = self.parse_data()
        Message = self.data.get('Message', None)
        # print(Message)
        answer = gs.converter({'Answer': self.classification.get(Message)})
        print(answer)
        return answer, 200, {'Access-Control-Allow-Origin': '*'}

    def get(self):
        """
        Метод получает списочные данные
        :return:
        """
        self.id_user = self.__args.get('id_user', None)
        print(self.id_user)
        answer = {'Answer': None} # get_chat_list_operator(self.id_user)
        return answer, 200, {'Access-Control-Allow-Origin': '*'}

    def option(self):
        return "OK", 200, {'Access-Control-Allow-Origin': '*'}