import hashlib
import logging
import uuid
import auth.api.helpers.base_name as names
from auth.api.helpers.service import Sql

def registration(user_data):
    """
    Метод проверяет корректность введенных данных
    :param user_data: dict данные пользователя
    :return: UUID сессии
    """
    print()
    print("USER_DATA", user_data)
    check = [names.LOGIN, names.PASSWORD, names.NAME, names.ID_COMPANY]
    registration_data = dict.fromkeys(check, '')
    error = False
    for data in check:
        if user_data.get(data, None) is None:
            logging.info('Incorrect parameter ' + data)
            registration_data[data] = 'Пустой параметр!'
            error = True
        else:
            registration_data[data] = user_data[data]
    if error:
        return {names.ANSWER: names.ERROR, names.DATA: registration_data}
    answer = inputUser(registration_data)
    if answer.get(names.ANSWER) is not names.SUCCESS:
        return {names.ANSWER: names.WARNING, names.DATA: {"error_info":"Ошибка запроса к базе данных"}}
    return answer

def inputUser(user_data):
    try:
        id = Sql.exec(file="api/sql/operator/insert_user.sql", args=user_data)
    except:
        logging.error('error: Ошибка запроса к базе данных. Возможно такой пользователь уже есть')
        return {names.ANSWER: names.WARNING,
                names.DATA: {"error_info":"Ошибка запроса к базе данных. Возможно такой пользователь уже есть"}}
    return {names.ANSWER: names.SUCCESS}

