# coding=utf-8
from chat.api.helpers.service import Gis as gs
from flask_restful import Resource, reqparse
from chat.api.src.Chat import *


class Chat(Resource):
    def __init__(self):
        self.__parser = reqparse.RequestParser()
        self.__parser.add_argument('data')
        self.__parser.add_argument('id_company')
        self.__parser.add_argument('id_user')
        self.__parser.add_argument('id_client')
        self.__parser.add_argument('Message')
        self.__args = self.__parser.parse_args()
        self.data = None
        self.id_company = None
        self.id_client = None

    def parse_data(self):
        self.data = self.__args.get('data', None)
        self.data = gs.converter(self.data)
        return self.data

    def put(self):
        """
        Метод принимает добавление чата/сообщения
        :return:
        """
        id_client = self.__args.get('id_client', None)
        answer = insert_new_chat(id_client)
        return answer, 200, {'Access-Control-Allow-Origin': '*'}

    def post(self):
        id_client = self.__args.get('id_client', None)
        Message = self.__args.get('Message', None)
        answer = post_chat_message(id_client, Message)
        # auth(data)
        return answer, 200, {'Access-Control-Allow-Origin': '*'}

    def get(self):
        """
        Метод получает списочные данные
        :return:
        """
        self.id_user = self.__args.get('id_user', None)
        print(self.id_user)
        answer = get_chat_list_operator(self.id_user)
        return answer, 200, {'Access-Control-Allow-Origin': '*'}
