-- Средний рейтинг по чатам оператора
select avg(rating)
from chat_list
  where "id_company" = {id_company}
