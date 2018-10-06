# -*- coding: utf-8 -*-
import vk_api
from vk_api.bot_longpoll import VkBotLongPoll, VkBotEventType
from .service import Sql
from .base_name import *
import logging
import datetime

ACCESS_TOKEN = '08b030e59cfca185c1e95f0caba3e7f245ba47681ed31b36606c95fb8294e94b1eefd1ce88858e501d007'
ACCESS_TOKEN_2 = 'b6b30f7014d5f65777496cbcc78874f9e7e7e11c0b276fea0f3cb77a68140a7ec492317472f801bb59d47'
GROUP_ID = '172231442'


def selectChat(user_data):
    try:
        session = Sql.exec(file="api/sql/chat/select_exists_chat.sql", args=user_data)
    except:
        logging.error('error: Ошибка запроса к базе данных. Возможно такой пользователь уже есть')
        return {ANSWER: WARNING,
                DATA: {"error_info": "Ошибка запроса к базе данных. Возможно такой пользователь уже есть"}}
    return {ANSWER: SUCCESS, DATA: session}


# vk_api.bot_longpoll.VkBotMessageEvent'>({'type': 'message_new', 'object':
# {'date': 1538790046, 'from_id': 56176108, 'id': 19, 'out': 0, 'peer_id': 56176108,
# 'text': 'asdas', 'conversation_message_id': 18, 'fwd_messages': [],
# 'important': False, 'random_id': 0, 'attachments': [], 'is_hidden': False},
# 'group_id': 172231442})>
def database_holding(event):
    check = ['id_chat']
    chat_data = dict.fromkeys(check, '')
    chat_data['id_chat'] = 'vk|' + event.obj.from_id

    select_data = selectChat(chat_data)
    if select_data.get(ANSWER) is SUCCESS:
        chat_id = select_data.get(DATA)
        if chat_id:
            # insertMessageToChat()
            # Добавляем в данный чат новое сообщение от ползователя
            Sql.exec(file="api/sql/chat/insert_chat.sql", args={'id_client': chat_id})
            return 1
    else:
        # Добавляем новый чат и вставляем сообщение от пользователя
        Sql.exec(file="../sql/insert_chat.sql", args={'id_client': chat_data['id_chat']})
        return 1
    args = {'SALT': "VK",
            'id_client': event.obj.from_id,
            # TODO: Что за поле
            # 'Message': Message,
            'Data_message': datetime.datetime.now(),
            'Sender': 'Client'
            }
    Sql.exec(file="../sql/insert_message_chat.sql", args=args)
    return 1


def send_to_operator(event):
    return 1


def main():
    vk_session = vk_api.VkApi(token=ACCESS_TOKEN_2)     # Создаем сессию
    longpoll = VkBotLongPoll(vk_session, GROUP_ID)      # Активируем работу с longpoll сервером

    for event in longpoll.listen():                     # Ждем события
        if event.type == VkBotEventType.MESSAGE_NEW:    # Если приняли новое сообщение
            # vk_api.bot_longpoll.VkBotMessageEvent'>({'type': 'message_new',
            # 'object': {'date': 1538790046, 'from_id': 56176108, 'id': 19,
            # 'out': 0, 'peer_id': 56176108, 'text': 'asdas', 'conversation_message_id': 18,
            # 'fwd_messages': [], 'important': False, 'random_id': 0, 'attachments': [],
            # 'is_hidden': False}, 'group_id': 172231442})>
            database_holding(event)                     # Работа с БД
            send_to_operator(event)


if __name__ == '__main__':
    main()
