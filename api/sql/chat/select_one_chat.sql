select *
from "chat_message"
where "id_chat" = {id_chat}
  and exists(
        select 1
        from "chat_list"
        where "id_user" = {id_user}
    )