-- Средний рейтинг по чатам оператора
select avg(rating)
from chat_list
  where "id_user" = {id_user}
