-- Получает все чаты оператора
select "id_chat"
  --, "status" as "Status"
  , "date" as "Data"
  , "rating" as "Rating"
  , "accept" as "Accept"
  , "id_chat" as "id_client"
  , (
    select "message"
    from "chat_message"
    where chat_message."id_chat" = chat_list."id_chat"
    order by date
    limit 1
    ) "Text"
from "chat_list"
where "id_user" = {id_user}
order by "date" desc