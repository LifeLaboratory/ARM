-- добавление нового чата
with ins as (
     insert into "client"("name")
     values ('')
    )
insert into "chat_list"("id_company", "status", "date", "id_client")
select 1, 'Open', now(), {id_client}
returning "id_chat"