-- Средний рейтинг по чатам операторов в компании
select
  "name" as "Name"
  , avg_rating::numeric(32, 3)
from (select avg(rating) avg_rating, "id_user" from "chat_list" group by "id_user") nd
  join "users" using ("id_user")
order by avg_rating desc