-- добавление нового чата
with ins as (
     insert into "client"("name")
     values ('')
    )
insert into "chat_list"("id_company", "status", "date", "id_chat")
select 1, 'Open', now(), ('{SALT}'::text || '|' || {id_client}::text)::text
returning "id_chat"