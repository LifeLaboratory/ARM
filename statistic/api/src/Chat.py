import statistic.api.helpers.base_name as names
from statistic.api.helpers.service import Sql
import logging
import datetime


def get_statistic():
    """
    Метод получается список чатов оператора
    :return:
    """
    try:
        list_chat = Sql.exec(file="api/sql/stati/select_rating_all_user_company.sql")
    except:
        logging.error('error: Ошибка запроса к базе данных. Возможно такой пользователь уже есть')
        return {names.ANSWER: names.WARNING,
                names.DATA: {"error_info": "Ошибка запроса к базе данных. Возможно такой пользователь уже есть"}}
    return {names.ANSWER: names.SUCCESS, names.DATA: list_chat}


def get_chat_list_history(id_user):
    """
    Метод получается список архивных чатов оператора
    :return:
    """
    try:
        list_chat = Sql.exec(file="api/sql/statistic/select_history.sql", args={'id_user': id_user})
        #  for i in list_chat:
        #     name = get_user_info(int(i['id_chat'].split('|')[0]))
        #    i['Name'] = name
    except:
        logging.error('error: Ошибка запроса к базе данных. Возможно такой пользователь уже есть')
        return {names.ANSWER: names.WARNING,
                names.DATA: {"error_info": "Ошибка запроса к базе данных. Возможно такой пользователь уже есть"}}
    return {names.ANSWER: names.SUCCESS, names.DATA: list_chat}


def get_chat():
    """
    Метод получает историю переписки
    :return:
    """
    try:
        chat_history = Sql.exec(file="api/sql/statistic/select_close.sql")
        # for i in chat_history:
        #     name = get_user_info(int(i['id_chat'].split('|')[0]))
        #     i['Name'] = name
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
    vk.send_msg_to_vk('|'.join(id_client), Message)
    '''
    try:
        chat_history = Sql.exec(file="api/sql/chat/insert_message_chat.sql", args=args)
    except:
        logging.error('error: Ошибка запроса к базе данных. Возможно такой пользователь уже есть')
        return {names.ANSWER: names.WARNING,
                names.DATA: {"error_info": "Ошибка запроса к базе данных. Возможно такой пользователь уже есть"}}
    '''
    return {names.ANSWER: names.SUCCESS, names.DATA: ''}


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
