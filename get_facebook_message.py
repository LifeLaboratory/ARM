from fbchat import Client
from fbchat.models import *

def database_holding(event):
    return 1

def send_to_operator(event):
    return 1

def main():
    client = Client('den-isk1995@mail.ru', '2xkzWW+%nKND', max_tries=100)
    while True:
        unread_thread_ids = client.fetchUnread()
        for unread_thread in unread_thread_ids:
            msg = client.fetchThreadMessages(unread_thread)
            print(unread_thread, msg.text)

#            database_holding(unread_msg, msg)
#            send_to_operator(unread_msg, msg)

    client.logout()

if __name__ == '__main__':
    main()