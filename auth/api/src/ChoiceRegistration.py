import api.helpers.base_name as names
from api.src.CompanyRegistration import registration as CompReg
from api.src.UserRegistration import registration as UsReg
from api.src.UpdateUserAuth import update as uAuth
from api.src.UpdateUserName import update as uName
from api.src.UpdateCompanyAuth import update as uCAuth
from api.src.UpdateCompanyName import update as uCName

def choice(user_data):
    if user_data.get(names.NAME, None) is not None and user_data.get(names.ID_COMPANY, None) is not None and user_data.get(names.LOGIN, None) is None:
        print("UpdateCompanyName")
        answer = uCName(user_data)
    elif user_data.get(names.ID_COMPANY, None) is not None and user_data.get(names.DESCRIPTION, None) is None:
        print("UpdateCompanyAuth")
        answer = uCAuth(user_data)
    elif user_data.get(names.NAME, None) is not None and user_data.get(names.ID_USER, None) is not None:
        print("UpdateUserName")
        answer = uName(user_data)
    elif user_data.get(names.ID_USER, None) is not None:
        print("UpdateUserAuth")
        answer = uAuth(user_data)
    elif user_data.get(names.ID_COMPANY, None) is None:
        print("Company registration")
        answer = CompReg(user_data)
    else:
        print("User registration")
        answer = UsReg(user_data)
    return answer