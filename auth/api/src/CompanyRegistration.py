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
    check = [names.LOGIN, names.PASSWORD, names.NAME, names.DESCRIPTION]
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
    answer = inputCompany(registration_data)
    if answer.get(names.ANSWER) is not names.SUCCESS:
        return {names.ANSWER: names.WARNING, names.DATA: {"error_info":"Ошибка запроса к базе данных"}}
    return answer

def inputCompany(user_data):
    password_hash = hashlib.md5()
    password_hash.update(user_data[names.PASSWORD].encode())
    user_data[names.PASSWORD] = password_hash.hexdigest()
    try:
        #id_user = gs.SqlQuery(sql)[0]['id_user']
        print(user_data)
        session = Sql.exec(file="../sql/company/insert_company.sql", args=user_data)
        print("ses:", session)
    except:
        logging.error('error: Ошибка запроса к базе данных. Возможно такой пользователь уже есть')
        return {names.ANSWER: names.WARNING,
                names.DATA: {"error_info":"Ошибка запроса к базе данных. Возможно такой пользователь уже есть"}}
    return {names.ANSWER: names.SUCCESS, names.DATA: session}

