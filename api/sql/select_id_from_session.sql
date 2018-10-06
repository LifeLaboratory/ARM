with session_t as (
  select *
  from "session"
  where session."session" = {Session}
)
select "id_user"
  , coalesce("id_company",
      (
       select "id_user"
       from "user_company"
       where user_company."id_user" = session_t."id_user"
       limit 1
      )
    ) as "id_company"
from session_t
where "id_user" is not null
  and "id_company" is not null