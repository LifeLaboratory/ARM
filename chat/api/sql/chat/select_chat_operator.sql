-- Получает все чаты оператора
select "id_chat"
  , "status" as "Status"
  , "date" as "Data"
  , "rating" as "Rating"
  , "accept" as "Accept"
  , (
    select "message"
    from "chat_message"
    where chat_message."id_chat" = chat_list."id_chat"
    order by date
    limit 1
    ) as "Last_text"
from "chat_list"
where "id_user" = {id_user}
  and "status" = 1
order by "date" desc