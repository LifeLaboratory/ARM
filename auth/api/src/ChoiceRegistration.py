import logging
import auth.api.helpers.base_name as names
from auth.api.src.CompanyRegistration import registration as CompReg
#from auth.api.src.UserRegistration import registration as UsReg
from auth.api.helpers.service import Sql

def choice(user_data):
    if user_data.get(names.COMPANY, None) is None:
        answer = CompReg(user_data)
    else:
        answer = "ITS USER"
        print(answer)
        #answer = UsReg(user_data)
    return answer