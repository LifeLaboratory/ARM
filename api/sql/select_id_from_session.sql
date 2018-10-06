with session_t as (
  select *
  from "session"
  where session."session" = '{Session}'::uuid
)
select "id_user"
  , coalesce("id_company",
      (
       select "id_company"
       from "user_company"
       where user_company."id_user" = session_t."id_user"
       limit 1
      )
    ) as "id_company"
from session_t
where "id_user" is not null
  or "id_company" is not null