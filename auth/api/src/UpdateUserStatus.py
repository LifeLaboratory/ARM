import auth.api.helpers.base_name as names
from auth.api.helpers.service import Sql
import logging


def update(user_data):
    check = ['id_user', 'Status']
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
    answer = updateUser(auth_data)
    if answer.get(names.ANSWER) is not names.SUCCESS:
        return {names.ANSWER: names.WARNING, names.DATA: {"error_info": "Ошибка запроса к базе данных"}}
    return answer

def updateUser(user_data):

    session = Sql.exec(file="api/sql/operator/update_status.sql", args=user_data)
    return {names.ANSWER: names.SUCCESS, names.DATA: session}

