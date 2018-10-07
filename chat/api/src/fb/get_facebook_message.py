from fbchat import Client
from fbchat.models import *
from api.service import database_holding

def send_to_operator(event):
    return 1


def main():
    client = Client('den-isk1995@mail.ru', '2xkzWW+%nKND', max_tries=100)
    #while True:
    unread_thread_ids = client.fetchUnread()
    for unread_thread in unread_thread_ids:
        msg = client.fetchThreadMessages(unread_thread)
        msg.reverse()
        for m in msg:
            if not m.is_read:
                print(unread_thread, m)
                database_holding('fb', unread_thread, m.text, 'Client')  # Работа с БД
                send_to_operator(unread_thread, m.text)
        client.markAsRead(unread_thread)
    client.logout()


if __name__ == '__main__':
    main()