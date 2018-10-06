
select *
from (select avg(rating) avg_rating from "chat_list" group by "id_user")
order by avg_rating