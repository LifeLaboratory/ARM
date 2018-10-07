from fbchat import Client
from fbchat.models import *
from integrator.vk.service import database_holding

def get_from_operator(event):
    return '100001371797717', 'send ret'

def send_to_operator(event):
    return 1

def main():
    client = Client('den-isk1995@mail.ru', '2xkzWW+%nKND', max_tries=100)
    while True:
        thread_id, msg = get_from_operator(client)
        database_holding('fb', thread_id, msg, 'Operator')
        client.sendMessage(msg,thread_id)

    client.logout()

if __name__ == '__main__':
    main()