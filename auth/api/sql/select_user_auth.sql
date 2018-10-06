-- В метод передаются Логин и пароль, возвращается id_user
insert into "session"("session", "id_user", "id_company")
select md5(random()::text || clock_timestamp()::text)::uuid
, "id_user"
, "id_company"
from (
  select (
  select "id_user"
  from "users"
  where "login" = '{Login}'
    and "password" = '{Password}'
  limit 1
  ) "id_user",
  (
  select "id_company"
  from "company"
  where "login" = '{Login}'
    and "password" = '{Password}'
  limit 1
  ) "id_company"
) nd
where "id_user" is not null or "id_company" is not null
returning "session" as "Session"