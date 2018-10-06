with uc as (
  select "id_user" as "del_user"
  from "user_company"
  where "id_company" = {id_company}
)
DELETE FROM "users"
WHERE exists(select 1 from uc where "del_user" = "id_user")