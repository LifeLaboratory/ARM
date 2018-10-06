-- добавление нового чата
with ins as (
     insert into "client"("name")
     values ('')
    )
insert into "chat_list"("id_company", "id_user", "status", "date", "id_client")
select {id_company}, {id_user}, 'Open', now()::timestamp, {id_client}
returning "id_chat"