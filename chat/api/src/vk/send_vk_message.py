import vk_api
from vk_api.bot_longpoll import VkBotLongPoll, VkBotEventType
from chat.api.helpers.service import database_holding

ACCESS_TOKEN_2 = 'b6b30f7014d5f65777496cbcc78874f9e7e7e11c0b276fea0f3cb77a68140a7ec492317472f801bb59d47'
GROUP_ID = '172231442'

def get_from_operator():
    return 1, 1

def get_vk_user_data(user_id):
    vk_session = vk_api.VkApi(token=ACCESS_TOKEN_2)
    vk = vk_session.get_api()
    answer = vk.users.get(user_id=user_id)
    #print(answer[0]['first_name'], answer[0]['last_name'])
    return answer # [{'id': 56176108, 'first_name': 'Денис', 'last_name': 'Степанов'}]

def send_msg_to_vk(str_chat_id, msg):
    vk_chat_id = str_chat_id.split('|')[1]
    vk_session = vk_api.VkApi(token=ACCESS_TOKEN_2)
    vk = vk_session.get_api()
    database_holding('vk', vk_chat_id, msg, 'Operator')
    vk.messages.send(user_id=vk_chat_id, message=msg)

def main():
    get_vk_user_data('56176108')


if __name__ == '__main__':
    main()