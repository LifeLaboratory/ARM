import vk_api
from vk_api.bot_longpoll import VkBotLongPoll, VkBotEventType

ACCESS_TOKEN_2 = 'b6b30f7014d5f65777496cbcc78874f9e7e7e11c0b276fea0f3cb77a68140a7ec492317472f801bb59d47'
GROUP_ID = '172231442'

def get_from_operator():
    return 1,1

def send_msg_to_vk(str_chat_id, msg):
    vk_chat_id = str_chat_id.split('|')[1]
    vk_session = vk_api.VkApi(token=ACCESS_TOKEN_2)
    vk = vk_session.get_api()
    vk.messages.send(user_id=vk_chat_id, message=msg)

def main():
    send_msg_to_vk('vk|56176108', "asdasdasd")

if __name__ == '__main__':
    main()