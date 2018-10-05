-- Получает все чаты оператора
select *
  , (
    select "message"
    from "chat_message"
    where chat_message."id_chat" = chat_list."id_chat"
    order by date
    limit 1
    ) "last_text"
from "chat_list"
where "id_user" = {id_user}