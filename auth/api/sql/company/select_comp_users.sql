-- получаем список оператов компании
select "name" as "Name"
  , "status" as "Status"
from users
where "id_user" = any(
    select "id_user"
    from "user_company"
    where "id_company" = {id_company}
    )