import auth.api.helpers.base_name as names
from auth.api.helpers.service import Sql
import logging
from auth.api.src.sessionToId import convert


def auth(user_data):
    check = [names.LOGIN, names.PASSWORD]
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
    answer = selectUser(auth_data)
    if answer.get(names.ANSWER) is not names.SUCCESS:
        return {names.ANSWER: names.WARNING, names.DATA: {"error_info": "Ошибка запроса к базе данных"}}
    return answer

def selectUser(user_data):
    try:
        session = Sql.exec(file="api/sql/select_user_auth.sql", args=user_data)
        sesData = dict()
        sesData[names.SESSION] = session[0]["session"]
        condata = convert(sesData)
        type = None
        #print(condata)
        if condata[names.DATA].get(names.ID_USER, None) is None and condata[names.DATA].get(names.ID_COMPANY,
                                                                                            None) is not None:
            type = "Company"
        elif condata[names.DATA].get(names.ID_USER, None) is not None and condata[names.DATA].get(names.ID_COMPANY,
                                                                                                  None) is not None:
            type = "Operator"

    except:
        logging.error('error: Ошибка запроса к базе данных. Возможно такой пользователь уже есть')
        return {names.ANSWER: names.WARNING,
                names.DATA: {"error_info":"Ошибка запроса к базе данных. Возможно такой пользователь уже есть"}}
    return {names.ANSWER: names.SUCCESS, names.DATA: session[0], names.TYPE: type}

