# coding=utf-8
from chat.api.helpers.service import Gis as gs
from flask_restful import Resource, reqparse
from chat.api.src.Chat import *
from chat.api.helpers.sessionToId import convert


class Chat(Resource):
    def __init__(self):
        self.__parser = reqparse.RequestParser()
        self.__parser.add_argument('data')
        self.__parser.add_argument('id_company')
        self.__parser.add_argument('id_user')
        self.__parser.add_argument('id_client')
        self.__parser.add_argument('Message')
        self.__parser.add_argument('Session')
        self.__args = self.__parser.parse_args()
        self.data = None
        self.id_company = None
        self.id_client = None

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
        """
        Метод принимает добавление чата/сообщения
        :return:
        """
        data = self.parse_data()
        id_client = data.get('id_client', None)
        answer = insert_new_chat(id_client)
        return answer, 200, {'Access-Control-Allow-Origin': '*'}

    def post(self):
        data = self.parse_data()
        id_client = data.get('id_client', None)
        Message = data.get('Message', None)
        answer = post_chat_message(id_client, Message)
        # auth(data)
        return answer, 200, {'Access-Control-Allow-Origin': '*'}

    def get(self):
        """
        Метод получает списочные данные
        :return:
        """
        self.session = self.__args.get('Session', None)
        print(self.session)
        if self.session is not None:
            data = dict()
            data[names.SESSION] = self.session
            condata = self.selectid(data)
            answer = get_chat_list_operator(condata[names.ID_USER])
        else:
            answer = {names.ANSWER: "Error", names.DATA: {"error_info": "Session not found"}}
        return answer, 200, {'Access-Control-Allow-Origin': '*'}
