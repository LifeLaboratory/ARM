import hashlib
import logging
import uuid
import api.helpers.base_name as names
from api.helpers.service import Gis as gs

def registration(user_data):
    """
    Метод проверяет корректность введенных данных
    :param user_data: dict данные пользователя
    :return: UUID сессии
    """
    check = [names.LOGIN, names.PASSWORD, names.NAME]
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

    answer = input_auth_table(registration_data)
    if answer.get(names.ANSWER) is not names.SUCCESS:
        return {names.ANSWER: names.WARNING, names.DATA: {"error_info":"Ошибка запроса к базе данных"}}
    answer = registration_data
    return answer
