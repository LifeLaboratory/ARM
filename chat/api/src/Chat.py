import chat.api.helpers.base_name as names
from chat.api.helpers.service import Sql
import logging
import chat.api.src.vk.send_vk_message as vk
import chat.api.src.fb.send_facebook_message as fb
import datetime

chat_type = {
    'VK': vk.send_msg_to_vk,
    'FB': fb.send_to_operator
}


def get_chat_list_operator(id_user):
    """
    Метод получается список чатов оператора
    :return:
    """
    try:
        list_chat = Sql.exec(file="api/sql/chat/select_chat_operator.sql", args={'id_user': id_user})
    except:
        logging.error('error: Ошибка запроса к базе данных. Возможно такой пользователь уже есть')
        return {names.ANSWER: names.WARNING,
                names.DATA: {"error_info": "Ошибка запроса к базе данных. Возможно такой пользователь уже есть"}}
    return {names.ANSWER: names.SUCCESS, names.DATA: list_chat}


def get_chat(id_chat):
    """
    Метод получает историю переписки
    :return:
    """
    try:
        chat_history = Sql.exec(file="api/sql/chat/select_chat_history.sql", args={'id_chat': id_chat})
    except:
        logging.error('error: Ошибка запроса к базе данных. Возможно такой пользователь уже есть')
        return {names.ANSWER: names.WARNING,
                names.DATA: {"error_info": "Ошибка запроса к базе данных. Возможно такой пользователь уже есть"}}
    return {names.ANSWER: names.SUCCESS, names.DATA: chat_history}


def post_chat_message(id_client, Message):
    """
    Метод отправляет клиенту сообщение
    :return:
    """
    id_client = id_client.split('|')
    args = {'SALT': id_client[0],
            'id_client': id_client[1],
            'Message': Message,
            'Data_message': datetime.datetime.now(),
            'Sender': 'Operator'}
    try:
        chat_history = Sql.exec(file="api/sql/chat/insert_message_chat.sql", args=args)
    except:
        logging.error('error: Ошибка запроса к базе данных. Возможно такой пользователь уже есть')
        return {names.ANSWER: names.WARNING,
                names.DATA: {"error_info": "Ошибка запроса к базе данных. Возможно такой пользователь уже есть"}}
    return {names.ANSWER: names.SUCCESS, names.DATA: chat_history}


def insert_new_chat(id_client):
    """
    Метод добавляет новый чат
    :return:
    """
    id_client = id_client.split('|')
    args = {
        'id_client': id_client[1]
    }
    try:
        Sql.exec(file="api/sql/chat/insert_chat.sql", args=args)
    except:
        logging.error('error: Ошибка запроса к базе данных. Возможно такой пользователь уже есть')
        return {names.ANSWER: names.WARNING,
                names.DATA: {"error_info": "Ошибка запроса к базе данных. Возможно такой пользователь уже есть"}}
    return {names.ANSWER: names.SUCCESS, names.DATA: None}


def update_chat_status():
    """
    Метод обновляет статус чата
    Закрыт/открыт
    :return:
    """
    pass