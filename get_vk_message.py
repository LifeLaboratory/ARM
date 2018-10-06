import vk_api
from vk_api.bot_longpoll import VkBotLongPoll, VkBotEventType

ACCESS_TOKEN = '08b030e59cfca185c1e95f0caba3e7f245ba47681ed31b36606c95fb8294e94b1eefd1ce88858e501d007'
ACCESS_TOKEN_2 = 'b6b30f7014d5f65777496cbcc78874f9e7e7e11c0b276fea0f3cb77a68140a7ec492317472f801bb59d47'
GROUP_ID = '172231442'

def database_holding(event):
    return 1

def send_to_operator(event):
    return 1

def main():
    vk_session = vk_api.VkApi(token=ACCESS_TOKEN_2)
    longpoll = VkBotLongPoll(vk_session, GROUP_ID)

    for event in longpoll.listen():
        if event.type == VkBotEventType.MESSAGE_NEW:
            print(event)
#            database_holding(event.obj)
#            send_to_operator(event.obj)

if __name__ == '__main__':
    main()