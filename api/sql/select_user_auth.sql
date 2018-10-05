-- В метод передаются Логин и пароль, возвращается id_user
insert into "session"("session", "id_user", "id_company")
select md5(random()::text || clock_timestamp()::text)::uuid
, "id_user"
, "id_company"
from (
  select (
  select "id_user"
  from "users"
  where "login" = '{login}'
    and "password" = '{password}'
  limit 1
  ) "id_user",
  (
  select "id_company"
  from "company"
  where "login" = '{login}'
    and "password" = '{password}'
  limit 1
  ) "id_company"
) nd
where "id_user" is not null
returning "session"