with static_accept as (
  select count(*) filter( where accept = 0) as "acc"
    , count(*) filter( where accept = 1) as "not_acc"
    , avg(rating) as "rating"
    , "id_user"
    , TO_DATE ("date"::date, 'YYYY-MM-01') as "date"
  from "chat_list"
  where "id_company" = {id_company}
  group by id_user, TO_DATE ("date"::date, 'YYYY-MM-01')
)
select 500 + "acc" * 1.7 - "not_acc" * 0.2 + "rating" * 1.8
 , "name"
 , "date"
from static_accept
  left join users using ("id_user")
order by 500 + "acc" * 1.7 - "not_acc" * 0.2 + "rating" * 1.8 desc, "name"