import vk_api
from vk_api.bot_longpoll import VkBotLongPoll, VkBotEventType

ACCESS_TOKEN_2 = 'b6b30f7014d5f65777496cbcc78874f9e7e7e11c0b276fea0f3cb77a68140a7ec492317472f801bb59d47'
GROUP_ID = '172231442'

def get_from_operator():
    return 1,1

def main():
    vk_session = vk_api.VkApi(token=ACCESS_TOKEN_2)
    vk = vk_session.get_api()
    while True:
        chat_id, msg = get_from_operator()
        vk.messages.send(user_id=chat_id, message=msg)



if __name__ == '__main__':
    main()