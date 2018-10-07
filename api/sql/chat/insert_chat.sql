-- добавление нового чата
with ins as (
     insert into "client"("name")
     values ('')
    )
insert into "chat_list"("id_chat", "status", "date")
select  ('{SALT}'::text || '|' || {id_client}::text)::text, 'Open', now()
returning "id_chat"