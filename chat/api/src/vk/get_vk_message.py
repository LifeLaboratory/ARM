# -*- coding: utf-8 -*-
import vk_api
from vk_api.bot_longpoll import VkBotLongPoll, VkBotEventType
from chat.api.helpers.service import database_holding
import chat.api.helpers.base_name as names
import logging

ACCESS_TOKEN = '08b030e59cfca185c1e95f0caba3e7f245ba47681ed31b36606c95fb8294e94b1eefd1ce88858e501d007'
ACCESS_TOKEN_2 = 'b6b30f7014d5f65777496cbcc78874f9e7e7e11c0b276fea0f3cb77a68140a7ec492317472f801bb59d47'
GROUP_ID = '172231442'

def send_to_operator(user_id, msg):
    return 1


def get_vk_user_data(user_id):
    vk_session = vk_api.VkApi(token=ACCESS_TOKEN_2)
    vk = vk_session.get_api()
    answer = vk.users.get(user_id=user_id)
    # print(answer[0]['first_name'], answer[0]['last_name'])
    return answer


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
            database_holding('vk', event.obj.from_id, event.obj.text, 'Client')  # Работа с БД
            send_to_operator(event.obj.from_id, event.obj.text)


if __name__ == '__main__':
    main()