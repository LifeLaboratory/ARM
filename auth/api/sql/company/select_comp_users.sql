-- получаем список оператов компании
select "id_user"
  , "name" as "Name"
  , "status" as "Status"
  , "login" as "Login"
from users
where "id_user" = any(
    select "id_user"
    from "user_company"
    where "id_company" = {id_company}
    )