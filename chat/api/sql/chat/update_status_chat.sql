update "chat_list"
set "status" = {Status}
where "id_chat" = {id_chat}
returning "id_chat"