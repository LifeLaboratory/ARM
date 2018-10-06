import auth.api.helpers.base_name as names
from auth.api.helpers.service import Sql
import logging


def deleteUS(user_data):
    check = [names.ID_USER]
    auth_data = dict.fromkeys(check, '')
    error = False
    for data in check:
        if user_data.get(data, None) is None:
            logging.info('Incorrect parameter ' + data)
            auth_data[data] = 'Пустой параметр!'
            error = True
        else:
            auth_data[data] = user_data[data]
    if error:
        return {names.ANSWER: names.ERROR, names.DATA: auth_data}
    answer = delUser(auth_data)
    if answer.get(names.ANSWER) is not names.SUCCESS:
        return {names.ANSWER: names.WARNING, names.DATA: {"error_info": "Ошибка запроса к базе данных"}}
    return answer

def delUser(user_data):
    try:
        session = Sql.exec(file="api/sql/operator/delete_user.sql", args=user_data)
    except:
        logging.error('error: Ошибка запроса к базе данных. Возможно такой пользователь уже есть')
        return {names.ANSWER: names.WARNING,
                names.DATA: {"error_info":"Ошибка запроса к базе данных. Возможно такой пользователь уже есть"}}
    return {names.ANSWER: names.SUCCESS, names.DATA: session}

