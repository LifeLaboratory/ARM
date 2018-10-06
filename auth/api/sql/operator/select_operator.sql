-- получить информацию о конкретном пользователе в компании
select "id_user"
  , "name" as "Names"
  , "status" as "Status"
  , "login" as "Login"
from "users"
where "id_user" = {id_user}