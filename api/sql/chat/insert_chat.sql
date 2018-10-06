-- добавление нового чата
with ins as (
     insert into "client"("name")
     values ('')
    )
insert into "chat_list"("id_company", "id_chat", "status", "date", "id_client")
select {id_company}, ({SALT}::text || '|' || {id_client}::text)::text, 'Open', now(), {id_client}
returning "id_chat"