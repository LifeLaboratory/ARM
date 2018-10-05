import auth.api.helpers.base_name as names
from auth.api.src.CompanyRegistration import registration as CompReg
from auth.api.src.UserRegistration import registration as UsReg
from auth.api.src.UpdateUser import update

def choice(user_data):
    if user_data.get(names.ID_USER, None) is not None:
        answer = update(user_data)
    elif user_data.get(names.ID_COMPANY, None) is None:
        answer = CompReg(user_data)
    else:
        answer = UsReg(user_data)
    return answer