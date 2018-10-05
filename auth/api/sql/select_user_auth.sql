-- В метод передаются Логин и пароль, возвращается id_user
select "id_user"
from "users"
where "Login" = %s
  and "Password" = %s