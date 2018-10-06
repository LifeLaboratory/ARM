import logging
import auth.api.helpers.base_name as names
from auth.api.helpers.service import Sql

def profile(user_data):
    check = [names.ID_USER]
    profile_data = dict.fromkeys(check, '')
    error = False
    for data in check:
        if user_data.get(data, None) is None:
            logging.info('Incorrect parameter ' + data)
            profile_data[data] = 'Пустой параметр!'
            error = True
        else:
            profile_data[data] = user_data[data]
    if error:
        return {names.ANSWER: names.ERROR, names.DATA: profile_data}
    answer = selectInfo(profile_data)
    if answer.get(names.ANSWER) is not names.SUCCESS:
        return {names.ANSWER: names.WARNING, names.DATA: {"error_info": "Ошибка запроса к базе данных"}}
    return answer

def selectInfo(user_data):
    try:
        info = Sql.exec(file="api/sql/operator/select_operator.sql", args=user_data)
    except:
        logging.error('error: Ошибка запроса к базе данных. Возможно такой пользователь уже есть')
        return {names.ANSWER: names.WARNING,
                names.DATA: {"error_info":"Ошибка запроса к базе данных. Возможно такой пользователь уже есть"}}
    return {names.ANSWER: names.SUCCESS, names.DATA: info[0]}

