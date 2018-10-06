-- получить информацию о конкретном пользователе в компании
select *
from "users"
where "id_user" = {id_user}
  and "id_company" = {id_company}